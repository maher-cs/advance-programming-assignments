/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.ui.rooms.addroom;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import hotel.dto.AddRoomDto;
import hotel.enums.RoomAvailability;
import hotel.enums.RoomType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import hotel.models.Room;
import hotel.services.DB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author mapvsnp
 */
public class AddRoomController implements Initializable {

    @FXML
    private JFXTextField txtRoomNumber;
    @FXML
    private JFXComboBox<Label> cmbRoomType;
    @FXML
    private JFXTextField txtPricePerDay;
    @FXML
    private JFXTextField txtNoOfBeds;
    @FXML
    private JFXComboBox<Label> cmbAvailability;
    
    private RequiredFieldValidator required;
    private NumberValidator numberVal;
    private IntegerValidator integerVal;
    @FXML
    private AnchorPane pnAnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setComboboxesValues();
        this.initValidators();
        this.setValidators();
    }    

    @FXML
    private void onSaveClick(ActionEvent event) {
        this.addRoom();
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
    }
    
    private void addRoom() {
        if(!validInputs()) {
            return;
        }
        AddRoomDto addRoomDto = new AddRoomDto(
            Integer.parseInt(this.txtRoomNumber.getText()),
            RoomType.typeOf(this.cmbRoomType.getValue().getText()),
            Double.parseDouble(this.txtPricePerDay.getText()),
            Integer.parseInt(this.txtNoOfBeds.getText()),
            RoomAvailability.typeOf(this.cmbAvailability.getValue().getText())
        );
        Room roomModel = new Room();
        try {
            roomModel.addRoom(addRoomDto);
        } catch (SQLException ex) {
            int status = ex.getErrorCode();
            if(status == DB.DUPLICATE_ERROR_CODE) {
                this.showRoomExistMsg();
            }
        }
        
    }
    
    private void setComboboxesValues() {
        // room types
        this.cmbRoomType.getItems().add(new Label(RoomType.SINGLE.toString()));
        this.cmbRoomType.getItems().add(new Label(RoomType.DOUBLE.toString()));
        this.cmbRoomType.getItems().add(new Label(RoomType.SUITE.toString()));
        
        // availability
        this.cmbAvailability.getItems().add(new Label(RoomAvailability.YES.toString()));
        this.cmbAvailability.getItems().add(new Label(RoomAvailability.NO.toString()));
    }
    
    private void initValidators() {
        required = new RequiredFieldValidator("this field is required");
        integerVal = new IntegerValidator("this is not a valid number");
        numberVal = new NumberValidator("this is not a valid number");
    }
    
    private void setValidators() {
        this.txtNoOfBeds.setValidators(required, integerVal);
        this.txtPricePerDay.setValidators(required, numberVal);
        this.txtRoomNumber.setValidators(required, integerVal);
        this.cmbAvailability.setValidators(required);
        this.cmbRoomType.setValidators(required);
    }
    
    private boolean validInputs() {
        return 
            this.txtNoOfBeds.validate()
            & this.txtPricePerDay.validate()
            & this.txtRoomNumber.validate()
            & this.cmbAvailability.validate()
            & this.cmbRoomType.validate();
    }
    
    private void showRoomExistMsg() {
        Label label = new Label("room already exists");
        label.getStyleClass().add("error-msg");
        Pane pane = pnAnchorPane;
        JFXSnackbar bar = new JFXSnackbar(pane);
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }
    
    private void showRoomAddedMsg() {
        Label label = new Label("room added succesfully");
        label.getStyleClass().add("succes-msg");
        Pane pane = pnAnchorPane;
        JFXSnackbar bar = new JFXSnackbar(pane);
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }
    
}
