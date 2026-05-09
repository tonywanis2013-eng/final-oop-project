package project.oop.util;

import project.oop.Guest;

public class Session {
    private static Guest currentGuest;

    public static Guest getCurrentGuest()        { return currentGuest; }
    public static void  setCurrentGuest(Guest g) { currentGuest = g; }
    public static void  clear()                  { currentGuest = null; }
}
