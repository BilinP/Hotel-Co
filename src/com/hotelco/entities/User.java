package com.hotelco.entities;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.hotelco.entities.Reservation.InvoiceDetails;
import com.hotelco.utilities.DatabaseUtil;

public class User {
    
    private int userId;

    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String phone;
    
    private boolean isEmployee;
    
    private boolean isManager;
    
    private Reservation[] reservations;

    public User(){}

    public User(int id){
        if (DatabaseUtil.doesIdExist(id)){
            fetchById(id);
        }
    }

    public User(String emailStr){
        email = emailStr;
        if (DatabaseUtil.doesEmailExist(emailStr))
        {
            fetchByEmail(emailStr);
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

    public int getUserId(){return userId;}

    public String getFirstName(){return firstName;}

    public String getLastName(){return lastName;}

    public String getEmail(){return email;}

    public boolean getIsEmployee(){return isEmployee;}

    public boolean getIsManager(){return isManager;}

    public Reservation [] getReservations(){return reservations;}

    public String getSalt() {
        Connection con = null;
        PreparedStatement p = null;
        String result = null;
        ResultSet rs = null;
        try {
            con = ReservationSystem.getDatabaseConnection();
            String sqlQuery =
            "SELECT salt FROM users WHERE user_id = " + userId;
            p = con.prepareStatement(sqlQuery);
            rs = p.executeQuery();
            if(rs.next()){
                result = rs.getString("salt");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
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
            if(rs.next()){
                result = rs.getString("hashed_password");
            }
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
            if(rs.next()){
                userId = rs.getInt("user_id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                phone = rs.getString("phone");
                isEmployee = rs.getBoolean("is_employee");
                isManager = rs.getBoolean("is_manager");
                reservations = fetchReservations(userId, true);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    public void fetchById(int userIdToFetch)
    {
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM users WHERE user_id = " + userIdToFetch;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                userId = userIdToFetch;
                email = rs.getString("email");
                firstName = rs.getString("first_name").trim();
                lastName = rs.getString("last_name").trim();
                phone = rs.getString("phone");
                isEmployee = rs.getBoolean("is_employee");
                isManager = rs.getBoolean("is_manager");
                reservations = fetchReservations(userIdToFetch, false);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

public Reservation[] fetchReservations(int userId, boolean fetchOnlyFuture){
    Reservation tempReservation = new Reservation();
    PreparedStatement ps = null;
    Connection con = null;
    String sqlQuery = null;
    ResultSet rs = null;
    Room tempRoom = null;
    LocalDate tempStartDate = null;
    LocalDate tempEndDate = null;
    User tempUser = null;
    InvoiceDetails tempInvoiceDetails = null;
    String tempComments = null;
    int tempGroupSize = 0;
    int tempReservationId = 0;
    boolean tempIsCancelled = false;
    ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
    Reservation[] result = null;

        try {
        sqlQuery = "SELECT * FROM reservations WHERE user_id = " + userId;
        if (fetchOnlyFuture) {
            sqlQuery += " AND CURDATE() <= end_date";
        }
        con = ReservationSystem.getDatabaseConnection();
        ps = con.prepareStatement(sqlQuery);
        rs = ps.executeQuery();
        
        while(rs.next()){
            tempRoom = new Room(rs.getInt("room_num"));
            tempStartDate = rs.getDate("start_date").toLocalDate();
            tempEndDate = rs.getDate("end_date").toLocalDate();
            tempUser = new User(rs.getInt("user_id"));
            tempInvoiceDetails = new Reservation().getInvoiceDetails();//FIXME: get real invoiceDetails
            tempComments = rs.getString("comments");
            tempGroupSize = rs.getInt("groupSize");
            tempReservationId = rs.getInt("reservation_id");
            tempIsCancelled = rs.getBoolean("is_cancelled");
            tempReservation = new Reservation(
                tempRoom, tempStartDate, tempEndDate, tempUser, tempInvoiceDetails,
                tempComments, tempGroupSize, tempReservationId, tempIsCancelled);
            reservationList.add(tempReservation);
        }
        result = new Reservation[reservationList.size()];
        reservationList.toArray(result);
    }
    catch (SQLException e){
        System.out.println(e);
    }
    return result;
}

public void push(String password) {
        
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

        Connection con = ReservationSystem.getDatabaseConnection();;
        PreparedStatement p = null;

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

    public void push(){
        Connection con = ReservationSystem.getDatabaseConnection();;
        PreparedStatement p = null;
        try {
            String sqlQuery =
                "UPDATE users " +
                "SET first_name = " + firstName +
                ", last_name = " + lastName +
                ", email = " + email +
                ", phone = " + phone +
                ", is_employee = " + isEmployee +
                ", is_manager = " + isManager;
            p = con.prepareStatement(sqlQuery);
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
}
