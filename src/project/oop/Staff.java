/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;

public abstract class Staff {
    
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected Role role;
    protected int workingHours;

    //Constructor
    public Staff(String username, String password,
                 LocalDate dateOfBirth, Role role, int workingHours) {

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setRole(role);
        setWorkingHours(workingHours);
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
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

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
    }

    public void setWorkingHours(int workingHours) {
        if (workingHours <= 0) {
            System.out.println("Error: Hours cannot be negative");
            return;
        }
        this.workingHours = workingHours;
    }
    // Behaviors

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

    // toString
    @Override
    public String toString() {
        return "Staff{" +
                "username='" + username + '\'' +
                ", role=" + role +
                ", workingHours=" + workingHours +
                '}';
    }  
}
