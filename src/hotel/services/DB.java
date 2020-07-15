/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mapvsnp
 */
public class DB {
    
    private static Connection conn;
    
    public static final int DUPLICATE_ERROR_CODE = 19;
    
    public static Connection connect() {
        if(conn != null) {
            return conn;
        }
        conn = null;
        String url = "jdbc:sqlite:src/db/hotels.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.err.println("error in connenting to database");
        }
        return conn;
    }
}
