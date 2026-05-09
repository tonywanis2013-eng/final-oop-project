package project.oop.ui.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.oop.*;
import project.oop.util.SceneNavigator;
import project.oop.util.SessionStaff;

import java.util.stream.Collectors;

public class ManageRoomsController {

    @FXML private TextField            roomNumberField;
    @FXML private ComboBox<RoomType>   roomTypeCombo;
    @FXML private Label                addStatusLabel;
    @FXML private Label                deleteStatusLabel;
    @FXML private TableView<Room>      roomsTable;
    @FXML private TableColumn<Room, Integer> colRoomNumber;
    @FXML private TableColumn<Room, String>  colRoomType;
    @FXML private TableColumn<Room, Double>  colRoomPrice;
    @FXML private TableColumn<Room, Integer> colRoomCapacity;
    @FXML private TableColumn<Room, String>  colRoomAmenities;
    @FXML private TableColumn<Room, String>  colRoomStatus;

    @FXML
    public void initialize() {
        // Populate room type combo
        roomTypeCombo.setItems(FXCollections.observableArrayList(HotelDatabase.roomTypes));
        roomTypeCombo.setConverter(new javafx.util.StringConverter<RoomType>() {
            @Override public String toString(RoomType rt) { return rt == null ? "" : rt.getName(); }
            @Override public RoomType fromString(String s) { return null; }
        });

        // Setup table columns
        colRoomNumber   .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getRoomNumber()).asObject());
        colRoomType     .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getType().getName()));
        colRoomPrice    .setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getType().getPrice()).asObject());
        colRoomCapacity .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getType().getCapacity()).asObject());
        colRoomAmenities.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getAmenities().stream().map(Amenity::getName).collect(Collectors.joining(", "))
        ));
        colRoomStatus.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().isAvailable() ? "Available" : "Occupied"));

        refreshTable();
    }

    @FXML
    private void handleAddRoom() {
        String numStr   = roomNumberField.getText().trim();
        RoomType type   = roomTypeCombo.getValue();

        if (numStr.isEmpty() || type == null) {
            addStatusLabel.setStyle("-fx-text-fill: #C0392B; -fx-font-size: 12px;");
            addStatusLabel.setText("Please fill in all fields.");
            return;
        }

        int roomNumber;
        try {
            roomNumber = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            addStatusLabel.setStyle("-fx-text-fill: #C0392B; -fx-font-size: 12px;");
            addStatusLabel.setText("Room number must be a number.");
            return;
        }

        // Check duplicate
        if (HotelDatabase.findRoom(roomNumber) != null) {
            addStatusLabel.setStyle("-fx-text-fill: #C0392B; -fx-font-size: 12px;");
            addStatusLabel.setText("Room #" + roomNumber + " already exists.");
            return;
        }

        Admin admin = (Admin) SessionStaff.getCurrentStaff();
        admin.addRoom(new Room(roomNumber, type));
        refreshTable();

        roomNumberField.clear();
        roomTypeCombo.setValue(null);
        addStatusLabel.setStyle("-fx-text-fill: #27AE60; -fx-font-size: 12px;");
        addStatusLabel.setText("Room #" + roomNumber + " added successfully!");
    }

    @FXML
    private void handleDeleteRoom() {
        Room selected = roomsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            deleteStatusLabel.setStyle("-fx-text-fill: #C0392B; -fx-font-size: 12px;");
            deleteStatusLabel.setText("Please select a room to delete.");
            return;
        }
        if (!selected.isAvailable()) {
            deleteStatusLabel.setStyle("-fx-text-fill: #C0392B; -fx-font-size: 12px;");
            deleteStatusLabel.setText("Cannot delete an occupied room.");
            return;
        }

        Admin admin = (Admin) SessionStaff.getCurrentStaff();
        admin.deleteRoom(selected);
        refreshTable();

        deleteStatusLabel.setStyle("-fx-text-fill: #27AE60; -fx-font-size: 12px;");
        deleteStatusLabel.setText("Room #" + selected.getRoomNumber() + " deleted.");
    }

    private void refreshTable() {
        roomsTable.setItems(FXCollections.observableArrayList(HotelDatabase.rooms));
    }

    @FXML private void goBack()        { SceneNavigator.navigateTo("AdminDashboard.fxml"); }
    @FXML private void handleLogout()  { SessionStaff.clear(); SceneNavigator.navigateTo("Login.fxml"); }
}
