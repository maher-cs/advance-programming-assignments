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
public enum RoomType {
    SINGLE ("single"),
    DOUBLE ("double"),
    SUITE ("suite");

    private final String name;       

    private RoomType(String s) {
        this.name = s;
    }

    public boolean equalsName(String otherName) {
        return this.name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
    
    public static RoomType typeOf(String s) {
        if(s.equals(DOUBLE.name)) {
            return DOUBLE;
        } else if (s.equals(SINGLE.name)) {
            return SINGLE;
        } else if (s.equals(SUITE.name)) {
            return SUITE;
        } else {
            throw new IllegalArgumentException(s + " not found in RoomType");
        }
    }
}