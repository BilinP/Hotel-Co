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

    public static Reservation getCurrentReservation(){return currentReservation;}

    public static void setCurrentUser(User newUser) {currentUser = newUser;}

    public static void setCurrentReservation(Reservation newReservation) {
        currentReservation = newReservation;
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
            "WHERE ('" + Date.valueOf(startDate) + "' >= start_date AND '" +
                Date.valueOf(startDate) + "' <= end_date) " +
            "OR ('" + Date.valueOf(endDate) + "' >= start_date AND '" +
                Date.valueOf(endDate) + "' <= end_date) " +
            "OR (start_date >= '" + Date.valueOf(startDate) +
                "' AND start_date <= '" + Date.valueOf(endDate) + "') " +
            "OR (end_date >= '" + Date.valueOf(startDate) +
                "' AND end_date <= '" + Date.valueOf(endDate) + "') " +
            "AND room_type = '" + roomType.toString() + "' " +
            "AND is_cancelled = 0";
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

    public static int findEmptyRoom(LocalDate startDate, LocalDate endDate, RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        int result = 0;
        try {
            sqlQuery = "SELECT room_num " + 
            "FROM rooms " + 
            "WHERE room_num NOT IN (" + 
                "SELECT room_num " + 
                "FROM reservations " + 
                "WHERE start_date <= '" + Date.valueOf(endDate) +
                "' AND end_date >= '" + Date.valueOf(startDate) + "')" + 
            " AND room_type = '" + roomType.toString() + "'";
                System.out.println(sqlQuery);
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("room_num");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }

    public static void book(){
        currentReservation.push();
    }
}
