/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.types;

import hotel.enums.RoomAvailability;
import hotel.enums.RoomType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mapvsnp
 */
public class TRoom {

    private IntegerProperty roomId = new SimpleIntegerProperty();
    private IntegerProperty roomNumber = new SimpleIntegerProperty();
    private StringProperty roomType = new SimpleStringProperty();
    private DoubleProperty pricePerDay = new SimpleDoubleProperty();
    private IntegerProperty noOfBeds = new SimpleIntegerProperty();
    private StringProperty availability = new SimpleStringProperty();

    public TRoom() {
    }

    public TRoom(Integer roomId, Integer roomNumber, RoomType roomType, Double pricePerDay, Integer noOfBeds, RoomAvailability availability) {
        this.roomId.set(roomId);
        this.roomNumber.set(roomNumber);
        this.roomType.set(roomType.toString());
        this.pricePerDay.set(pricePerDay);
        this.noOfBeds.set(noOfBeds);
        this.availability.set(availability.toString());
    }

    public Integer getRoomId() {
        return roomId.get();
    }

    public void setRoomId(Integer roomId) {
        this.roomId.set(roomId);
    }

    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber.get();
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public IntegerProperty roomNumberProperty() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType.get();
    }

    public void setRoomType(RoomType roomType) {
        this.roomType.set(roomType.toString());
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public Double getPricePerDay() {
        return pricePerDay.get();
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay.set(pricePerDay);
    }

    public DoubleProperty pricePerDayProperty() {
        return pricePerDay;
    }

    public Integer getNoOfBeds() {
        return noOfBeds.get();
    }

    public void setNoOfBeds(Integer noOfBeds) {
        this.noOfBeds.set(noOfBeds);
    }

    public IntegerProperty noOfBedsProperty() {
        return noOfBeds;
    }

    public String getAvailability() {
        return availability.get();
    }

    public void setAvailability(RoomAvailability availability) {
        this.availability.set(availability.toString());
    }

    public StringProperty availabilityProperty() {
        return availability;
    }

    @Override
    public String toString() {
        return "TRoom{" + "roomId=" + roomId + ", roomNumber=" + roomNumber + ", roomType=" + roomType + ", pricePerDay=" + pricePerDay + ", noOfBeds=" + noOfBeds + ", availability=" + availability + '}';
    }
    
    public void init() {
        this.roomIdProperty();
        this.roomNumberProperty();
        this.roomTypeProperty();
        this.pricePerDayProperty();
        this.noOfBedsProperty();
        this.availabilityProperty();
    }
}
