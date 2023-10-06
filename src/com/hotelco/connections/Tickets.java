package com.hotelco.connections;
import com.hotelco.TicketType;

import java.sql.*;

public class Tickets {
    public static Connection con = DatabaseConnection.connectDB();
    public static PreparedStatement p;
    public static ResultSet rs;
    public static String sql;

    public static int GetNextTicket(TicketType tktType) {
        try {
            switch (tktType) {
                case BOOKING:
                    sql = "SELECT booking_tkt FROM tickets";
                    break;
                case CUSTOMER:
                    sql = "SELECT customer_tkt FROM tickets";
                    break;
                case EMPLOYEE:
                    sql = "SELECT employee_tkt FROM tickets";
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
            switch (tktType) {
                case BOOKING:
                    sql = "UPDATE tickets SET booking_tkt = " + nextTkt;
                    break;
                case CUSTOMER:
                    sql = "UPDATE tickets SET customer_tkt = " + nextTkt;
                    break;
                case EMPLOYEE:
                    sql = "UPDATE tickets SET employee_tkt = " + nextTkt;
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
