package com.hotelco.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;

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

public class RHController extends BaseController {

    @FXML
    private TableView<Reservation> table;

    @FXML
    private TableColumn<Reservation, String> roomType;

    @FXML
    private TableColumn<Reservation, Integer> orderNumber;

    @FXML
    private TableColumn<Reservation, LocalDate> checkInDate;

    @FXML
    private TableColumn<Reservation, LocalDate> checkOutDate;

    @FXML
    private TableColumn<Reservation, String> status;

    @FXML
    private TableColumn<Reservation, String> total;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            roomType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRoom().getRoomType().toPrettyString()));
            orderNumber.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationId"));
            checkInDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("startDate"));
            checkOutDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("endDate"));
            status.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getIsCancelled() ? "Cancelled" :
                cell.getValue().getEndDate().isBefore(LocalDate.now()) ? "Completed" : 
                "Active"));
            //unimplemented
            total.setCellValueFactory(new PropertyValueFactory<Reservation, String>(null));
            table.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
            displayOrders();
        });
    }

    private void displayOrders() {
        Reservation reservation[] = ReservationSystem.getCurrentUser()
            .fetchReservations(false, true, false);
        Collections.reverse(Arrays.asList(reservation));
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(Arrays.asList(reservation));
        table.setItems(reservations);
    }

}