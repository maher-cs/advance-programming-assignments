/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.models;

import hotel.dto.LoginDto;
import hotel.dto.RegisterDto;
import hotel.services.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mapvsnp
 */
public class User {
    
    public int register(RegisterDto registerDto) {
        
        String firstname = registerDto.getFirstname();
        String lastname = registerDto.getLastname();
        String sid = registerDto.getSid();
        String email = registerDto.getEmail();
        String mobile = registerDto.getMobile();
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        
        String sql = "INSERT INTO users" + 
                " (firstname, lastname, sid, email, mobile, username, password)" + 
                " VALUES(?,?,?,?,?,?,?)";
        Connection conn = DB.connect();
        PreparedStatement st;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, sid);
            st.setString(4, email);
            st.setString(5, mobile);
            st.setString(6, username);
            st.setString(7, password);
            
            st.executeUpdate();
            
        } catch (SQLException ex) {
            int errorCode = ex.getErrorCode();
            if(errorCode == DB.DUPLICATE_ERROR_CODE) {
                System.out.println("user already exist");
                return DB.DUPLICATE_ERROR_CODE;
            } else {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
            
        }
        return 0;
    }
    
    public boolean login(LoginDto loginDto) {
        boolean loginSucess = false;
        
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        
        String sql = "SELECT * FROM users" +
                " WHERE username LIKE ? AND password LIKE ?";
        
        Connection conn = DB.connect();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()) {
                System.out.println("success");
                loginSucess = true;
            } else {
                System.out.println("invalid username or password!");
                loginSucess = false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loginSucess;
    }
    
}
