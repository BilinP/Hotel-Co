package com.hotelco.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.DatabaseUtil;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * The RHController class is the associated controller class of the 'ReservationHistoryGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 */
public class CheckInController extends BaseController {

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
     * TableColumn containing information about a bookings total cost.
     */    
    @FXML
    private TableColumn<Reservation, String> total;


    private List<Reservation> selectedReservations = new ArrayList<>();
    /**
     * Called immediately upon controller creation.
     * Sets up the parameters for the data to be displayed in each TableColumn.
     * Afterwards, it calls displayOrders().
     */
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            roomType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRoom().getRoomType().toPrettyString()));
            orderNumber.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationId"));
            checkInDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("startDate"));
            checkOutDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("endDate"));
            //unimplemented
            total.setCellValueFactory(new PropertyValueFactory<Reservation, String>(null));

            table.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    toggle(newSelection);
                }
            });



            displayOrders();
        });
    }

    /**
     * Uses an array of reservations for the current user and passes it to the TableView.
     * This will set the data in each TableColumn.
     */
    private void displayOrders() {
        Reservation reservation[] = DatabaseUtil.getTodayCheckIns();
        Collections.reverse(Arrays.asList(reservation));
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(Arrays.asList(reservation));
        table.setItems(reservations);
    }

    private void toggle(Reservation reservation) {
        if (selectedReservations.contains(reservation)) {
            selectedReservations.remove(reservation);
        } else {
            selectedReservations.add(reservation);
        }
        updateRowStyles();
    }

    private void updateRowStyles() {
        table.getStyleClass().removeAll("table-row-cell-selectedtoggle");
    
    for (Reservation selectedReservation : selectedReservations) {
        int index = table.getItems().indexOf(selectedReservation);
    
        if (index != -1) {
            int rowIndex = index + 1; 
                System.out.print(rowIndex);
            table.lookup(".table-row-cell:index(" + rowIndex + ")").getStyleClass().add("table-row-cell-selectedtoggle");
        }
    }

}
}