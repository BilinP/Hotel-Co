package com.hotelco.entities;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hotelco.utilities.DatabaseUtil;

public class User {
    
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean isEmployee;
    private boolean isManager;
    private int reservationIds[];

    public User(int id){
        if (DatabaseUtil.doesIdExist(id)){
            //fetchById(id);
        }
    }

    public User(String emailStr){
        email = emailStr;
        if (DatabaseUtil.doesEmailExist(emailStr))
        {
            fetchByEmail(emailStr);
        }
        else{
            //FIXME: handle bad email
        }
    }

    public User(String newFirstName, String newLastName, String newEmail, String newPhone){
        if (DatabaseUtil.doesEmailExist(newEmail)){
            fetchByEmail(newEmail);
        }
        else {
            firstName = newFirstName;
            lastName = newLastName;
            email = newEmail;
            phone = newPhone;
        }
    }

    public void push(String password) {

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

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getId(){return userId;}

    public String getFirstName(){return firstName;}

    public String getLastName(){return lastName;}

    public String getEmail(){return email;}

    public boolean getIsEmployee(){return isEmployee;}

    public boolean getIsManager(){return isManager;}

    public int [] getReservations(){return reservationIds;}

    public String getSalt() {
        Connection con = null;
        PreparedStatement p = null;
        String result = null;
        ResultSet rs = null;
        // try {
        //     con = ReservationSystem.getDatabaseConnection();
        //     String sqlQuery =
        //     "SELECT salt FROM users WHERE 'user_id' = " + userId;
        //     p = con.prepareStatement(sqlQuery);
        //     rs = p.executeQuery();
        //     rs.next();
        //     result = rs.getString("salt");
        // }
        // catch (SQLException e)
        // {
        //     System.out.println(e);
        // }
        return result;
    }

     public String getHashedPassword() {
        Connection con = null;
        PreparedStatement p = null;
        String result = null;
        ResultSet rs = null;
        try {
            con = ReservationSystem.getDatabaseConnection();
            String sqlQuery =
            "SELECT hashed_password FROM users WHERE user_id = " + userId;
            p = con.prepareStatement(sqlQuery);
            rs = p.executeQuery();
            rs.next();
            result = rs.getString("hashed_password");
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return result;
    }

    public void setId(int newUserId){userId = newUserId;}

    public void setFirstName(String newFirstName){firstName = newFirstName;}

    public void setLastName(String newLastName){lastName = newLastName;}

    public void setEmail(String newEmail){email = newEmail;}

    public void fetchByEmail(String emailStr)
    {
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM users WHERE email = '" + emailStr + "'";
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            rs.next();
            userId = rs.getInt("user_id");
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
            isEmployee = rs.getBoolean("is_employee");
            isManager = rs.getBoolean("is_manager");
            //FIXME: get reservations
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    public void Push(){
    
    

    };


}
