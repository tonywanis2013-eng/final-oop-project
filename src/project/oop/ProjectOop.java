/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project.oop;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class ProjectOop {

    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {

        HotelDatabase.initialize();

        while (true) {

            System.out.println("\n--- WELCOME TO THE HOTEL SYSTEM ---");
            System.out.println("1. Guest Login");
            System.out.println("2. Guest Registration");
            System.out.println("3. Staff Login (Admin/Receptionist)");
            System.out.println("4. Add New Admin");
            System.out.println("5. Add New Receptionist");
            System.out.println("6. Show Payments Summary");
            System.out.println("7. Exit");

            System.out.print("Choice: ");

            int choice = 0;

            if (s.hasNextInt()) {
                choice = s.nextInt();
                s.nextLine();
            } else {
                System.out.println("Invalid input");
                s.next();
                continue;
            }

            switch (choice) {

                case 1 -> loginGuest();
                case 2 -> registerGuest();
                case 3 -> loginStaff();
                case 4 -> createNewAdmin();
                case 5 -> createNewrec();
                case 6 -> showPaymentsSummary();
                case 7 -> System.exit(0);

                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ================= PAYABLE TEST =================
    public static void showPaymentsSummary() {

        System.out.println("\n--- PAYABLE TEST ---");

        double total = 0;

        for (Reservation r : HotelDatabase.reservations) {
            total += r.calculatePayment();
        }

        for (Invoice i : HotelDatabase.invoices) {
            total += i.calculatePayment();
        }

        System.out.println("TOTAL REVENUE = " + total);
    }

    // ================= STAFF LOGIN =================
    public static void loginStaff() {

        System.out.print("Username: ");
        String user = s.next();

        System.out.print("Password: ");
        String pass = s.next();

        for (Staff member : HotelDatabase.staffList) {

            if (member.getUsername().equals(user) &&
                member.getPassword().equals(pass)) {

                System.out.println("Login successful: " + member.getRole());

                if (member.getRole() == Role.ADMIN) {
                    showAdminMenu((Admin) member);
                } else if (member.getRole() == Role.RECEPTIONIST) {
                    showReceptionistMenu((Receptionist) member);
                }

                return;
            }
        }

        System.out.println("Invalid staff credentials");
    }

    // ================= GUEST LOGIN =================
    public static void loginGuest() {

        System.out.print("Username: ");
        String user = s.next();

        System.out.print("Password: ");
        String pass = s.next();

        for (Guest g : HotelDatabase.guests) {

            if (g.getUsername().equals(user) &&
                g.getPassword().equals(pass)) {

                System.out.println("Welcome " + g.getUsername());
                showGuestMenu(g);
                return;
            }
        }

        System.out.println("Invalid guest credentials");
    }

    // ================= GUEST MENU =================
    public static void showGuestMenu(Guest guest) {

        System.out.println("\n--- GUEST MENU ---");
        System.out.println("1. View Rooms");
        System.out.println("2. Make Reservation");
        System.out.println("3. Checkout");
        System.out.println("4. Logout");

        int choice = s.nextInt();

        switch (choice) {

            case 1 -> guest.viewAvailableRooms();

            case 2 -> {
                System.out.print("Enter room number: ");
                int roomNum = s.nextInt();

                Room room = HotelDatabase.findRoom(roomNum);

                if (room != null) {
                    LocalDate in = LocalDate.now();
                    LocalDate out = in.plusDays(2);
                    guest.makeReservation(room, in, out);
                } else {
                    System.out.println("Room not found");
                }
            }

            case 3 -> {
                Reservation res = guest.getLatestReservation();

                if (res != null) {
                    guest.checkout(res, PaymentMethod.CASH);
                    System.out.println("Thank you!");
                } else {
                    System.out.println("No active reservation");
                }
            }

            default -> System.out.println("Invalid choice");
        }
    }

    // ================= ADMIN =================
    public static void createNewAdmin() {

        System.out.print("Username: ");
        String name = s.next();

        System.out.print("Password: ");
        String pass = s.next();

        Admin a = new Admin(name, pass, LocalDate.now(), 1);
        HotelDatabase.staffList.add(a);

        System.out.println("Admin added successfully");
    }

    // ================= RECEPTIONIST =================
    public static void createNewrec() {

        System.out.print("Username: ");
        String name = s.next();

        System.out.print("Password: ");
        String pass = s.next();

        Receptionist r = new Receptionist(name, pass, LocalDate.now(), 1);
        HotelDatabase.staffList.add(r);

        System.out.println("Receptionist added successfully");
    }

    // ================= ADMIN MENU =================
    public static void showAdminMenu(Admin admin) {

        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. View Rooms");
        System.out.println("2. Add Room");
        System.out.println("3. View Room Types");
        System.out.println("4. View Amenities");

        int choice = s.nextInt();

        switch (choice) {

            case 1 -> admin.viewAllRooms();

            case 2 -> {
                System.out.print("Room number: ");
                int num = s.nextInt();

                System.out.print("Room type: ");
                String typeName = s.next();

                RoomType type = admin.findRoomType(typeName);

                if (type != null) {
                    Room room = new Room(num, type);
                    admin.addRoom(room);
                    System.out.println("Room added");
                } else {
                    System.out.println("Type not found");
                }
            }

            case 3 -> admin.viewRoomTypes();
            case 4 -> admin.viewAmenities();

            default -> System.out.println("Invalid choice");
        }
    }

    // ================= RECEPTIONIST MENU =================
    public static void showReceptionistMenu(Receptionist re) {

        System.out.println("\n--- RECEPTIONIST MENU ---");
        System.out.println("1. View Guests");
        System.out.println("2. View Rooms");
        System.out.println("3. View Reservations");
        System.out.println("4. Add Reservation");

        int choice = s.nextInt();

        switch (choice) {

            case 1 -> re.viewGuests();
            case 2 -> re.viewRooms();
            case 3 -> re.viewReservations();

            case 4 -> {
                System.out.print("Guest username: ");
                String gName = s.next();

                Guest guest = HotelDatabase.findGuest(gName);

                System.out.print("Room number: ");
                int rNum = s.nextInt();

                Room room = HotelDatabase.findRoom(rNum);

                if (guest != null && room != null) {

                    LocalDate in = LocalDate.now();
                    LocalDate out = in.plusDays(2);

                    Reservation res = new Reservation(guest, room, in, out);
                    if (room.isAvailable()) {
   
               Reservation myres = new Reservation(guest, room, in,out);
               HotelDatabase.reservations.add(res);
               room.setAvailable(false); 
               System.out.println("Reservation successfully created!");
               } else {
                System.out.println("That room is already taken!");
                }

                    System.out.println("Reservation created");
                } else {
                    System.out.println("Invalid guest or room");
                }
            }

            default -> System.out.println("Invalid choice");
        }
    }
    public static void registerGuest()
        {
         String x;
         String y;
         LocalDate date;
         int year ;
         int month ;
         int day ;
         double balance;
         String address;
         String geninput;
         Gender gen;
         String roompr;
         System.out.println("enter your username");
         x=s.next();
         System.out.println("enter the password");
         y=s.next();
         while(y.length()<8)
         {
           System.out.println("pass must be at least 8 characters");
           y=s.next();
         }
         System.out.println("Enter year, month, and day:");
         year = s.nextInt();
         month = s.nextInt();
         day = s.nextInt();
         date = LocalDate.of(year, month, day);
         System.out.println("enter the balance");
         balance=s.nextDouble();
         while(balance<0)
         {
             System.out.println("balance can not be less than zero");
             balance=s.nextDouble();    
         }
         System.out.println("enter the address");
         address=s.next();
         System.out.println("enter the gender(MALE/FEMALE)");
         geninput=s.next().toUpperCase();
         gen=Gender.valueOf(geninput);
         System.out.println("enter the room preference");
         roompr=s.next();
         Guest g=new Guest(x,y,date,balance,address,gen,roompr);
         HotelDatabase.guests.add(g);
         System.out.println("Registration successful! You can now login.");
        }
}
 
            
            
            
        
    

