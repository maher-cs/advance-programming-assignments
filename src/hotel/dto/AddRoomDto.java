/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.dto;

import hotel.enums.RoomAvailability;
import hotel.enums.RoomType;

/**
 *
 * @author mapvsnp
 */
public class AddRoomDto {
    
    private Integer roomNumber;
    private RoomType roomType;
    private Double pricePerDay;
    private Integer noOfBeds;
    private RoomAvailability availability;
    
    public AddRoomDto(Integer roomNumber, RoomType roomType, Double pricePerDay, Integer noOfBeds, RoomAvailability availability) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;
        this.noOfBeds = noOfBeds;
        this.availability = availability;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public Integer getNoOfBeds() {
        return noOfBeds;
    }

    public RoomAvailability getAvailability() {
        return availability;
    }
    
    @Override
    public String toString() {
        return "AddRoomDto{" + "roomNumber=" + roomNumber.toString() + ", roomType=" + roomType.toString() + ", pricePerDay=" + pricePerDay.toString() + ", noOfBeds=" + noOfBeds.toString() + ", availability=" + availability.toString() + '}';
    }
    
}
