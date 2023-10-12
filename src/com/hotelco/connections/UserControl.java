package com.hotelco.connections;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.Password;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserControl {
    //pre-condition: input is validated
    //creates a new user in the database
    public static void pushUser
            (String firstName, String lastName, String email, String phone, String password) {

        Connection con = null;
        PreparedStatement p = null;
        
        Password pass = new Password();
        String salt = pass.getSalt();
        String hashedPassword = null;

        try {
            hashedPassword = pass.encrypt(password);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println(e);
        }

        con = ReservationSystem.getDatabaseConnection();

        try {

            String query = "insert into users " +
                    "(first_name, last_name, email, phone, hashed_password, salt)" +
                    " values (?, ?, ?, ?, ?, ?)";

            p = con.prepareStatement(query);
            p.setString(1, firstName);
            p.setString(2, lastName);
            p.setString(3, email);
            p.setString(4, phone);
            p.setString(5, hashedPassword);
            p.setString(6, salt);
            p.execute();

            /*System.out.println("User with following details created:\n" +
                    "First Name: " + firstName +
                    "\nLast Name: " + lastName +
                    "\nEmail: " + email +
                    "\nPhone number: " + phone +
                    "\nPassword: " + password +
                    "\nId: " + nextTkt);
             */
        }

        // Catch block to handle exception
        catch (SQLException e) {

            // Print exception pop-up on screen
            System.out.println(e);
        }
    }
}
