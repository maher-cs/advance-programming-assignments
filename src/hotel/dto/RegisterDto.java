/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.dto;

/**
 *
 * @author mapvsnp
 */
public class RegisterDto {
    
    private String username;
    private String email;
    private String password;
    private String mobile;
    private String sid;
    private String firstname;
    private String lastname;

    public RegisterDto(String username, String email, String password, String mobile, String sid, String firstname, String lastname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.sid = sid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getSid() {
        return sid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "RegisterDto{" + "username=" + username + ", email=" + email + ", password=" + password + ", mobile=" + mobile + ", sid=" + sid + ", firstname=" + firstname + ", lastname=" + lastname + '}';
    }
    
}
