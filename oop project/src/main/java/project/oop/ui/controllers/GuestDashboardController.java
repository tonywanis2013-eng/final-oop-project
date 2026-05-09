package project.oop.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import project.oop.HotelDatabase;
import project.oop.Guest;
import project.oop.Reservation;
import project.oop.ReservationStatus;
import project.oop.util.SceneNavigator;
import project.oop.util.Session;

import java.util.List;
import java.util.stream.Collectors;

public class GuestDashboardController {

    @FXML private Label guestNameLabel;
    @FXML private Label balanceLabel;
    @FXML private Label reservationCountLabel;
    @FXML private Label prefLabel;
    @FXML private Label profileUsername;
    @FXML private Label profileGender;
    @FXML private Label profileAddress;
    @FXML private Label profileDob;
    @FXML private Label latestReservationLabel;

    @FXML
    public void initialize() {
        Guest guest = Session.getCurrentGuest();
        if (guest == null) return;

        guestNameLabel.setText(guest.getUsername());
        balanceLabel.setText(String.format("$%.2f", guest.getBalance()));

        String pref = guest.getRoomPreferences();
        prefLabel.setText(pref == null || pref.isEmpty() ? "—" : pref);

        profileUsername.setText(guest.getUsername());
        profileGender.setText(guest.getGender().toString());
        profileAddress.setText(guest.getAddress());
        profileDob.setText(guest.getDateOfBirth().toString());

        List<Reservation> myReservations = HotelDatabase.reservations.stream()
                .filter(r -> r.getGuest().equals(guest))
                .collect(Collectors.toList());

        long active = myReservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.PENDING ||
                             r.getStatus() == ReservationStatus.CONFIRMED)
                .count();
        reservationCountLabel.setText(String.valueOf(active));

        Reservation latest = guest.getLatestReservation();
        if (latest == null) {
            latestReservationLabel.setText("No active reservation.");
        } else {
            latestReservationLabel.setText(
                    "Room " + latest.getRoom().getRoomNumber() +
                    " | " + latest.getCheckInDate() + " → " + latest.getCheckOutDate() +
                    " | " + latest.getStatus()
            );
        }
    }

    @FXML private void showDashboard()    { SceneNavigator.navigateTo("GuestDashboard.fxml"); }
    @FXML private void showRooms()        { SceneNavigator.navigateTo("RoomBrowsing.fxml"); }
    @FXML private void showReservations() { SceneNavigator.navigateTo("Reservation.fxml"); }
    @FXML private void showCheckout()     { SceneNavigator.navigateTo("Checkout.fxml"); }

    @FXML
    private void handleLogout() {
        Session.clear();
        SceneNavigator.navigateTo("Login.fxml");
    }
}
