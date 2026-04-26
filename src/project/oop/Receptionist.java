/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;

public class Receptionist extends Staff {
    
     public Receptionist(String u, String p, LocalDate d, int h) {
        super(u, p, d, Role.RECEPTIONIST, h);
    }

    // View data
    public void viewGuests() {
        for (int i = 0; i < HotelDatabase.guests.size(); i++) {
            Guest g = HotelDatabase.guests.get(i);
            System.out.println(g);
        }
    }

    public void viewRooms() {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            Room r = HotelDatabase.rooms.get(i);
            System.out.println(r);
        }
    }

    public void viewReservations() {
        for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
            Reservation r = HotelDatabase.reservations.get(i);
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
        r.setStatus(ReservationStatus.COMPLETED);
        r.getRoom().setAvailable(true);
    }    
}
