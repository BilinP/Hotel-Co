package com.hotelco.utilities;

import com.hotelco.entities.ReservationSystem;
import java.sql.*;

public class DatabaseUtil{

    public static boolean doesIdExist(Integer userId){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            sqlQuery = "SELECT count(*) as total FROM users where user_id = " + userId;
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

    public static boolean doesEmailExist(String email){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            sqlQuery = "SELECT count(*) AS total FROM users where email = '" + email + "'";
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