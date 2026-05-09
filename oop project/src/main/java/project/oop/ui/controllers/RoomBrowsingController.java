package project.oop.ui.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import project.oop.*;
import project.oop.util.SceneNavigator;
import project.oop.util.Session;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomBrowsingController {

    @FXML private Label                      guestNameLabel;
    @FXML private ComboBox<String>           typeFilter;
    @FXML private ComboBox<String>           amenityFilter;
    @FXML private TextField                  maxPriceField;
    @FXML private TableView<Room>            roomsTable;
    @FXML private TableColumn<Room, Integer> colRoomNumber;
    @FXML private TableColumn<Room, String>  colType;
    @FXML private TableColumn<Room, Double>  colPrice;
    @FXML private TableColumn<Room, Integer> colCapacity;
    @FXML private TableColumn<Room, String>  colAmenities;
    @FXML private TableColumn<Room, String>  colStatus;

    @FXML
    public void initialize() {
        Guest guest = Session.getCurrentGuest();
        if (guest != null) guestNameLabel.setText(guest.getUsername());

        // Column bindings
        colRoomNumber.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getRoomNumber()).asObject());
        colType      .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getType().getName()));
        colPrice     .setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getType().getPrice()).asObject());
        colCapacity  .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getType().getCapacity()).asObject());
        colAmenities .setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getAmenities().stream()
                        .map(Amenity::getName)
                        .collect(Collectors.joining(", "))
        ));
        colStatus.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().isAvailable() ? "Available" : "Occupied"
        ));

        // Populate filters
        typeFilter.getItems().add("All Types");
        HotelDatabase.roomTypes.forEach(rt -> typeFilter.getItems().add(rt.getName()));
        typeFilter.setValue("All Types");

        amenityFilter.getItems().add("All Amenities");
        HotelDatabase.amenities.forEach(a -> amenityFilter.getItems().add(a.getName()));
        amenityFilter.setValue("All Amenities");

        roomsTable.setItems(FXCollections.observableArrayList(HotelDatabase.rooms));
    }

    @FXML
    private void applyFilter() {
        String type    = typeFilter.getValue();
        String amenity = amenityFilter.getValue();
        String maxStr  = maxPriceField.getText().trim();

        List<Room> filtered = HotelDatabase.rooms.stream()
                .filter(r -> type == null    || type.equals("All Types")      || r.getType().getName().equals(type))
                .filter(r -> amenity == null || amenity.equals("All Amenities") ||
                             r.getAmenities().stream().anyMatch(a -> a.getName().equals(amenity)))
                .filter(r -> {
                    if (maxStr.isEmpty()) return true;
                    try { return r.getType().getPrice() <= Double.parseDouble(maxStr); }
                    catch (NumberFormatException e) { return true; }
                })
                .collect(Collectors.toList());

        roomsTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void clearFilter() {
        typeFilter.setValue("All Types");
        amenityFilter.setValue("All Amenities");
        maxPriceField.clear();
        roomsTable.setItems(FXCollections.observableArrayList(HotelDatabase.rooms));
    }

    @FXML
    private void bookRoom() {
        Room selected = roomsTable.getSelectionModel().getSelectedItem();
        if (selected == null)           { alert("No Selection", "Please select a room first."); return; }
        if (!selected.isAvailable())    { alert("Unavailable", "This room is already occupied."); return; }

        // Date input dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Book " + selected);
        dialog.setHeaderText("Enter dates in format: yyyy-MM-dd");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        TextField checkInField  = new TextField(); checkInField.setPromptText("2025-06-01");
        TextField checkOutField = new TextField(); checkOutField.setPromptText("2025-06-05");
        grid.add(new Label("Check-In:"),  0, 0); grid.add(checkInField,  1, 0);
        grid.add(new Label("Check-Out:"), 0, 1); grid.add(checkOutField, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        try {
            LocalDate checkIn  = LocalDate.parse(checkInField.getText().trim());
            LocalDate checkOut = LocalDate.parse(checkOutField.getText().trim());

            Guest guest = Session.getCurrentGuest();
            Reservation res = guest.makeReservation(selected, checkIn, checkOut);

            if (res != null) {
                roomsTable.refresh();
                alert("Booked!", "Room " + selected.getRoomNumber() + " reserved!\nTotal: $" +
                        String.format("%.2f", res.calculatePayment()));
            } else {
                alert("Failed", "Could not book the room.");
            }
        } catch (IllegalArgumentException e) {
            alert("Invalid Input", e.getMessage());
        } catch (DateTimeParseException e) {
            alert("Invalid Date", "Use the format yyyy-MM-dd (e.g. 2025-06-01).");
        }
    }

    private void alert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    @FXML private void showDashboard()    { SceneNavigator.navigateTo("GuestDashboard.fxml"); }
    @FXML private void showRooms()        { SceneNavigator.navigateTo("RoomBrowsing.fxml"); }
    @FXML private void showReservations() { SceneNavigator.navigateTo("Reservation.fxml"); }
    @FXML private void showCheckout()     { SceneNavigator.navigateTo("Checkout.fxml"); }
    @FXML private void handleLogout()     { Session.clear(); SceneNavigator.navigateTo("Login.fxml"); }
}
