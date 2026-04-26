/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.util.ArrayList;

public class Room {
    private int roomNumber;
    private RoomType type;
    private ArrayList<Amenity> amenities;
    private boolean available;

    // Constructor
    public Room(int roomNumber, RoomType type) {
        if (roomNumber <= 0)
            throw new IllegalArgumentException("Invalid room number");

        if (type == null)
            throw new IllegalArgumentException("Room type cannot be null");

        this.roomNumber = roomNumber;
        this.type = type;
        this.amenities = new ArrayList<>();
        this.available = true;
    }

    // Getters
    public int getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public boolean isAvailable() { return available; }
    public ArrayList<Amenity> getAmenities() { return amenities; }

    // Setters
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setType(RoomType type) {
        if (type == null)
            throw new IllegalArgumentException("Invalid room type");
        this.type = type;
    }

    //  Manage Amenities
    public void addAmenity(Amenity amenity) {
        if (amenity == null)
            throw new IllegalArgumentException("Amenity cannot be null");

        amenities.add(amenity);
    }

    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
    }
    

    // toString
    @Override
    public String toString() {
        return "Room #" + roomNumber +
                " | " + type +
                " | Available: " + available +
                " | Amenities: " + amenities;
    }   
}
