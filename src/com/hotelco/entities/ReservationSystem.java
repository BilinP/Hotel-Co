package com.hotelco.entities;

import com.hotelco.RoomType;
import com.hotelco.connections.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Class that 
 */
public class ReservationSystem {
    private static Connection connection = DatabaseConnection.connectDB();

    private static User currentUser;

    private static Reservation currentReservation;

    public static User getCurrentUser(){return currentUser;}

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static void logout()
    {
        currentUser = new User();
    }

    public static Connection getDatabaseConnection() {
        //FIXME: check if open, open if not
        return connection;
    }

    public static boolean checkAvailability(LocalDate startDate, LocalDate endDate, RoomType roomType){
        return (countRooms(roomType) - countOccupiedRooms(startDate, endDate, roomType)) > 0;
    }

    public static int countOccupiedRooms(LocalDate startDate, LocalDate endDate, RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        int result = 0;
        try {
            sqlQuery = "SELECT COUNT(reservations.room_num) AS total FROM reservations " + 
            "INNER JOIN rooms ON reservations.room_num = rooms.room_num " + 
            "WHERE start_date <= '" + Date.valueOf(startDate) +
            "' AND end_date >= '" + Date.valueOf(endDate) +
            "' AND room_type = '" + roomType.toString() + "'";
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }

    public static int countRooms(RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        int result = 0;
        try {
            sqlQuery = "SELECT COUNT(room_num) AS total FROM rooms WHERE room_type = '" +
                roomType.toString() + "'";
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }

    public static int book(){
        
    }
}
