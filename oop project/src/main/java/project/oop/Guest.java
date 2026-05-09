/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Guest {
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    private String roomPreferences;
    public static ArrayList<Guest> guests = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    //Constructor
    public Guest(String username, String password, LocalDate dateOfBirth,
                 double balance, String address, Gender gender, String roomPreferences) {

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        setAddress(address);
        setGender(gender);
        setRoomPreferences(roomPreferences);
    }
     //getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public double getBalance() {
        return balance;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

    public String getRoomPreferences() {
        return roomPreferences;
    }
    //setters (with validation)
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        this.password = password;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public void setAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = address;
    }

    public void setGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        this.gender = gender;
    }

    public void setRoomPreferences(String roomPreferences) {
        this.roomPreferences = roomPreferences;
    }
    //Behaviors
    // Register
    public static Guest register(String username, String password, LocalDate dob,
                                 double balance, String address, Gender gender, String pref) {

        Guest g = new Guest(username, password, dob, balance, address, gender, pref);
        HotelDatabase.guests.add(g);
        return g;
    }

    // Login
    public static Guest login(String username, String password) {
        for (int i = 0; i < HotelDatabase.guests.size(); i++) {
            Guest g = HotelDatabase.guests.get(i);
            if (g.getUsername().equals(username) && g.getPassword().equals(password)) {
                return g;
            }
        }

        throw new IllegalArgumentException("Invalid username or password");
    }
    // View Available Rooms
    public void viewAvailableRooms() {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            Room r = HotelDatabase.rooms.get(i);
            if (r.isAvailable()) {
                System.out.println(r);
            }
        }
    }
        // Make Reservation
        public Reservation makeReservation(Room room, LocalDate checkIn, LocalDate checkOut) {

            if (!room.isAvailable()) {
               System.out.println("invalid");
               return null;
            }

            Reservation res = new Reservation(this, room, checkIn, checkOut);
            room.setAvailable(false);
            HotelDatabase.reservations.add(res);
            return res;
        }

        // View Reservations
        public void viewReservations() {
            for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
                Reservation r = HotelDatabase.reservations.get(i);
                if (r.getGuest().equals(this)) {
                    System.out.println(r);
                }
            }
        }
    // Cancel Reservation
    public void cancelReservation(Reservation r) {
        r.setStatus(ReservationStatus.CANCELLED);
    }
    // Checkout
    public Invoice checkout(Reservation r, PaymentMethod method) {

        Invoice invoice = new Invoice(r, method);

        if (this.balance < invoice.getTotalAmount()) {
           System.out.println("invalid,not enough balance");
        }

        this.balance -= invoice.getTotalAmount();
        HotelDatabase.invoices.add(invoice);

        return invoice;
    }
    //toString
    @Override
    public String toString() {
        return "Guest{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                ", gender=" + gender +
                ", preferences='" + roomPreferences + '\'' +
                '}';
    } 
    public Reservation getLatestReservation() 
    {
      Reservation latest = null;
    for (Reservation r : HotelDatabase.reservations) {
        if (r.getGuest().equals(this)) {
            latest = r;
        }
    }
     return latest;
    }
}
