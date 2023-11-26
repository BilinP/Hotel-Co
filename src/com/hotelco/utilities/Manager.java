package com.hotelco.utilities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;

import com.hotelco.constants.RoomType;
import com.hotelco.entities.ReservationSystem;

/**
 * Maintains the manager information while giving tools for the manager to
 * maintain a reservation
 * @author John Lee, Daniel Schwartz
 */
public class Manager{

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
            System.out.println("Manager.getTodayRevenue()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }

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
            System.out.println("Manager.countTodayCheckIns()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }

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
