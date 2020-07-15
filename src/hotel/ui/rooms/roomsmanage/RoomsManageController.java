/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.ui.rooms.roomsmanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import hotel.dto.AddRoomDto;
import hotel.enums.RoomAvailability;
import hotel.enums.RoomType;
import hotel.models.Room;
import hotel.services.DB;
import hotel.types.TRoom;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author mapvsnp
 */
public class RoomsManageController implements Initializable {

    // table
    @FXML
    private TableView<TRoom> tblRooms;
    @FXML
    private TableColumn<TRoom, Integer> tblcRId;
    @FXML
    private TableColumn<TRoom, Integer> tblcRNumber;
    @FXML
    private TableColumn<TRoom, String> tblcRType;
    @FXML
    private TableColumn<TRoom, Double> tblcPPDay;
    @FXML
    private TableColumn<TRoom, Integer> tblcNBeds;
    @FXML
    private TableColumn<TRoom, String> tblcRAval;

    @FXML
    private JFXTextField txtAddRoomNo;
    @FXML
    private JFXTextField txtAddPPD;
    @FXML
    private JFXTextField txtAddNoBeds;
    @FXML
    private JFXComboBox<Label> cmbAddRoomType;
    @FXML
    private JFXComboBox<Label> cmbAddRoomAval;
    
    @FXML
    private AnchorPane pnAnchorPane;
    
    private RequiredFieldValidator required;
    private NumberValidator numberVal;
    private IntegerValidator integerVal;
    
    ObservableList<TRoom> roomsObvList;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // table
        this.roomsObvList = FXCollections.observableArrayList();
        this.getRooms();
        this.fetchRoomsDataInTable();
        
