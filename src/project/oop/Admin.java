/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;

public class Admin extends Staff 
{
     public Admin(String username, String password,
                 LocalDate dateOfBirth, int workingHours) {

        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }
    public void viewRoomTypes() {
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
            RoomType t = HotelDatabase.roomTypes.get(i);
            System.out.println(t);
        }
    }

    public void viewAmenities() {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            Amenity a = HotelDatabase.amenities.get(i);
            System.out.println(a);
        }
    }

    public void addRoom(Room room) {
        HotelDatabase.rooms.add(room);
    }

    public void deleteRoom(Room room) {
        if (!HotelDatabase.rooms.remove(room)) {
            throw new RuntimeException("Room not found");
        }
    }

    public void updateRoomAvailability(Room room, boolean status) {
        room.setAvailable(status);
    }

    public void addRoomType(RoomType type) {
        HotelDatabase.roomTypes.add(type);
    }

    public void updateRoomType(String oldName, String newName, double newPrice) {
        RoomType type = findRoomType(oldName);

        if (type == null) {
            throw new RuntimeException("RoomType not found");
        }

        type.setName(newName);
        type.setPrice(newPrice);
    }

    public void deleteRoomType(String name) {
        RoomType type = findRoomType(name);

        if (type == null) {
            throw new RuntimeException("RoomType not found");
        }

        HotelDatabase.roomTypes.remove(type);
    }

    public void addAmenity(Amenity amenity) {
        HotelDatabase.amenities.add(amenity);
    }

    public void updateAmenity(String oldName, String newName) {
        Amenity amenity = findAmenity(oldName);

        if (amenity == null) {
            throw new RuntimeException("Amenity not found");
        }

        amenity.setName(newName);
    }

    public void deleteAmenity(String name) {
        Amenity amenity = findAmenity(name);

        if (amenity == null) {
            throw new RuntimeException("Amenity not found");
        }

        HotelDatabase.amenities.remove(amenity);
    }

    public RoomType findRoomType(String name) {
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
            RoomType t = HotelDatabase.roomTypes.get(i);
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    private Amenity findAmenity(String name) {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            Amenity a = HotelDatabase.amenities.get(i);
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }
    public void viewAllRooms() 
    {
    System.out.println("\n--- LIST OF ALL ROOMS ---");
    for (Room r : HotelDatabase.rooms) {
        // This assumes your Room class has a way to get the number and type name
        System.out.println("Room #" + r.getRoomNumber() + " | Type: " + r.getType().getName());
    }
    }
}
    
    

