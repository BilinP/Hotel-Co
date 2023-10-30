package com.hotelco.utilities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hotelco.constants.RoomType;
import com.hotelco.entities.ReservationSystem;

public class DailyRates {
    public static BigDecimal getRoomRate(RoomType roomType){
        Connection con = ReservationSystem.getDatabaseConnection();
        PreparedStatement ps = null;
        BigDecimal result = null;
        ResultSet rs = null;
        String sqlQuery =
            "SELECT rate FROM rates," +
            "WHERE type = '" + roomType.toString().toLowerCase(null) +
            "'";
        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if (rs.next()){
                result = rs.getBigDecimal("rate");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
            System.out.println("User not updated in database");
        }
        return result;
    }
}
