/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;
import java.time.LocalDate;

public class Admin extends Staff implements Manageable<Room>
{
    public Admin(String username, String password,
                 LocalDate dateOfBirth, int workingHours) {

        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }

    
    @Override
    public void add(Room room) {
        addRoom(room);
    }

    @Override
    public void update(Room room) {
        updateRoomAvailability(room, true);
    }

    @Override
    public void delete(Room room) {
        deleteRoom(room);
    }

    
    public void viewRoomTypes() {
        for (RoomType t : HotelDatabase.roomTypes) {
            System.out.println(t);
        }
    }

    public void viewAmenities() {
        for (Amenity a : HotelDatabase.amenities) {
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
        for (RoomType t : HotelDatabase.roomTypes) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    private Amenity findAmenity(String name) {
        for (Amenity a : HotelDatabase.amenities) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public void viewAllRooms() {
        System.out.println("\n--- LIST OF ALL ROOMS ---");
        for (Room r : HotelDatabase.rooms) {
            System.out.println("Room #" + r.getRoomNumber() +
                    " | Type: " + r.getType().getName());
        }
    }
    
}
    
    

