package com.hotelco.entities;

import com.hotelco.connections.DatabaseConnection;
import com.hotelco.utilities.RoomType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Main system that drives the application and holds the active elements
 * with which the overall system interacts. Aggregates most elements and
 * holds the open database connection.
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class ReservationSystem {
    /**
     * Generally open connection to database.
     */
    private static Connection connection = DatabaseConnection.connectDB();
    /**
     * The logged in user
     */
    private static User currentUser;
    /**
     * The single reservation held by the Reservation System for processing.
     * Should be used immediately and set to NULL after use.
     */
    private static Reservation currentReservation;
    /**
     * Gets the logged in user
     * @return the logged in user
     */
    public static User getCurrentUser(){return currentUser;}
    /**
     * Gets the active reservation held by the Reservation System
     * @return the active reservation held by the Reservation System
     */
    public static Reservation getCurrentReservation(){return currentReservation;}
    /**
     * Logs in the supplied user
     * @param newUser user to be logged in
     */
    public static void setCurrentUser(User newUser) {currentUser = newUser;}
    /**
     * Sets the active reservation to be held by the Reservation System.
     * @param newReservation reservation to be held by the Reservation System
     */
    public static void setCurrentReservation(Reservation newReservation) {
        currentReservation = newReservation;
    }
    /**
     * Logs out the logged in user.
     */
    public static void logout()
    {
        currentUser = null;
        currentReservation = null;
    }
    /**
     * Connects to the database.
     * @return connection to the database
     */
    public static Connection getDatabaseConnection() {
        try{
            if(connection.isClosed())
            {
                DatabaseConnection.connectDB();
            }   
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return connection;
    }
    /**
     * Checks the availability of a room type within a given date.
     * @param startDate start date to check
     * @param endDate end date to check
     * @param roomType room type to check
     * @return the availability of a room with supplied parameters
     */
    public static boolean checkAvailability(LocalDate startDate, LocalDate endDate, RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            sqlQuery =
            "SELECT COUNT(room_num) as total " +
            "FROM (" +
                "SELECT room_num " +
                "FROM rooms " +
                "WHERE room_num " +
                "NOT IN (" +
                    "SELECT room_num " + 
                    "FROM reservations " +
                    "WHERE start_date <= '" + Date.valueOf(endDate) + "' " +
                    "AND end_date >= '" + Date.valueOf(startDate) + "')" +
                "AND room_type = '" + roomType.toString() + "' " +
                "LIMIT 1) AS T";
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getBoolean("total");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }
    /**
     * Checks how many rooms are currently occupied.
     * @param startDate
     * @param endDate
     * @param roomType
     * @return the number of occupied rooms.
     */
    public static Integer countOccupiedRooms(LocalDate startDate, LocalDate endDate, RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Integer result = 0;
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
    /**
     * Gives an integer for the total amount of rooms.
     * @param roomType
     * @return the total amount of rooms
     */
    public static Integer countRooms(RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Integer result = 0;
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
    /**
     * Finds the next empty room of the supplied type.
     * @param startDate start date to check
     * @param endDate end date to check
     * @param roomType room type to check
     * @return gives a room number of an empty room.
     */
    public static Integer findEmptyRoom(LocalDate startDate, LocalDate endDate, RoomType roomType){
        PreparedStatement ps = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Integer result = 0;
        try {
            sqlQuery = "SELECT room_num " + 
            "FROM rooms " + 
            "WHERE room_num NOT IN (" + 
                "SELECT room_num " + 
                "FROM reservations " + 
                "WHERE start_date <= '" + Date.valueOf(endDate) +
                "' AND end_date >= '" + Date.valueOf(startDate) + "') " + 
            "AND room_type = '" + roomType.toString() + "'" + 
            "LIMIT 1";
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
    /**
     * Inserts a reservation into the database, associated with the logged in user.
     */
    public static void book(){
        currentReservation.create();
        currentUser.fetch(); //assures currentUser is immediately updated with new booking
        Reservation[] userReservations = currentUser.fetchReservations(false,false);
        ReservationSystem.currentReservation = userReservations[userReservations.length - 1];
    }
    /**
     * Cancels the "current reservation" by setting isCancelled. See currentReservation
     * member for further description of current reservation.
     */
    public static void cancelReservation(){
        currentReservation.setIsCancelled(true);
        currentReservation.push();
    }
}
