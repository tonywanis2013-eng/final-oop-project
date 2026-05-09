package project.oop.ui.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.oop.*;
import project.oop.util.SceneNavigator;
import project.oop.util.SessionStaff;

import java.util.stream.Collectors;

public class StaffDashboardController {

    // Sidebar
    @FXML private Label staffNameLabel;
    @FXML private Label staffRoleLabel;
    @FXML private Label pageTitle;
    @FXML private Button btnGuests;
    @FXML private Button btnRooms;
    @FXML private Button btnReservations;

    // Guests table
    @FXML private TableView<Guest>              guestsTable;
    @FXML private TableColumn<Guest, String>    colGuestUsername;
    @FXML private TableColumn<Guest, String>    colGuestGender;
    @FXML private TableColumn<Guest, String>    colGuestDob;
    @FXML private TableColumn<Guest, String>    colGuestAddress;
    @FXML private TableColumn<Guest, Double>    colGuestBalance;
    @FXML private TableColumn<Guest, String>    colGuestPref;

    // Rooms table
    @FXML private TableView<Room>               roomsTable;
    @FXML private TableColumn<Room, Integer>    colRoomNumber;
    @FXML private TableColumn<Room, String>     colRoomType;
    @FXML private TableColumn<Room, Double>     colRoomPrice;
    @FXML private TableColumn<Room, Integer>    colRoomCapacity;
    @FXML private TableColumn<Room, String>     colRoomAmenities;
    @FXML private TableColumn<Room, String>     colRoomStatus;

    // Reservations table
    @FXML private TableView<Reservation>             reservationsTable;
    @FXML private TableColumn<Reservation, Integer>  colResId;
    @FXML private TableColumn<Reservation, String>   colResGuest;
    @FXML private TableColumn<Reservation, Integer>  colResRoom;
    @FXML private TableColumn<Reservation, String>   colResCheckIn;
    @FXML private TableColumn<Reservation, String>   colResCheckOut;
    @FXML private TableColumn<Reservation, String>   colResStatus;
    @FXML private TableColumn<Reservation, Double>   colResTotal;

    @FXML
    public void initialize() {
        Staff staff = SessionStaff.getCurrentStaff();
        if (staff != null) {
            staffNameLabel.setText(staff.getUsername());
            staffRoleLabel.setText(staff.getRole().toString());
        }

        setupGuestsTable();
        setupRoomsTable();
        setupReservationsTable();

        showGuests(); // default view
    }

    // ── Setup columns ────────────────────────────────────────────

    private void setupGuestsTable() {
        colGuestUsername.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUsername()));
        colGuestGender  .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGender().toString()));
        colGuestDob     .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDateOfBirth().toString()));
        colGuestAddress .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAddress()));
        colGuestBalance .setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getBalance()).asObject());
        colGuestPref    .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRoomPreferences()));
        guestsTable.setItems(FXCollections.observableArrayList(HotelDatabase.guests));
    }

    private void setupRoomsTable() {
        colRoomNumber  .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getRoomNumber()).asObject());
        colRoomType    .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getType().getName()));
        colRoomPrice   .setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getType().getPrice()).asObject());
        colRoomCapacity.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getType().getCapacity()).asObject());
        colRoomAmenities.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getAmenities().stream().map(Amenity::getName).collect(Collectors.joining(", "))
        ));
        colRoomStatus  .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().isAvailable() ? "Available" : "Occupied"));
        roomsTable.setItems(FXCollections.observableArrayList(HotelDatabase.rooms));
    }

    private void setupReservationsTable() {
        colResId      .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colResGuest   .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGuest().getUsername()));
        colResRoom    .setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getRoom().getRoomNumber()).asObject());
        colResCheckIn .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCheckInDate().toString()));
        colResCheckOut.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCheckOutDate().toString()));
        colResStatus  .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatus().toString()));
        colResTotal   .setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().calculatePayment()).asObject());
        reservationsTable.setItems(FXCollections.observableArrayList(HotelDatabase.reservations));
    }

    // ── Navigation ───────────────────────────────────────────────

    @FXML
    public void showGuests() {
        pageTitle.setText("Guests");
        guestsTable.setVisible(true);       guestsTable.setManaged(true);
        roomsTable.setVisible(false);       roomsTable.setManaged(false);
        reservationsTable.setVisible(false); reservationsTable.setManaged(false);
        highlight(btnGuests);
        guestsTable.setItems(FXCollections.observableArrayList(HotelDatabase.guests));
    }

    @FXML
    public void showRooms() {
        pageTitle.setText("Rooms");
        guestsTable.setVisible(false);      guestsTable.setManaged(false);
        roomsTable.setVisible(true);        roomsTable.setManaged(true);
        reservationsTable.setVisible(false); reservationsTable.setManaged(false);
        highlight(btnRooms);
        roomsTable.setItems(FXCollections.observableArrayList(HotelDatabase.rooms));
    }

    @FXML
    public void showReservations() {
        pageTitle.setText("Reservations");
        guestsTable.setVisible(false);      guestsTable.setManaged(false);
        roomsTable.setVisible(false);       roomsTable.setManaged(false);
        reservationsTable.setVisible(true); reservationsTable.setManaged(true);
        highlight(btnReservations);
        reservationsTable.setItems(FXCollections.observableArrayList(HotelDatabase.reservations));
    }

    private void highlight(Button active) {
        String normal = "-fx-background-color: transparent; -fx-text-fill: #B5D4F4; " +
                        "-fx-font-size: 13px; -fx-alignment: CENTER_LEFT; " +
                        "-fx-padding: 12 20 12 20; -fx-border-width: 0; " +
                        "-fx-background-radius: 0; -fx-cursor: hand;";
        String activeStyle = "-fx-background-color: rgba(255,255,255,0.18); -fx-text-fill: white; " +
                        "-fx-font-size: 13px; -fx-alignment: CENTER_LEFT; " +
                        "-fx-padding: 12 20 12 20; -fx-border-width: 0; " +
                        "-fx-background-radius: 0; -fx-cursor: hand; -fx-font-weight: bold;";
        btnGuests.setStyle(normal);
        btnRooms.setStyle(normal);
        btnReservations.setStyle(normal);
        active.setStyle(activeStyle);
    }

    @FXML
    private void handleLogout() {
        SessionStaff.clear();
        SceneNavigator.navigateTo("Login.fxml");
    }
}
