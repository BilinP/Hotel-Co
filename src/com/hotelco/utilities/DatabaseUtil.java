package com.hotelco.utilities;

import com.hotelco.entities.ReservationSystem;
import java.sql.*;
/**
 * Utility class to check if user ids or emails exist.
 */
public class DatabaseUtil{
/**
 * Checks if ID exists in the database.
 * @param userId id to check
 * @return true if id exists in the database, false if not.
 */
    public static Boolean doesIdExist(Integer userId){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Boolean result = false;
        try {
            sqlQuery = "SELECT count(*) as total FROM users where user_id = " +
                userId;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total") == 1;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }
    /**
    * Checks if email exists in the database.
    * @param email email to check
    * @return true if email exists in the database, false if not.
    */
    public static Boolean doesEmailExist(String email){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Boolean result = false;
        try {
            sqlQuery = "SELECT count(*) AS total FROM users where email = '" +
                email + "'";
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total") == 1;
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    };
}