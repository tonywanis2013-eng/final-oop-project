/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;

public class Receptionist extends Staff implements Manageable<Reservation> {
    
      public Receptionist(String u, String p, LocalDate d, int h) {
        super(u, p, d, Role.RECEPTIONIST, h);
    }

    // ================= Manageable Implementation =================

    @Override
    public void add(Reservation r) {
        HotelDatabase.reservations.add(r);
    }

    @Override
    public void update(Reservation r) {
        r.setStatus(ReservationStatus.CONFIRMED);
    }

    @Override
    public void delete(Reservation r) {
        HotelDatabase.reservations.remove(r);
    }

    // ================= Existing Behaviors =================

    public void viewGuests() {
        for (Guest g : HotelDatabase.guests) {
            System.out.println(g);
        }
    }

    public void viewRooms() {
        for (Room r : HotelDatabase.rooms) {
            System.out.println(r);
        }
    }

    public void viewReservations() {
        for (Reservation r : HotelDatabase.reservations) {
            System.out.println(r);
        }
    }

    // Check-in
    public void checkIn(Reservation r) {
        r.setStatus(ReservationStatus.CONFIRMED);
        r.getRoom().setAvailable(false);
    }

    // Check-out
    public void checkOut(Reservation r) {
        r.setStatus(ReservationStatus.CONFIRMED);
        r.getRoom().setAvailable(true);
    }
}
