/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.oop;

public class RoomType {
    
     private String name;
    private double price;
    private int capacity;

    // Constructor
    public RoomType(String name, double price, int capacity) {
        setName(name);
        setPrice(price);
        setCapacity(capacity);
    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }

    // Setters (Validation)
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Invalid room type name");
        this.name = name;
    }

    public void setPrice(double price) {
        if (price <= 0)
            throw new IllegalArgumentException("Price must be positive");
        this.price = price;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be positive");
        this.capacity = capacity;
    }

    //  toString
    @Override
    public String toString() {
        return name + " | Price: " + price + " | Capacity: " + capacity;
    }
}
