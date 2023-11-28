package com.hotelco.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import com.hotelco.devtools.TimerTool;
import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.ReservationCalculator;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * The RHController class is the associated controller class of the 'ReservationHistoryGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 */
public class AllReservationController extends BaseController {

    /**
     * TableView that contains every TableColumn.
     */
    @FXML
    private TableView<Reservation> table;

    /**
     * TableColumn containing information about a bookings room type.
     */
    @FXML
    private TableColumn<Reservation, String> roomType;

    /**
     * TableColumn containing information about a bookings reservation number.
     */
    @FXML
    private TableColumn<Reservation, Integer> orderNumber;

    /**
     * TableColumn containing information about a bookings check in date.
     */    
    @FXML
    private TableColumn<Reservation, LocalDate> checkInDate;

    /**
     * TableColumn containing information about a bookings check out date.
     */    
    @FXML
    private TableColumn<Reservation, LocalDate> checkOutDate;

    /**
     * TableColumn containing information about a bookings current active status.
     */    
    @FXML
    private TableColumn<Reservation, String> status;

    /**
     * TableColumn containing information about a bookings total cost.
     */    
    @FXML
    private TableColumn<Reservation, String> total;

    /**
     * Called immediately upon controller creation.
     * Sets up the parameters for the data to be displayed in each TableColumn.
     * Afterwards, it calls displayOrders().
     */
    @FXML
    private void initialize() {
            roomType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRoom().getRoomType().toPrettyString()));
            orderNumber.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationId"));
            checkInDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("startDate"));
            checkOutDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("endDate"));
            status.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getIsCancelled() ? "Cancelled" :
                cell.getValue().getEndDate().isBefore(LocalDate.now()) ? "Completed" : 
                "Active"));
            total.setCellValueFactory(cell -> {
                Reservation reservation = cell.getValue();
                return new SimpleStringProperty("$" + ReservationCalculator.calcTotal(reservation).toString());
            });
            table.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
            displayOrders();        
        Platform.runLater(() -> {

        });
    }

    /**
     * Uses an array of reservations for the current user and passes it to the TableView.
     * This will set the data in each TableColumn.
     */
    private void displayOrders() {
        TimerTool timer = new TimerTool(101);
        Reservation reservation[] = DatabaseUtil.getAllReservations(false);
        timer.print(103);
        Collections.reverse(Arrays.asList(reservation));
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(Arrays.asList(reservation));
        table.setItems(reservations);
    }
}