package com.hotelco.entities;

import com.hotelco.connections.DatabaseConnection;

import java.sql.Connection;

public class ReservationSystem {
    private static Connection connection = DatabaseConnection.connectDB();

    private static User currentUser;

    private static Reservation currentReservation;

    public static User getCurrentUser(){return currentUser;}

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static Connection getDatabaseConnection() {
        //FIXME: check if open, open if not
        return connection;
    }
}
