package com.hotelco.connections;
import com.hotelco.TicketType;
import com.hotelco.entities.ReservationSystem;

import java.sql.*;

public class Tickets {
    public static Connection con = ReservationSystem.GetDatabaseConnection();
    public static PreparedStatement p = null;
    public static ResultSet rs = null;
    public static String sql = null;

    public static int GetNextTicket(TicketType tktType) {
        try {
            switch (tktType) {
                case BOOKING:
                    sql = "SELECT ticket FROM tickets WHERE type = 'booking'";
                    break;
                case CUSTOMER:
                    sql = "SELECT ticket FROM tickets WHERE type = 'customer'";
                    break;
                case EMPLOYEE:
                    sql = "SELECT ticket FROM tickets WHERE type = 'employee'";
                    break;
            }

            p = con.prepareStatement(sql);
            rs = p.executeQuery();
            rs.next();
            return rs.getInt(1);
        }

        // Catch block to handle exception
        catch (SQLException e) {

            // Print exception pop-up on screen
            System.out.println(e);
        }
        return -1;
    }

    public static void SetNextTicket(TicketType tktType, int nextTkt) {
        try {
            sql = "UPDATE tickets SET ticket = " + nextTkt + " WHERE type = ";
            switch (tktType) {
                case BOOKING:
                    sql += "'booking'";
                    break;
                case CUSTOMER:
                    sql += "'customer'";
                    break;
                case EMPLOYEE:
                    sql = "'employee'";
                    break;
            }

            p = con.prepareStatement(sql);
            p.execute();
        }

        // Catch block to handle exception
        catch (SQLException e) {

            // Print exception pop-up on screen
            System.out.println(e);
        }
    }
}
