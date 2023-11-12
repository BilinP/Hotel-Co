package com.hotelco.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.hotelco.constants.Constants;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.GroupSize;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ReservationLookupController extends BaseController {


    @FXML
    private ComboBox<RoomType> RoomSelection;

    /**
     * Text containing the ID of the reservation being viewed.
     */
    @FXML
    private Text reservationID;

    /**
     * Text containing the check-in date of the reservation being viewed.
     */
    @FXML
    private Text checkInDate;

    /**
     * Text containing the check-out date of the reservation being viewed.
     */
    @FXML
    private Text checkOutDate;

    /**
     * Text containing the amount of guests of the reservation being viewed.
     */
    @FXML
    private Text guestNumber;

    /**
     * Text containing the status of the reservation, e.g. whether it is active, canceled, or completed.
     */
    @FXML
    private Text status;

    /**
     * Button named 'Cancel' that calls 'cancel()' when pressed.
     */
    @FXML
    private Button cancel;

    /**
     * VBox that is the parent node to the Button 'cancel'.
     */
    @FXML
    private VBox vBox;

    @FXML
    private DatePicker checkInChange;

    @FXML
    private DatePicker checkOutChange;
    
    

    //@FXML
    //private Text guestsChange;

    //@FXML
    //private ChoiceBox roomTypeChange;

    @FXML
    private Button change;
    @FXML
    private Button check;
    @FXML
    private Text roomtype;

    /**
     * Instance of current Reservation being viewed.
     */
    private Reservation reservation;
    private int amountOfGuest;


   

    /**
     * This method will cancel the reservation currently being viewed.
     * It is triggered by pressing the 'Cancel' button.
     * @param event The 'mouse released' event that is triggered by pressing the 'Cancel' button.
     */
    @FXML
    private void cancel(MouseEvent event) {
        reservation.setIsCancelled(true);
        reservation.push();
        ReservationHistoryController rhc = (ReservationHistoryController) switchScene(FXMLPaths.ORDER_LOOKUP, event);
        rhc.setNotification("Reservation successfully canceled.");
    }

    /**
     * This method is called by pressing the 'Go Back' text.
     * It exits the 'ReservationLookupGUI' and enters the 'ReservationHistoryGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Go Back' text.
     */
    @FXML
    private void switchToMenuScene(MouseEvent event) {
        switchScene(FXMLPaths.ORDER_LOOKUP, event);
    }

    /**
     * This method will print all relevant information of a reservation onto the screen.
     * It will save the parameter locally.
     * @param reservation Instance of the reservation to be printed.
     */
    void writeReservationInfo(Reservation reservation) {
        this.reservation = reservation;
        reservationID.setText("Reservation# "+ Integer.toString(reservation.getReservationId()));
        if (reservation.getEndDate().isBefore(LocalDate.now())) {
            status.setText("Status: Completed");
            vBox.getChildren().remove(cancel);
        }
        else if (reservation.getIsCancelled()) {
            status.setText("Status: Canceled");
            status.setFill(Color.GRAY);
            vBox.getChildren().remove(cancel);
        }
        else {
            status.setText("Status: Active");
        }        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        checkInDate.setText(reservation.getStartDate().format(dateTimeFormatter));
        checkOutDate.setText(reservation.getEndDate().format(dateTimeFormatter));
        amountOfGuest=reservation.getGroupSize();
        roomtype.setText("Room Type: "+ reservation.getRoom().getRoomType().toPrettyString());
        RoomSelection.getItems().addAll(RoomType.values());
        RoomSelection.getSelectionModel().select(reservation.getRoom().getRoomType());
        guestNumber.setText("Guest: "+ Integer.toString(amountOfGuest));
        checkInChange.setValue(reservation.getStartDate());
        checkOutChange.setValue(reservation.getEndDate());
    }

    @FXML
    private void change(MouseEvent event) {
        // if (reservation.getRoom().getRoomType() == RoomType.toRoomType(roomTypeChange.getValue())){
            LocalDate start = checkInChange.getValue();
            LocalDate end = checkOutChange.getValue();
            //Integer groupSize = groupSizeChange.getValue();
            reservation.setStartDate(start);
            reservation.setEndDate(end);
            //reservation.setGroupSize(groupSize);
            reservation.push();
            writeReservationInfo(reservation);
        // }
        /*else {
            reservation.cancel(true);
            Room room = new Room(
                ReservationSystem.findEmptyRoom(
                startDate.getValue(), endDate.getValue(),
                RoomType.toRoomType(roomTypeChange)));
            reservation.setStartDate(start);
            reservation.setEndDate(end);
            reservation.setGroupSize(groupSize);
            reservation.setRoom(room);
            ReservationSystem.setCurrentReservation(reservation);
            ReservationSystem.book();
        } */
        
    }

    @FXML
    private void check(MouseEvent event) {
        LocalDate start = checkInChange.getValue();
        LocalDate end = checkOutChange.getValue();
        //Integer groupSize = groupSizeChange.getValue();
        RoomType roomtype = reservation.getRoom().getRoomType();
        //RoomType roomtype = roomTypeChange.getValue();

        if(ReservationSystem.checkAvailability(start, end, roomtype)){
            //System.out.print("true");
            change.setDisable(false);
         }else{
            // System.out.print("false");
            change.setDisable(true);
         }
    }

    @FXML
    void decreaseGuest(MouseEvent event) {
<<<<<<< Updated upstream
        amountOfGuest--;
        guestNumber.setText("Guests: "+ Integer.toString(amountOfGuest));

=======
        if (amountOfGuest > 1) {
            --amountOfGuest;
            updateRoomChoices();
            guestNumber.setText("Guest: "+ Integer.toString(amountOfGuest));
            change.setDisable(true);
        }
>>>>>>> Stashed changes
    }

    @FXML
    void increaseGuest(MouseEvent event) {
        if (amountOfGuest < Constants.MAX_CAP) {
            ++amountOfGuest;
            updateRoomChoices();
            guestNumber.setText("Guest: "+Integer.toString(amountOfGuest));
            change.setDisable(true);
        }
    }

    @FXML
    void ChangeRoom(MouseEvent event) {
        if(!(reservation.getRoom().getRoomType().equals(RoomSelection.getValue()))){
         reservation.getRoom().setRoomType(RoomSelection.getValue());
        }
         reservation.push();
        writeReservationInfo(reservation);
    }

    @FXML
    void updateGuest(MouseEvent event) {
        if(!(reservation.getGroupSize()==amountOfGuest)){
         reservation.setGroupSize(amountOfGuest);
        }
        reservation.push();
        writeReservationInfo(reservation);
    }
    

    @FXML
    void switchToPayment(MouseEvent event) {
        switchScene(FXMLPaths.PAYMENT, event);
    }

    public void updateRoomChoices(){
        RoomType temp = RoomSelection.getValue();
        RoomSelection.getItems().clear();
        switch (GroupSize.toRoomTypes(amountOfGuest)[0]){
            case DBL:
                RoomSelection.getItems().add(RoomType.DBL);
            case QUEEN:
                RoomSelection.getItems().add(RoomType.QUEEN);
                RoomSelection.getItems().add(RoomType.KING);
            case SUITE:
                RoomSelection.getItems().add(RoomType.SUITE);
        }
        RoomSelection.setValue(temp);
    }
}
