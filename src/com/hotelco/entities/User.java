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
/**
 * Maintains details of a users information.
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class User {
    /**
     * Represents the user ID.
     */
    private Integer userId;
    /**
     * Represents the first name of a user.
     */
    private String firstName;
    /**
     * Represents the last name of a user.
     */
    private String lastName;
    /**
     * Represents the email of a user.
     */
    private String email;
    /**
     * Represents the phone number of a user.
     */
    private String phone;
    /**
     * Represents the employee status of a user.
     */
    private boolean isEmployee;
    /**
     * Represents the manager status of a user.
     */
    private boolean isManager;
    /**
     * Represents an array of reservations a user may have.
     */
    private Reservation[] reservations;
    /**
     * Fetches a user data through their ID.
     * @param id
     */
    public User(Integer id){
        if (DatabaseUtil.doesIdExist(id)){
            userId = id;
            fetch();
        }
    }
    /**
     * Fetches the user data through their email.
     * @param emailStr
     */
    public User(String emailStr){
        if (DatabaseUtil.doesEmailExist(emailStr))
        {
            email = emailStr;
            fetch();
        }
    }
    /**
     * Creates a User object with supplied details
     * @param newFirstName
     * @param newLastName
     * @param newEmail
     * @param newPhone
     */
    public User(String newFirstName, String newLastName, String newEmail, String newPhone){
        firstName = newFirstName;
        lastName = newLastName;
        email = newEmail;
        phone = newPhone;
    }
    /**
     * Getter for a user ID.
     * @return the user ID
     */
    public Integer getUserId(){return userId;}
    /**
     * Getter for the first name of a user.
     * @return the first name
     */
    public String getFirstName(){return firstName;}
    /**
     * Getter for the last name of a user.
     * @return the last name
     */
    public String getLastName(){return lastName;}
    /**
     * Getter for the email of a user.
     * @return an email
     */
    public String getEmail(){return email;}
    /**
     * Getter for a boolean of an employee status.
     * @return a employee status boolean
     */
    public boolean getIsEmployee(){return isEmployee;}
    /**
     * Getter for a boolean of an manager status.
     * @return a manager status boolean
     */
    public boolean getIsManager(){return isManager;}
    /**
     * Getter for reservation(s).
     * @return a reservation
     */
    public Reservation [] getReservations(){return reservations;}
    /**
     * Getter for the salt for their repspective passwords.
     * @return  a salt digit(s)
     */
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
    /**
     * Getter for a hashed password.
     * @return a hashed password
     */
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
    /**
     * Setter for an ID.
     * @param newUserId
     */
    public void setId(Integer newUserId){userId = newUserId;}
    /**
     * Setter for a first name.
     * @param newFirstName
     */
    public void setFirstName(String newFirstName){firstName = newFirstName;}
    /**
     * Setter for a last name.
     * @param newLastName
     */
    public void setLastName(String newLastName){lastName = newLastName;}
    /**
     * Setter for an email.
     * @param newEmail
     */
    public void setEmail(String newEmail){email = newEmail;}
    /**
     * Setter for a reservation(s).
     * @param newReservations
     */
    public void setReservations(Reservation[] newReservations){reservations = newReservations;}
    /**
     * Adds a reservation to an array of reservations.
     * @param reservationToAdd
     */
    public void addReservation(Reservation reservationToAdd){
        Integer reservationsLength = reservations.length;
        Reservation[] newReservations = new Reservation[reservationsLength + 1];
        System.arraycopy(reservations, 0, newReservations, 0, reservationsLength);
        newReservations[reservationsLength] = reservationToAdd;
        reservations = newReservations;
    }
    /**
     * Fetches data of user through email.
     * @param emailStr
     */
    public void fetchByEmail(String emailStr){
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
                reservations = fetchReservations(this, true);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    /**
     * Fetches data of user through user ID.
     * @param userIdToFetch
     */
    public void fetchById(Integer userIdToFetch){
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
                email = rs.getString("email");
                firstName = rs.getString("first_name").trim();
                lastName = rs.getString("last_name").trim();
                phone = rs.getString("phone");
                isEmployee = rs.getBoolean("is_employee");
                isManager = rs.getBoolean("is_manager");
                reservations = fetchReservations(this, false);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    /**
     * Fetch tool to determine waht method to fetch by.
     */
    public void fetch(){
        if (userId == null) {
            fetchByEmail(email);
        }
        else{
            fetchById(userId);
        }
    }
    /**
     * Fetches reservation with a user with additional input for future reservations.
     * @param user
     * @param fetchOnlyFuture
     * @return the reservation results.
     */
    public Reservation[] fetchReservations(User user, boolean fetchOnlyFuture){
        Reservation tempReservation = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Room tempRoom = null;
        LocalDate tempStartDate = null;
        LocalDate tempEndDate = null;
        InvoiceDetails tempInvoiceDetails = null;
        String tempComments = null;
        Integer tempGroupSize = 0;
        Integer tempReservationId = 0;
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
                //tempInvoiceDetails = new Reservation().getInvoiceDetails();//FIXME: get real invoiceDetails
                tempComments = rs.getString("comments");
                tempGroupSize = rs.getInt("group_size");
                tempReservationId = rs.getInt("reservation_id");
                tempIsCancelled = rs.getBoolean("is_cancelled");
                tempReservation = new Reservation(
                    tempRoom, tempStartDate, tempEndDate, this, tempInvoiceDetails,
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
    /**
     * Pushes a password to the database.
     * @param password
     */
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
    /**
     * Pushes a password by default.
     */
    public void push(){
        Connection con = ReservationSystem.getDatabaseConnection();
        PreparedStatement p = null;
        String sqlQuery = null;

        if(email == null && userId == null){
            //@GAzaCSUN Can we do a pop up error message here?
        }
        else if (userId == null){
            sqlQuery =
                "INSERT INTO users " +
                "SET " +
                "first_name = " + firstName +
                ", last_name = " + lastName +
                ", email = " + email +
                ", phone = " + phone +
                ", is_employee = " + isEmployee +
                ", is_manager = " + isManager;
        } else {
            sqlQuery =
                "UPDATE users " +
                "SET first_name = '" + firstName +
                "', last_name = '" + lastName +
                "', email = '" + email +
                "', phone = '" + phone +
                "', is_employee = " + isEmployee +
                ", is_manager = " + isManager + 
                " WHERE user_id = " + userId;
        }
        try {
            p = con.prepareStatement(sqlQuery);
            p.execute();
        }
        catch (SQLException e) {
            System.out.println(e);
            System.out.println("User not updated in database");
        }
    }
}
