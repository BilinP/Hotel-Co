package com.hotelco.entities;

import com.hotelco.connections.DatabaseConnection;
import com.hotelco.constants.DatabaseStatus;
import com.hotelco.constants.RoomType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Main system that drives the application and holds the active elements
 * with which the overall system interacts. Aggregates most elements and
 * holds the open database connection.
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class ReservationSystem {
    /**
     * Status of the database, as an enum DatabaseStatus
     */
    private static DatabaseStatus status = DatabaseStatus.NOT_CONNECTED;
    /**
     * Connection to database. Not safe to use directly. Instead, use
     * getDabaseConnection().
     */
    private static Connection connection = DatabaseConnection.connectDB();
    /**
     * The logged in user
     */
    private static User currentUser;
    /**
     * The single reservation held by the Reservation System for processing.
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
     * Gets the current status of the DB as a DatabaseStatus enum
     * @return the current status of the DB as a DatabaseStatus enum
     */
    public static DatabaseStatus getDatabaseStatus(){return status;}
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
     * Sets the current database status
     * @param newStatus current status of the DB
     */
    public static void setDatabaseStatus(DatabaseStatus newStatus){status = newStatus;}
    /**
     * Checks if the database is ready for processing
     * @return true if databse is ready, false if not
     */
    public static Boolean isReady(){return status == DatabaseStatus.READY;};
    /**
     * Logs out the logged in user.
     */
    public static void logout()
    {
        currentUser = null;
        currentReservation = null;
    }
    /**
     * Connects to the database. Reconnects if connection is closed. Changes
     * status to DatabaseStatus.PROCESSING. Every call should be coupled with
     * ReservationSystem.ready() after processing completes.
     * @return connection to the database
     */
    public static Connection getDatabaseConnection() {
        status = DatabaseStatus.PROCESSING;
        try{
            if (connection.isClosed()){
                connection = DatabaseConnection.connectDB();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("DatabaseConnection.getDatabaseConnection()");
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
    public static Boolean checkAvailability(
            LocalDate startDate, LocalDate endDate, RoomType roomType){
        Boolean result = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery =
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
        Connection con = getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getBoolean("total");
            }
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.checkAvailability()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ready();
        return result;
    }
    /**
     * Checks how many rooms are currently occupied.
     * @param startDate
     * @param endDate
     * @param roomType
     * @return the number of occupied rooms.
     */
    public static Integer countOccupiedRooms(
        LocalDate startDate, LocalDate endDate, RoomType roomType){
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT COUNT(reservations.room_num)" + 
            "AS total FROM reservations " + 
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
            "AND is_cancelled = 0";;
        Connection con = getDatabaseConnection();
        
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.countOccupiedRooms()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ready();
        return result;
    }
    /**
     * Gets an Integer for the total amount of rooms in the database by room type
     * @param roomType the room type to count
     * @return the total amount of rooms of the supplied room type
     */
    public static Integer countRooms(RoomType roomType){
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT COUNT(room_num) AS total FROM rooms WHERE room_type = '" +
                roomType.toString() + "'";;
        Connection con = getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.countRooms()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ready();
        return result;
    }
    /**
     * Finds the next empty room of the supplied type.
     * @param startDate start date to check
     * @param endDate end date to check
     * @param roomType room type to check
     * @return gives a room number of an empty room.
     */
    public static Integer findEmptyRoom(
        LocalDate startDate, LocalDate endDate, RoomType roomType){
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT room_num " + 
            "FROM rooms " + 
            "WHERE room_num NOT IN (" + 
                "SELECT room_num " + 
                "FROM reservations " + 
                "WHERE start_date <= '" + Date.valueOf(endDate) +
                "' AND end_date >= '" + Date.valueOf(startDate) + "') " + 
            "AND room_type = '" + roomType.toString() + "'" + 
            "LIMIT 1";
        Connection con = getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("room_num");
            }
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.findEmptyRoom()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ready();
        return result;
    }
    /**
     * Inserts a reservation into the database, associated with the
     * logged in user.
     */
    public static void book(){
        currentReservation.create();
    }
    /**
     * Cancels the "current reservation" by setting isCancelled. See
     * currentReservation member for further description of current reservation.
     */
    public static void cancelReservation(){
        currentReservation.setIsCancelled(true);
        currentReservation.push();
    }
    /**
     * Checks out every reservation whose check out date is today
     */
    public static void dailyCheckOut(){
        int i;
        Reservation[] todayCheckOuts = getTodayCheckouts();
        if(todayCheckOuts != null){
            for(i = 0; i < todayCheckOuts.length; i++){
                todayCheckOuts[i].checkOut();
            }
        }
        else{
            //System.out.println("No checkouts found today.");
        }
        
    }
    /**
     * Checks in every reservation whose check in date is today
     */
    public static void dailyCheckIn(){
        int i;
        Reservation[] todayCheckIns = getTodayCheckIns();
        if (todayCheckIns != null){
            for(i = 0; i < todayCheckIns.length; i++){
                // System.out.println("Checking in Reservation " +
                //     todayCheckIns[i].getReservationId());
                todayCheckIns[i].checkIn();            
            }
        }
    }
    /**
     * Gets all the reservations with today as their check out date.
     * @return the reservations with today as their check out date
     */
    public static Reservation[] getTodayCheckouts(){
        //FIXME: What to do when the daily check out doesn't run?
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        Reservation[] result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * " + 
            "FROM reservations " + 
            "WHERE end_date <= '" + Date.valueOf(LocalDate.now()) +
            "' AND is_checked_in = 1";
        Connection con = getDatabaseConnection();
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                reservationList.add(
                    new Reservation(rs.getInt("reservation_id")));
                processing();
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.getTodayCheckouts()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ready();
        return result;
    }
    /**
     * Gets all the reservations with today as their check in date.
     * @return the reservations with today as their check in date
     */
    public static Reservation[] getTodayCheckIns(){
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        Reservation[] result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * " + 
            "FROM reservations " + 
            "WHERE start_date = '" + Date.valueOf(LocalDate.now()) +
            "' AND is_checked_in = 0";
        Connection con = getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                reservationList.add(
                    new Reservation(rs.getInt("reservation_id")));
                processing();
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.getTodayCheckIns()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ready();
        return result;
    }
    /**
     * Updates the current user and reservation from the database.
     */
    public static void update(){
        if (ReservationSystem.getCurrentUser() != null){
            ReservationSystem.getCurrentUser().fetch();
        }
        if (ReservationSystem.getCurrentReservation() != null){
            ReservationSystem.getCurrentReservation().fetch();
        }
    }
    /**
     * Dummy function that would connect and request payment
     * @param amount the amount to be paid
     * @return true if payment succeeded, false if payment fails
     */
    public static Boolean requestCreditCardPayment(BigDecimal amount){
        return true;
    }
    /**
     * Makes a payment for a reservation based on their user's credit card
     * @param reservation Reservation to be paid
     * @return true if payment succeeded, false if it failed
     */
    public static Boolean makePayment(Reservation reservation){
        Payment payment = new Payment(reservation);
        return payment.getPaymentId() != null;
    }

    public static void ready(){
        ReservationSystem.setDatabaseStatus(DatabaseStatus.READY);
    }

    public static void processing(){
        ReservationSystem.setDatabaseStatus(DatabaseStatus.PROCESSING);
    }
}
