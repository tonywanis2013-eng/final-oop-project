/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project.oop;
import java.util.Scanner;
import java.time.LocalDate;

/**
 *
 * @author tawfik
 */
public class ProjectOop 
{
     private static Scanner s = new Scanner(System.in);
   
   

   
    public static void main(String[] args) 
    {
        HotelDatabase.initialize();
         
       while (true) 
       {
            System.out.println("\n--- WELCOME TO THE HOTEL SYSTEM ---");
            System.out.println("1. Guest Login");
            System.out.println("2. Guest Registration");
            System.out.println("3. Staff Login (Admin/Receptionist)");
            System.out.println("4. add new admin");
            System.out.println("5. add new receptionist");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            int choice=0;
            if(s.hasNextInt())
            {
            choice = s.nextInt();
            s.nextLine(); 
            }
            else
            {
                System.out.println("invalid");
                s.next();
                continue;
            }

            switch (choice) {
                case 1 -> loginGuest();
                case 2 -> registerGuest();
                case 3 -> loginStaff();
                case 4 -> createNewAdmin();
                case 5 -> createNewrec();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
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
        public static void loginStaff()
        {
            System.out.print("Username: ");
            String user = s.next();
            System.out.print("Password: ");
            String pass = s.next();
            for (Staff member : HotelDatabase.staffList) 
            {
              if (member.getUsername().trim().equalsIgnoreCase(user.trim()) && member.getPassword().trim().equals(pass.trim())) {
            System.out.println("Login successful! Role: " + member.getRole());
            
            // Branching based on role
             if (member.getRole() == Role.ADMIN) 
             {
                showAdminMenu((Admin) member);
             } 
             else if (member.getRole() == Role.RECEPTIONIST) 
             {
               showReceptionistMenu((Receptionist) member);
             }
              return;
             }
            }
        System.out.println("Error: Staff member not found.");
        }
        public static void loginGuest() {
        System.out.print("Username: ");
        String user = s.next();
        System.out.print("Password: ");
        String pass = s.next();

        for (Guest g : HotelDatabase.guests) {
        if (g.getUsername().equals(user) && g.getPassword().equals(pass)) {
            System.out.println("Login successful! Welcome " + g.getUsername());
            showGuestMenu(g);
            return;
          }
        }
        System.out.println("Error: Invalid guest credentials.");
        }
        public static void showAdminMenu(Admin admin) 
        {
          System.out.println("\n--- ADMIN DASHBOARD ---");
          System.out.println("1-view room types");
          System.out.println("2-view Amenities");
          System.out.println("3-Add room");
          System.out.println("4-view all rooms");
          System.out.print("Choice: ");
          int choice = s.nextInt();
            s.nextLine(); 
            switch (choice) 
            {
                case 1 -> admin.viewRoomTypes();
                case 2 -> admin.viewAmenities();
                case 3 ->
                {
                    System.out.print("Enter Room Number: ");
                    int roomNum = s.nextInt();
                    s.nextLine();
                    System.out.println("enter room type");
                    String y=s.next();
                    RoomType roomtype= admin.findRoomType(y);
                    if (roomtype != null) 
                    {
                       Room newRoom = new Room(roomNum, roomtype);
                       admin.addRoom(newRoom);
                       System.out.println("Room added successfully!");
                  } 
                  else 
                  {
                    System.out.println("Error: Room type '" + y + "' does not exist.");
                  }                                      
                   System.out.println("Room added successfully!");
                  }  
                  case 4 -> admin.viewAllRooms(); 
                  default -> System.out.println("Invalid choice.");         
            }
        }
        public static void showReceptionistMenu(Receptionist re) 
        {
          System.out.println("--- RECEPTIONIST MENU ---");
          System.out.println("1-view Guests");
          System.out.println("2-view rooms");
          System.out.println("3-view reservations");
          System.out.println("4-add reservation");
          System.out.print("Choice: ");
           int choice = s.nextInt();
            s.nextLine(); 
           switch (choice)
           {
               case 1 -> re.viewGuests();
               case 2 -> re.viewRooms();
               case 3 -> re.viewReservations();
               case 4->
               {
                   System.out.print("Enter Guest Username: ");
                   String gName = s.next();
                   Guest guest = HotelDatabase.findGuest(gName); 

                  System.out.print("Enter Room Number: ");
                  int rNum = s.nextInt();
                  Room room = HotelDatabase.findRoom(rNum);  
                  LocalDate checkIn = LocalDate.now(); 
                  LocalDate checkOut = checkIn.plusDays(2);

                  if (guest != null && room != null) 
                  {
                   try 
                   {          
                     Reservation res = new Reservation(guest, room, checkIn, checkOut);
                     HotelDatabase.reservations.add(res);
                     System.out.println("Reservation created successfully!");
                    } 
                   catch (Exception e) 
                   {
                     System.out.println("Error: " + e.getMessage());
                   }
                   } 
                  else 
                  {
                   System.out.println("Error: Guest or Room not found.");
                  }
               }
               default -> System.out.println("invalid choice");          
           }
        }

       public static void showGuestMenu(Guest guest) 
       {
        System.out.println("\n--- GUEST PORTAL ---");
        System.out.println("1. View Available Rooms");
        System.out.println("2. Make a Reservation");
        System.out.println("3. Pay Invoice & Checkout");
        System.out.println("4. Logout");
        System.out.print("Choice: ");

          int choice = s.nextInt();
           switch (choice) 
           {        
            case 1 -> guest.viewAvailableRooms();
            case 2 -> 
            {
              System.out.print("Enter room number to book: ");
              int roomNum = s.nextInt(); 
              Room selectedRoom = null;
             for (Room r : HotelDatabase.rooms) {
             if (r.getRoomNumber() == roomNum) {
              selectedRoom = r;
              break;
        }
    }

    if (selectedRoom == null) {
        System.out.println("Room number does not exist.");
    } else {
        LocalDate in = LocalDate.now();
        LocalDate out = LocalDate.now().plusDays(2);
        // This calls your method which handles the "invalid" check
        guest.makeReservation(selectedRoom, in, out);
          }
            }
            case 3 -> 
            {
               Reservation res = guest.getLatestReservation(); 
               if (res != null) 
               {
                guest.checkout(res, PaymentMethod.CASH);
                System.out.println("thank you");
               } else {
                 System.out.println("Error: You do not have an active reservation to check out from.");
                }
            }        
            default -> System.out.println("invalid choice");
           }
        }
       public static void createNewAdmin() 
    {
     System.out.println("Enter new admin username:");
     String name = s.next();
     System.out.println("Enter password:");
     String pass = s.next(); 
     Admin a = new Admin(name, pass, LocalDate.now(), 1);
     HotelDatabase.staffList.add(a);
     System.out.println("New admin registered successfully!");
    }   
      public static void createNewrec()
      {
       System.out.println("Enter new receptionist username:");
       String name = s.next();
       System.out.println("Enter password:");
       String pass = s.next(); 
       Receptionist r = new Receptionist(name, pass, LocalDate.now(), 1);
       HotelDatabase.staffList.add(r);
       System.out.println("New receptionist registered successfully!");
          
      }
}
 
            
            
            
        
    

