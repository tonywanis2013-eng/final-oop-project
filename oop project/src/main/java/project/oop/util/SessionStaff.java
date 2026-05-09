package project.oop.util;

import project.oop.Staff;

public class SessionStaff {
    private static Staff currentStaff;

    public static Staff getCurrentStaff()         { return currentStaff; }
    public static void  setCurrentStaff(Staff s)  { currentStaff = s; }
    public static void  clear()                   { currentStaff = null; }
}
