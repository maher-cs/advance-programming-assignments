/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.models;

import hotel.dto.AddRoomDto;
import hotel.enums.RoomAvailability;
import hotel.enums.RoomType;
import hotel.services.DB;
import hotel.types.TRoom;
import hotel.ui.rooms.roomsmanage.RoomsManageController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mapvsnp
 */
public class Room {
    
    public TRoom addRoom(AddRoomDto addRoomDto) throws SQLException {
        TRoom addedRoom = null;
        Integer roomNumber = addRoomDto.getRoomNumber();
        RoomType roomType = addRoomDto.getRoomType();
        Double pricePerDay = addRoomDto.getPricePerDay();
        Integer noOfBeds = addRoomDto.getNoOfBeds();
        RoomAvailability availability = addRoomDto.getAvailability();
        
        String sql = "INSERT INTO rooms" + 
                " (number, type, price_per_day, no_of_beds, availability)" + 
                " VALUES(?,?,?,?,?)";
        Connection conn = DB.connect();
        PreparedStatement st;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, roomNumber.toString());
            st.setString(2, roomType.toString());
            st.setString(3, pricePerDay.toString());
            st.setString(4, noOfBeds.toString());
            st.setString(5, availability.toString());
            st.executeUpdate();
            addedRoom = getRoomByNumber(roomNumber);
        } catch (SQLException ex) {
            throw ex;
        }
        return addedRoom;
    }
    
    public ObservableList<TRoom> getAllRooms() {
        String sql = "SELECT * FROM rooms";
        Connection conn = DB.connect();
        PreparedStatement st;
        ResultSet rs;
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        ObservableList<TRoom> rooms = FXCollections.observableArrayList();
        try {
            while(rs.next()) {
                TRoom typeRoom = new TRoom();
                typeRoom.setRoomId(rs.getInt("id"));
                typeRoom.setRoomNumber(rs.getInt("number"));
                typeRoom.setRoomType(RoomType.typeOf(rs.getString("type")));
                typeRoom.setNoOfBeds(rs.getInt("no_of_beds"));
                typeRoom.setPricePerDay(rs.getDouble("price_per_day"));
                typeRoom.setAvailability(RoomAvailability.typeOf(rs.getString("availability")));
                rooms.add(typeRoom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomsManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rooms;
    }
    
    public TRoom getRoomByNumber(Integer number) {
        TRoom room = null;
        String sql = "SELECT * FROM rooms WHERE number = ?";
        Connection conn = DB.connect();
        PreparedStatement st;
        ResultSet rs;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, number.toString());
            rs = st.executeQuery();
            room = new TRoom(
                rs.getInt("id"),
                rs.getInt("number"),
                RoomType.typeOf(rs.getString("type")),
                rs.getDouble("price_per_day"),
                rs.getInt("no_of_beds"),
                RoomAvailability.typeOf(rs.getString("availability"))
            );
        } catch (SQLException ex) {
            System.out.println("room not found");
            return null;
        }
        return room;
    }
    
    public int deleteRoomById(Integer id) {
        String sql = "DELETE FROM rooms WHERE id = ?";
        Connection conn = DB.connect();
        PreparedStatement st;
        ResultSet rs;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, id.toString());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoomsManageController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("room not found");
            return -1;
        }
        return 0;
    }
    
    public int updateRoom(TRoom newRoom) {
        String sql = "UPDATE rooms" + 
                " SET number = ?, type = ?, price_per_day = ?, no_of_beds = ?, availability = ?" +
                " WHERE id = ?";
        Connection conn = DB.connect();
        PreparedStatement st;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, newRoom.getRoomNumber().toString());
            st.setString(2, newRoom.getRoomType());
            st.setString(3, newRoom.getPricePerDay().toString());
            st.setString(4, newRoom.getNoOfBeds().toString());
            st.setString(5, newRoom.getAvailability());
            st.setString(6, newRoom.getRoomId().toString());
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            int status = ex.getErrorCode();
            if(status == DB.DUPLICATE_ERROR_CODE) {
                System.out.println("room number already exists");
            } else {
                Logger.getLogger(RoomsManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return status;
        }
        return 0;
    }
}