        // add room form
        this.setComboboxesValues();
        this.initValidators();
        this.setValidators();
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        this.addRoom();
    }

    @FXML
    private void onDeleteClick(ActionEvent event) {
        this.deleteRoom();
    }
    
    @FXML
    private void onEditRoomNumberCommit(TableColumn.CellEditEvent<TRoom, Integer> event) {
        TRoom room = event.getRowValue();
        room.setRoomNumber(event.getNewValue());
        this.updateRoom(room);
    }

    @FXML
    private void onEditRoomTypeCommit(TableColumn.CellEditEvent<TRoom, String> event) {
        TRoom room = event.getRowValue();
        room.setRoomType(RoomType.typeOf(event.getNewValue()));
        this.updateRoom(room);
    }

    @FXML
    private void onEditNoOfBedsCommit(TableColumn.CellEditEvent<TRoom, Integer> event) {
        TRoom room = event.getRowValue();
        room.setNoOfBeds(event.getNewValue());
        this.updateRoom(room);
    }

    @FXML
    private void onEditPricePerDayCommit(TableColumn.CellEditEvent<TRoom, Double> event) {
        TRoom room = event.getRowValue();
        room.setPricePerDay(event.getNewValue());
        this.updateRoom(room);
    }

    @FXML
    private void onEditAvailabilityCommit(TableColumn.CellEditEvent<TRoom, String> event) {
        TRoom room = event.getRowValue();
        room.setAvailability(RoomAvailability.typeOf(event.getNewValue()));
        int status = this.updateRoom(room);
    }
    
    private void getRooms() {
        Room roomModel = new Room();
        this.roomsObvList = roomModel.getAllRooms();
    }
    
    private void fetchRoomsDataInTable() {
        this.tblRooms.setItems(this.roomsObvList);
        this.tblRooms.setEditable(true);
        this.tblcRId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        this.tblcRNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        this.tblcRType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        this.tblcNBeds.setCellValueFactory(new PropertyValueFactory<>("noOfBeds"));
        this.tblcPPDay.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        this.tblcRAval.setCellValueFactory(new PropertyValueFactory<>("availability"));
        
        this.tblcRNumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.tblcRType.setCellFactory(ComboBoxTableCell.forTableColumn(getComboboxesOptions()[0]));
        this.tblcNBeds.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.tblcPPDay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        this.tblcRAval.setCellFactory(ComboBoxTableCell.forTableColumn(getComboboxesOptions()[1]));
        
        this.tblRooms.getColumns().setAll(tblcRId, tblcRNumber, tblcRType, tblcNBeds, tblcPPDay, tblcRAval);
    }
    
    private void addRoom() {
        TRoom addedRoom = null;
        if(!validInputs()) {
            return;
        }
        AddRoomDto addRoomDto = new AddRoomDto(
            Integer.parseInt(this.txtAddRoomNo.getText()),
            RoomType.typeOf(this.cmbAddRoomType.getValue().getText()),
            Double.parseDouble(this.txtAddPPD.getText()),
            Integer.parseInt(this.txtAddNoBeds.getText()),
            RoomAvailability.typeOf(this.cmbAddRoomAval.getValue().getText())
        );
        Room roomModel = new Room();
        try {
            addedRoom = roomModel.addRoom(addRoomDto);
            this.roomsObvList.add(addedRoom);
            this.clearAddRoomForm();
            this.showRoomAddedMsg();
        } catch (SQLException ex) {
            int status = ex.getErrorCode();
            if(status == DB.DUPLICATE_ERROR_CODE) {
                this.showRoomExistMsg();
            }
        }
    }
    
    private void deleteRoom() {
        Room roomModel = new Room();
        Integer roomId = this.tblRooms.getSelectionModel().getSelectedItem().getRoomId();
        int index = this.tblRooms.getSelectionModel().getSelectedIndex();
        int status = roomModel.deleteRoomById(roomId);
        if(status == 0) {
            this.showRoomDeletedMsg();
            this.roomsObvList.remove(index);
        }
    }
    
    private int updateRoom(TRoom newRoom) {
        Room roomModel = new Room();
        int status = roomModel.updateRoom(newRoom);
        if(status == 0) {
            this.showRoomUpdatedMsg();
            return 0;
        } else {
            this.showRoomExistMsg();
            return status;
        }
    }
    
    private void setComboboxesValues() {
        // room types
        this.cmbAddRoomType.getItems().add(new Label(RoomType.SINGLE.toString()));
        this.cmbAddRoomType.getItems().add(new Label(RoomType.DOUBLE.toString()));
        this.cmbAddRoomType.getItems().add(new Label(RoomType.SUITE.toString()));
        
        // availability
        this.cmbAddRoomAval.getItems().add(new Label(RoomAvailability.YES.toString()));
        this.cmbAddRoomAval.getItems().add(new Label(RoomAvailability.NO.toString()));
    }
    
    private String[][] getComboboxesOptions() {
        String[][] labels = {
            {RoomType.SINGLE.toString(), RoomType.DOUBLE.toString(), RoomType.SUITE.toString()},
            {RoomAvailability.YES.toString(),RoomAvailability.NO.toString()}
        };
        return labels;
    }
    
    private void initValidators() {
        required = new RequiredFieldValidator("this field is required");
        integerVal = new IntegerValidator("this is not a valid number");
        numberVal = new NumberValidator("this is not a valid number");
    }
    
    private void setValidators() {
        this.txtAddNoBeds.setValidators(required, integerVal);
        this.txtAddPPD.setValidators(required, numberVal);
        this.txtAddRoomNo.setValidators(required, integerVal);
        this.cmbAddRoomAval.setValidators(required);
        this.cmbAddRoomType.setValidators(required);
    }
    
    private boolean validInputs() {
        return 
            this.txtAddNoBeds.validate()
            & this.txtAddPPD.validate()
            & this.txtAddRoomNo.validate()
            & this.cmbAddRoomAval.validate()
            & this.cmbAddRoomType.validate();
    }
    
    private void clearAddRoomForm() {
        this.txtAddNoBeds.clear();
        this.txtAddPPD.clear();
        this.txtAddRoomNo.clear();
        this.cmbAddRoomAval.getSelectionModel().clearSelection();
        this.cmbAddRoomType.getSelectionModel().clearSelection();
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
    
    private void showRoomDeletedMsg() {
        Label label = new Label("room deleted succesfully");
        label.getStyleClass().add("succes-msg");
        Pane pane = pnAnchorPane;
        JFXSnackbar bar = new JFXSnackbar(pane);
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }

    private void showRoomUpdatedMsg() {
        Label label = new Label("room upudated succesfully");
        label.getStyleClass().add("succes-msg");
        Pane pane = pnAnchorPane;
        JFXSnackbar bar = new JFXSnackbar(pane);
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }
}
