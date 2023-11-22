package com.hotelco.utilities;

import com.hotelco.constants.RoomType;
import com.hotelco.entities.ReservationSystem;

import java.math.BigDecimal;
import java.sql.*;
/**
 * Utility class to check if user ids or emails exist.
 * @author Daniel Schwartz
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
    };
}