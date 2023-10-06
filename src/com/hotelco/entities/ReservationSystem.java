package com.hotelco.entities;

import com.hotelco.connections.DatabaseConnection;

import java.sql.Connection;

public class ReservationSystem {
    private static Connection connection = DatabaseConnection.connectDB();

    public static Connection GetDatabaseConnection() {
        //check if open, open if not
        return connection;
    }
}
