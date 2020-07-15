/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.ui.register;

import com.github.fxrouter.FXRouter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import hotel.dto.RegisterDto;
import hotel.models.User;
import hotel.services.DB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author mapvsnp
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private JFXButton btnBackToLogin;
    @FXML
    private AnchorPane pnAnchorPane;
    
    private RequiredFieldValidator required;
    private RegexValidator emailRX;
    private RegexValidator mobileRX;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initValidators();
        this.setValidators();
    }

    @FXML
    private void onRegisterClick(ActionEvent event) {
        if(!validInputs()) {
            return;
        }
        RegisterDto registerDto = new RegisterDto(
            this.txtUserName.getText(),
            this.txtEmail.getText(),
            this.txtPassword.getText(),
            this.txtMobile.getText(),
            this.txtID.getText(),
            this.txtFirstName.getText(),
            this.txtLastName.getText()
        );
        User user = new User();
        int status = user.register(registerDto);
        if(status == DB.DUPLICATE_ERROR_CODE) {
            this.showUserExistMsg();
        }
    }

    @FXML
    private void onBackToLoginClick(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validInputs() {
        return 
            this.txtFirstName.validate()
            & this.txtLastName.validate()
            & this.txtID.validate()
            & this.txtEmail.validate()
            & this.txtMobile.validate()
            & this.txtUserName.validate()
            & this.txtPassword.validate();
    }
    
    private void initValidators() {
        required = new RequiredFieldValidator("this field is required");
        
        emailRX = new RegexValidator("this is not a valid email");
        emailRX.setRegexPattern("^(.+)@(.+)$");
        
        mobileRX = new RegexValidator("this is not a valid KSA mobile number");
        mobileRX.setRegexPattern("^05(?:[0-9] ?){8}$");
    }
    
    private void setValidators() {
        this.txtFirstName.setValidators(required);
        this.txtLastName.setValidators(required);
        this.txtID.setValidators(required);
        this.txtEmail.setValidators(required, emailRX);
        this.txtMobile.setValidators(required, mobileRX);
        this.txtUserName.setValidators(required);
        this.txtPassword.setValidators(required);
    }
    
    private void showUserExistMsg() {
        Label label = new Label("user already exists");
        label.getStyleClass().add("error-msg");
        Pane pane = pnAnchorPane;
        JFXSnackbar bar = new JFXSnackbar(pane);
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }
    
}
