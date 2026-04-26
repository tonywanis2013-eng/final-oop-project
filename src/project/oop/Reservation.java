/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation implements payable{
    
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;

    public Reservation(Guest guest, Room room,
                       LocalDate checkInDate, LocalDate checkOutDate) {

        if (checkInDate == null || checkOutDate == null ||
                !checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.PENDING;

        room.setAvailable(false);
    }

    // 🔥 Implement Payable
    @Override
    public double calculatePayment() {
        double pricePerNight = room.getType().getPrice();
        return pricePerNight * getNumberOfNights();
    }

    // getters
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public ReservationStatus getStatus() { return status; }

    // setters
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    // total nights
    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    // toString
    @Override
    public String toString() {
        return "Reservation{" +
                "guest=" + guest.getUsername() +
                ", room=" + room +
                ", status=" + status +
                '}';
    }
}