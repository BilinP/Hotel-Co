package com.hotelco.connections;
import com.hotelco.TicketType;
import com.hotelco.entities.ReservationSystem;

import java.sql.*;

public class UserControl {
    //pre-condition: input is validated
    //creates a new user in the database
    public static void CreateUser
            (String firstName, String lastName, String email, String phone, String password) {

        Connection con = null;
        PreparedStatement p = null;

        con = ReservationSystem.GetDatabaseConnection();

        try {

            String query = "insert into users " +
                    "(first_name, last_name, email, phone, password, id)" +
                    " values (?, ?, ?, ?, ?, ?)";
            int nextTkt = Tickets.GetNextTicket(TicketType.CUSTOMER);

            p = con.prepareStatement(query);
            p.setString(1, firstName);
            p.setString(2, lastName);
            p.setString(3, email);
            p.setString(4, phone);
            p.setString(5, password);
            p.setInt(6, nextTkt);
            p.execute();

            /*System.out.println("User with following details created:\n" +
                    "First Name: " + firstName +
                    "\nLast Name: " + lastName +
                    "\nEmail: " + email +
                    "\nPhone number: " + phone +
                    "\nPassword: " + password +
                    "\nId: " + nextTkt);
             */

            Tickets.SetNextTicket(TicketType.CUSTOMER, nextTkt + 1);
            con.close();
        }

        // Catch block to handle exception
        catch (SQLException e) {

            // Print exception pop-up on screen
            System.out.println(e);
        }
    }
}
