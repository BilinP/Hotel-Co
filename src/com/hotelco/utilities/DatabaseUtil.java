package com.hotelco.utilities;

import com.hotelco.entities.ReservationSystem;
import java.sql.*;

public class DatabaseUtil{

    public static boolean doesIdExist(int userId){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            sqlQuery = "SELECT count(*) FROM users where user_id = " + userId;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1) == 1;
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }

    public static boolean doesEmailExist(String email){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            sqlQuery = "SELECT count(*) FROM users where email = '" + email + "'";
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1) == 1;
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    };
}