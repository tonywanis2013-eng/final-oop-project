package project.oop;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation implements payable {

    private static int counter = 1;
    private final int id;

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
        this.id = counter++;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.PENDING;

        room.setAvailable(false);
    }

    @Override
    public double calculatePayment() {
        return room.getType().getPrice() * getNumberOfNights();
    }

    // Getters
    public int getId()                    { return id; }
    public Guest getGuest()               { return guest; }
    public Room getRoom()                 { return room; }
    public LocalDate getCheckInDate()     { return checkInDate; }
    public LocalDate getCheckOutDate()    { return checkOutDate; }
    public ReservationStatus getStatus()  { return status; }
    public long getNumberOfNights()       { return ChronoUnit.DAYS.between(checkInDate, checkOutDate); }

    // Setter
    public void setStatus(ReservationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "#" + id + " - Room " + room.getRoomNumber() +
                " (" + checkInDate + " to " + checkOutDate + ") | " + status;
    }
}