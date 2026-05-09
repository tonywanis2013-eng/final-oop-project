package project.oop.ui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import project.oop.*;
import project.oop.util.SceneNavigator;
import project.oop.util.Session;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutController {

    @FXML private Label                 guestNameLabel;
    @FXML private ComboBox<Reservation> reservationCombo;
    @FXML private Label invoiceRoom;
    @FXML private Label invoiceType;
    @FXML private Label invoiceCheckIn;
    @FXML private Label invoiceCheckOut;
    @FXML private Label invoiceNights;
    @FXML private Label invoicePrice;
    @FXML private Label invoiceTotal;
    @FXML private RadioButton rbCash;
    @FXML private RadioButton rbCredit;
    @FXML private RadioButton rbOnline;
    @FXML private ToggleGroup paymentGroup;
    @FXML private Label       statusLabel;
    @FXML private VBox        receiptBox;
    @FXML private Label       receiptDetails;

    @FXML
    public void initialize() {
        Guest guest = Session.getCurrentGuest();
        if (guest == null) return;

        guestNameLabel.setText(guest.getUsername());
        loadPendingReservations();
    }

    private void loadPendingReservations() {
        Guest guest = Session.getCurrentGuest();
        List<Reservation> pending = HotelDatabase.reservations.stream()
                .filter(r -> r.getGuest().equals(guest))
                .filter(r -> r.getStatus() == ReservationStatus.PENDING ||
                             r.getStatus() == ReservationStatus.CONFIRMED)
                .collect(Collectors.toList());
        reservationCombo.setItems(FXCollections.observableArrayList(pending));
    }

    @FXML
    private void handleReservationSelected() {
        Reservation r = reservationCombo.getValue();
        if (r == null) return;

        invoiceRoom    .setText(String.valueOf(r.getRoom().getRoomNumber()));
        invoiceType    .setText(r.getRoom().getType().getName());
        invoiceCheckIn .setText(r.getCheckInDate().toString());
        invoiceCheckOut.setText(r.getCheckOutDate().toString());
        invoiceNights  .setText(String.valueOf(r.getNumberOfNights()));
        invoicePrice   .setText(String.format("$%.2f", r.getRoom().getType().getPrice()));
        invoiceTotal   .setText(String.format("$%.2f", r.calculatePayment()));
    }

    @FXML
    private void handleConfirmPayment() {
        Reservation r = reservationCombo.getValue();
        if (r == null) { statusLabel.setText("Please select a reservation."); return; }

        Toggle selected = paymentGroup.getSelectedToggle();
        if (selected == null) { statusLabel.setText("Please select a payment method."); return; }

        Guest guest = Session.getCurrentGuest();
        if (guest.getBalance() < r.calculatePayment()) {
            statusLabel.setText("Insufficient balance ($" + String.format("%.2f", guest.getBalance()) + " available).");
            return;
        }


        PaymentMethod method;
        if (selected == rbCash)        method = PaymentMethod.CASH;
        else if (selected == rbCredit) method = PaymentMethod.CREDIT_CARD;
        else                           method = PaymentMethod.ONLINE;


        Invoice invoice = guest.checkout(r, method);


        r.setStatus(ReservationStatus.CONFIRMED);
        statusLabel.setText("");

        String methodName = method == PaymentMethod.CASH ? "Cash" :
                            method == PaymentMethod.CREDIT_CARD ? "Credit Card" : "Online";

        receiptDetails.setText(
                "Reservation #" + r.getId() + "\n" +
                "Room " + r.getRoom().getRoomNumber() + " (" + r.getRoom().getType().getName() + ")\n" +
                "Check-in: " + r.getCheckInDate() + "  |  Check-out: " + r.getCheckOutDate() + "\n" +
                "Nights: " + r.getNumberOfNights() + "  |  Price/night: $" +
                String.format("%.2f", r.getRoom().getType().getPrice()) + "\n" +
                "Amount paid: $" + String.format("%.2f", invoice.getTotalAmount()) + "\n" +
                "Payment method: " + methodName + "\n" +
                "Remaining balance: $" + String.format("%.2f", guest.getBalance())
        );
        receiptBox.setVisible(true);
        receiptBox.setManaged(true);

        reservationCombo.getItems().remove(r);
        reservationCombo.setValue(null);
        clearInvoice();
    }

    private void clearInvoice() {
        invoiceRoom.setText("—"); invoiceType.setText("—");
        invoiceCheckIn.setText("—"); invoiceCheckOut.setText("—");
        invoiceNights.setText("—"); invoicePrice.setText("—");
        invoiceTotal.setText("0.00");
    }

    @FXML private void showDashboard()    { SceneNavigator.navigateTo("GuestDashboard.fxml"); }
    @FXML private void showRooms()        { SceneNavigator.navigateTo("RoomBrowsing.fxml"); }
    @FXML private void showReservations() { SceneNavigator.navigateTo("Reservation.fxml"); }
    @FXML private void showCheckout()     { SceneNavigator.navigateTo("Checkout.fxml"); }
    @FXML private void handleLogout()     { Session.clear(); SceneNavigator.navigateTo("Login.fxml"); }
}
