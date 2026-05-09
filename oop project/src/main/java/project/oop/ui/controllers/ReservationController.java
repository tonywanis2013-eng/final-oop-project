package project.oop.ui.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.oop.*;
import project.oop.util.SceneNavigator;
import project.oop.util.Session;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationController {

    @FXML private Label                          guestNameLabel;
    @FXML private ComboBox<String>               statusFilter;
    @FXML private TableView<Reservation>         reservationTable;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, Integer> colRoom;
    @FXML private TableColumn<Reservation, String>  colType;
    @FXML private TableColumn<Reservation, String>  colCheckIn;
    @FXML private TableColumn<Reservation, String>  colCheckOut;
    @FXML private TableColumn<Reservation, String>  colStatus;
    @FXML private TableColumn<Reservation, Double>  colTotal;
    @FXML private Label                          statusLabel;

    @FXML
    public void initialize() {
        Guest guest = Session.getCurrentGuest();
        if (guest != null) guestNameLabel.setText(guest.getUsername());

        colId      .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colRoom    .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getRoom().getRoomNumber()).asObject());
        colType    .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRoom().getType().getName()));
        colCheckIn .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCheckInDate().toString()));
        colCheckOut.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCheckOutDate().toString()));
        colStatus  .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatus().toString()));
        colTotal   .setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().calculatePayment()).asObject());

        statusFilter.getItems().addAll("All", "PENDING", "CONFIRMED", "CANCELLED", "COMPLETED");
        statusFilter.setValue("All");

        loadTable("All");
    }

    @FXML
    private void handleFilter() {
        loadTable(statusFilter.getValue());
    }

    private void loadTable(String filter) {
        Guest guest = Session.getCurrentGuest();
        List<Reservation> list = HotelDatabase.reservations.stream()
                .filter(r -> r.getGuest().equals(guest))
                .collect(Collectors.toList());

        if (filter != null && !filter.equals("All")) {
            ReservationStatus status = ReservationStatus.valueOf(filter);
            list = list.stream().filter(r -> r.getStatus() == status).collect(Collectors.toList());
        }

        reservationTable.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void handleNewReservation() {
        SceneNavigator.navigateTo("RoomBrowsing.fxml");
    }

    @FXML
    private void handleCancel() {
        Reservation r = reservationTable.getSelectionModel().getSelectedItem();
        if (r == null) { statusLabel.setText("Select a reservation to cancel."); return; }

        if (r.getStatus() != ReservationStatus.PENDING &&
            r.getStatus() != ReservationStatus.CONFIRMED) {
            statusLabel.setText("Only pending or confirmed reservations can be cancelled.");
            return;
        }

        Guest guest = Session.getCurrentGuest();

        // Refund if already paid
        if (r.getStatus() == ReservationStatus.CONFIRMED) {
            guest.setBalance(guest.getBalance() + r.calculatePayment());
        }

        guest.cancelReservation(r);
        r.getRoom().setAvailable(true);
        loadTable(statusFilter.getValue());
        statusLabel.setText("Reservation #" + r.getId() + " cancelled. Refund: $" +
                String.format("%.2f", r.calculatePayment()));
    }

    @FXML
    private void handlePaySelected() {
        Reservation r = reservationTable.getSelectionModel().getSelectedItem();
        if (r == null) { statusLabel.setText("Select a reservation to pay."); return; }
        if (r.getStatus() == ReservationStatus.CONFIRMED) { statusLabel.setText("This reservation is already paid."); return; }
        if (r.getStatus() == ReservationStatus.CANCELLED)  { statusLabel.setText("Cannot pay a cancelled reservation."); return; }
        SceneNavigator.navigateTo("Checkout.fxml");
    }

    @FXML private void showDashboard()    { SceneNavigator.navigateTo("GuestDashboard.fxml"); }
    @FXML private void showRooms()        { SceneNavigator.navigateTo("RoomBrowsing.fxml"); }
    @FXML private void showReservations() { SceneNavigator.navigateTo("Reservation.fxml"); }
    @FXML private void showCheckout()     { SceneNavigator.navigateTo("Checkout.fxml"); }
    @FXML private void handleLogout()     { Session.clear(); SceneNavigator.navigateTo("Login.fxml"); }
}
