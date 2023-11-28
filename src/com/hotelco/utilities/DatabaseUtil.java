package com.hotelco.utilities;

import com.hotelco.constants.RoomType;
import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
/**
 * Utility class to check if user ids or emails exist.
 * @author Daniel Schwartz, John Lee
 */
public class DatabaseUtil{
/**
 * Checks if ID exists in the database.
 * @param userId id to check
 * @return true if id exists in the database, false if not.
 */
    public static Boolean doesIdExist(Integer userId){
        Boolean result = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery =
            "SELECT count(*) as total FROM users where user_id = " + userId;
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total") == 1;
            }
                    }
        catch(SQLException e)
        {
            System.out.println("doesIdExist()");
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
    * Checks if email exists in the database.
    * @param email email to check
    * @return true if email exists in the database, false if not.
    */
    public static Boolean doesEmailExist(String email){
        Boolean result = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT count(*) AS total FROM users where email = '" +
                email + "'";
        Connection con = ReservationSystem.getDatabaseConnection();
        
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total") == 1;
            }
                    }
        catch (SQLException e){
            System.out.println("DatabaseUtil.doesEmailExist()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    };

    /**
     * Gets the rate from the database for the supplied RoomType
     * @param roomType the room type for this rate request 
     * @return the rate of this room type
     */
    public static BigDecimal getRate(RoomType roomType){
        BigDecimal rate = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT rate FROM rates " +
            "WHERE type = '" + roomType.toString().toLowerCase() + "'";
        Connection con = ReservationSystem.getDatabaseConnection();
        
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                rate = rs.getBigDecimal("rate");
            }
                    }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getRate");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return rate;
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
        Connection con = ReservationSystem.getDatabaseConnection();
    
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getBoolean("total");
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.checkAvailability()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Checks the database for room availability by room type, on each and every
     * day in a range. Not very efficient, but for a maximum range of one month,
     * it will be sufficiently fast for the user.
     * 
     * @param startDate beginning of desired availabilities range
     * @param endDate end of desired availabilities range
     * @param roomType room type for which to check availability
     * @return an ordered array of availibitilies. Element 0 is the availability
     * on the startDate, element n is the availability n days after startDate.
     */
    public static Boolean[] getAvailabilities(
            LocalDate startDate, LocalDate endDate, RoomType roomType){
        Integer periodLen = new BigDecimal(
            ChronoUnit.DAYS.between(startDate, endDate)).intValueExact();
        Boolean[] result = new Boolean[periodLen];
        LocalDate checkDate = startDate;
        LocalDate nextDate;
    
        for (Integer i = 0; i < periodLen; i++){
            nextDate = checkDate.plusDays(1);
            result[i] = checkAvailability(checkDate, nextDate, roomType);
            checkDate = nextDate;
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
        Connection con = ReservationSystem.getDatabaseConnection();
        
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.countOccupiedRooms()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
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
        String sqlQuery = "SELECT COUNT(room_num) " +
            "AS total FROM rooms WHERE room_type = '" +
            roomType.toString() + "'";;
        Connection con = ReservationSystem.getDatabaseConnection();
    
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.countRooms()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
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
        Connection con = ReservationSystem.getDatabaseConnection();
    
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("room_num");
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.findEmptyRoom()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
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
        Connection con = ReservationSystem.getDatabaseConnection();
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                reservationList.add(
                    new Reservation(rs.getInt("reservation_id"), true));
                ReservationSystem.processing();
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getTodayCheckouts()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
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
            "WHERE start_date = '" + Date.valueOf(LocalDate.now()) + "' " +
            "AND is_checked_in = 0";
        Connection con = ReservationSystem.getDatabaseConnection();
    
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                reservationList.add(
                    new Reservation(rs.getInt("reservation_id"), true));
                ReservationSystem.processing();
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getTodayCheckIns()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    };

    /**
     * Gets all reservations in the database.
 * @param withReservations TODO
     * @return all reservations in the database.
     */
    public static Reservation[] getAllReservations(Boolean withReservations){
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        Reservation[] result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * FROM reservations";
        Connection con = ReservationSystem.getDatabaseConnection();
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                reservationList.add(
                    new Reservation(rs.getInt("reservation_id"), withReservations));
                ReservationSystem.processing();
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getAllReservations()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }

    /**
     * Gets all active reservations in the database. This includes reservations
     * where the date is >= today, reservations not marked as cancelled, and
     * reservations not marked as checked out.
     * @param withUserReservations whether the fetch should populate the user's
     * reservations
     * @return all active reservations in the database
     */
    public static Reservation[] getActiveReservations(Boolean withUserReservations){
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        Reservation[] result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * "
        + "FROM reservations "
        + "WHERE end_date >= '" + Date.valueOf(LocalDate.now())
        + "' AND is_cancelled = 0 "
        + "AND is_checked_out = 0";
        Connection con = ReservationSystem.getDatabaseConnection();
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                reservationList.add(
                    new Reservation(rs.getInt("reservation_id"), true));
                ReservationSystem.processing();
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getAllReservations()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    public static ResultSet getActiveReservationsRS(){
        PreparedStatement ps = null;
        ResultSet result = null;
        String sqlQuery = "SELECT * "
        + "FROM reservations "
        + "WHERE end_date >= '" + Date.valueOf(LocalDate.now())
        + "' AND is_cancelled = 0 "
        + "AND is_checked_out = 0";
        Connection con = ReservationSystem.getDatabaseConnection();
        try {
            ps = con.prepareStatement(sqlQuery);
            result = ps.executeQuery();
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getAllReservationsRS()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Finds the amount of vacant rooms at the current date
     * @return the number of vacant rooms
     */
    public static Integer findVacantRoomSize(){
        
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT room_num " + 
            "FROM rooms " + 
            "WHERE room_num NOT IN (" + 
                "SELECT room_num " + 
                "FROM reservations " + 
                "WHERE start_date <= '" + Date.valueOf(LocalDate.now()) +
                "' OR end_date >= '" + Date.valueOf(LocalDate.now()) + "') "; 
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result++;
            }
        }
        catch (SQLException e){
            System.out.println("ReservationSystem.findEmptyRoom()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;

    }
    /**
     * Gives the amount of revenue made within the current day
     * @return revenue
     */
    public static BigDecimal getTodayRevenue(){
        
        BigDecimal result = new BigDecimal(0);
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT amount " + 
            "FROM payments " + 
            "WHERE time > '" + Date.valueOf(LocalDate.now()) + "'";
            System.out.println(sqlQuery);
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            while(rs.next()){
                result = result.add(rs.getBigDecimal("amount"));
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.getTodayRevenue()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Gives an integer for the number of check-ins within the current day
     * @return number of check-ins
     */
    public static Integer countTodayCheckIns(){   
        Integer result = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT COUNT(*) AS total " + 
            "FROM reservations " + 
            "WHERE startDate = '" + Date.valueOf(LocalDate.now()) + "'";
            System.out.println(sqlQuery);
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.countTodayCheckIns()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Counts the amount of available rooms within the current day
     * @return number of available rooms
     */
    public static Integer countAvailableRooms(RoomType roomType){
        Integer result = 0;
        LocalDate today = LocalDate.now();
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
                    "WHERE start_date <= '" + Date.valueOf(today) + "' " +
                    "AND end_date >= '" + Date.valueOf(today) + "') " +
                "AND room_type = '" + roomType.toString() + "') " +
                "AS T";
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("DatabaseUtil.countAvailableRooms()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
}