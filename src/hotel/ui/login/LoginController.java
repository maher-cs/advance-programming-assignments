/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.ui.login;

import com.github.fxrouter.FXRouter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import hotel.dto.LoginDto;
import hotel.models.User;
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
public class LoginController implements Initializable {

    //  Components    //
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private JFXDialog dlgError;
    
    //  validators   //
    private RequiredFieldValidator required;
    @FXML
    private AnchorPane pnAnchorPane;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        required = new RequiredFieldValidator("this field is required");
        this.setValidators();
    }    

    @FXML
    private void onLoginClick(ActionEvent event) {
        if(!this.validInputs()) {
            return;
        }
        LoginDto loginDto = new LoginDto(
            this.txtUserName.getText(),
            this.txtPassword.getText()
        );
        User user = new User();
        boolean loginSuccess = user.login(loginDto);
        if(!loginSuccess) {
            this.showWrongInputMsg();
        } else {
            try {
                FXRouter.goTo("roomsmanage");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void onRigetserClick(ActionEvent event) {
        try {
            FXRouter.goTo("register");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validInputs() {
        return 
            this.txtUserName.validate()
            & this.txtPassword.validate();
    }
    
    private void setValidators() {
        this.txtUserName.setValidators(required);
        this.txtPassword.setValidators(required);
    }
    
    private void showWrongInputMsg() {
        Label label = new Label("invalid username or password");
        label.getStyleClass().add("login-error-msg");
        Pane pane = pnAnchorPane;
        JFXSnackbar bar = new JFXSnackbar(pane);
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }
}
