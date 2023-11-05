package com.hotelco.utilities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelco.entities.ReservationSystem;

public class TaxRate {
    public static BigDecimal getTaxRate(){
        Connection con = ReservationSystem.getDatabaseConnection();
        PreparedStatement ps = null;
        BigDecimal result = null;
        ResultSet rs = null;
        String sqlQuery =
            "SELECT rate FROM rates " +
            "WHERE type = 'tax'";
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if (rs.next()){
                result = rs.getBigDecimal("rate");
            }
            else {
                result = null;
                System.out.println("Tax rate not fetched");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

     public static BigDecimal getTaxMultiplier(){
        Connection con = ReservationSystem.getDatabaseConnection();
        PreparedStatement ps = null;
        BigDecimal result = null;
        ResultSet rs = null;
        String sqlQuery =
            "SELECT rate FROM rates " +
            "WHERE type = 'tax'";
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if (rs.next()){
                result = rs.getBigDecimal("rate");
            }
            else {
                result = null;
                System.out.println("Tax rate not fetched");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        if (result != null){
            result = result.divide(new BigDecimal("100"));
            result = result.add(new BigDecimal("1"));
        }
        return result;
    }
}
