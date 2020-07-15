/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.enums;

/**
 *
 * @author mapvsnp
 */
public enum RoomAvailability {
    YES ("yes"),
    NO ("no");
    
    private final String name;
    
    private RoomAvailability(String s) {
        this.name = s;
    }
    
    public boolean equalsName(String otherName) {
        return this.name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
    
    public static RoomAvailability typeOf(String s) {
        if(s.equals(YES.name)) {
            return YES;
        } else if (s.equals(NO.name)) {
            return NO;
        } else {
            throw new IllegalArgumentException(s + " not found in RoomAvailability");
        }
    }
}
