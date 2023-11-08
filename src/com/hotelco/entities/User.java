package com.hotelco.entities;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.hotelco.utilities.DatabaseUtil;
/**
 * Maintains details of a users information.
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class User {
    /**
     * Represents the unique user ID. This ID is originally generated by the
     * database itself.
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
    private Boolean isEmployee;
    /**
     * Represents the manager status of a user.
     */
    private Boolean isManager;
    /**
     * Represents all reservations associated with this user.
     */
    private Reservation[] reservations;
    /**
     * Constructs and fetches a user data through their ID.
     * @param id user id with which to fetch user
     */
    public User(Integer id){
        if (DatabaseUtil.doesIdExist(id)){
            userId = id;
            fetch();
        }
    }
    /**
     * Creates and fetches the user data through their email.
     * @param emailStr email with which to fetch user
     */
    public User(String emailStr){
        if (DatabaseUtil.doesEmailExist(emailStr))
        {
            email = emailStr;
            fetch();
        }
    }
    /**
     * Constructs a User object with supplied details. This user is not
     * concurrent with database and push() must be called immediately after
     * construction in order to insert this User in to the database
     * @param newFirstName first name
     * @param newLastName last name
     * @param newEmail email
     * @param newPhone phone number
     */
    public User(
        String newFirstName, String newLastName,
        String newEmail, String newPhone){
        firstName = newFirstName;
        lastName = newLastName;
        email = newEmail;
        phone = newPhone;
    }
    /**
     * Gets the user ID associated with this user.
     * @return the user ID
     */
    public Integer getUserId(){return userId;}
    /**
     * Gets the first name associated with this user.
     * @return the first name associated with this user
     */
    public String getFirstName(){return firstName;}
    /**
     * Gets the last name associated with this user.
     * @return the last name associated with this user
     */
    public String getLastName(){return lastName;}
    /**
     * Gets the email associated with this user.
     * @return the email associated with this user
     */
    public String getEmail(){return email;}
    /**
     * Gets the employee status associated with this user.
     * @return the employee status associated with this user
     */
    public Boolean getIsEmployee(){return isEmployee;}
    /**
     * Gets the manager status associated with this user.
     * @return the manager status associated with this user
     */
    public Boolean getIsManager(){return isManager;}
    /**
     * Gets all reservations associated with this user.
     * @return all reservations associated with this user
     */
    public Reservation [] getReservations(){return reservations;}
    /**
     * Gets the salt for this user from the database.
     * @return the salt associated with this user
     */
    public String getSalt() {
        PreparedStatement p = null;
        String result = null;
        ResultSet rs = null;
        String sqlQuery =
            "SELECT salt FROM users WHERE user_id = " + userId;
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            p = con.prepareStatement(sqlQuery);
            rs = p.executeQuery();
            if(rs.next()){
                result = rs.getString("salt");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Reservation.getSalt()");
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Gets the hashed password associated with this user.
     * @return the hashed password associated with this user
     */
     public String getHashedPassword() {
        PreparedStatement ps = null;
        String result = null;
        ResultSet rs = null;
        String sqlQuery =
            "SELECT hashed_password FROM users WHERE user_id = " + userId;
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("hashed_password");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("User.getHashedPassword()");
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Sets ID to be associated with this user.
     * @param newUserId ID to be associated with this user
     */
    public void setId(Integer newUserId){userId = newUserId;}
    /**
     * Sets first name to be associated with this user.
     * @param newFirstName first name to be associated with this user
     */
    public void setFirstName(String newFirstName){firstName = newFirstName;}
    /**
     * Sets last name to be associated with this user.
     * @param newLastName last name to be associated with this user
     */
    public void setLastName(String newLastName){lastName = newLastName;}
    /**
     * Sets email to be associated with this user.
     * @param newEmail email to be associated with this user
     */
    public void setEmail(String newEmail){email = newEmail;}
    /**
     * Sets reservations to be associated with this user.
     * @param newReservations reservations to be associated with this user
     */
    public void setReservations(Reservation[] newReservations){
        reservations = newReservations;}
    /**
     * Adds a reservation to user's current set of reservations.
     * @param reservationToAdd reservation to add to user's current set of reservations
     */
    public void addReservation(Reservation reservationToAdd){
        Integer reservationsLength = reservations.length;
        Reservation[] newReservations = new Reservation[reservationsLength + 1];
        System.arraycopy(
            reservations, 0, newReservations, 0, reservationsLength);
        newReservations[reservationsLength] = reservationToAdd;
        reservations = newReservations;
    }
    /**
     * Fetches user from database through email.
     * @param emailStr email with which to fetch user
     */
    public void fetchByEmail(String emailStr){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * FROM users WHERE email = '" + emailStr + "'";
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                userId = rs.getInt("user_id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                phone = rs.getString("phone");
                isEmployee = rs.getBoolean("is_employee");
                isManager = rs.getBoolean("is_manager");
                reservations = fetchReservations(false, false, false);
            }
        }
        catch (SQLException e){
            System.out.println("User.fetchByEmail()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
    }
    /**
     * Fetches User from database through user ID.
     * @param userIdToFetch ID with which to fetch user
     */
    public void fetchById(Integer userIdToFetch){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * FROM users WHERE user_id = " + userIdToFetch;
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                email = rs.getString("email");
                firstName = rs.getString("first_name").trim();
                lastName = rs.getString("last_name").trim();
                phone = rs.getString("phone");
                isEmployee = rs.getBoolean("is_employee");
                isManager = rs.getBoolean("is_manager");
                reservations = fetchReservations(false, false, false);
            }
        }
        catch (SQLException e){
            System.out.println("User.fetchById()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
    }
    /**
     * Fetches user from database by email or id. Assumes user has one of
     * those valid members already.
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
     * Fetches all reservations associated with a user from the database by user id
     * @param user 
     * @param fetchOnlyFuture
     * @return the reservation results.
     */
    public Reservation[] fetchReservations(
        Boolean onlyFuture, Boolean byDate, Boolean onlyNotCancelled){
        Reservation tempReservation = null;
        Room tempRoom = null;
        LocalDate tempStartDate = null;
        LocalDate tempEndDate = null;
        String tempComments = null;
        Integer tempGroupSize = 0;
        Integer tempReservationId = 0;
        Boolean tempIsCancelled = false;
        Boolean tempIsCheckedIn = false;
        Boolean tempIsCheckedOut = false;
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        Reservation[] result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * FROM reservations WHERE user_id = " + userId;
        Connection con = null;
        
        if (onlyFuture) {
            sqlQuery += " AND '" + Date.valueOf(LocalDate.now()) +
            "' <= end_date";
        }
        if (onlyNotCancelled){
            sqlQuery += " AND is_cancelled = 0";
        }
        if (byDate)
        {
            sqlQuery += " ORDER BY start_date";
        }
        con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            
            while(rs.next()){
                tempRoom = new Room(rs.getInt("room_num"));
                ReservationSystem.processing();
                tempStartDate = rs.getDate("start_date").toLocalDate();
                tempEndDate = rs.getDate("end_date").toLocalDate();
                tempComments = rs.getString("comments");
                tempGroupSize = rs.getInt("group_size");
                tempReservationId = rs.getInt("reservation_id");
                tempIsCancelled = rs.getBoolean("is_cancelled");
                tempIsCheckedIn = rs.getBoolean("is_checked_in");
                tempIsCheckedOut = rs.getBoolean("is_checked_out");
                tempReservation = new Reservation(tempRoom, tempStartDate,
                    tempEndDate, this, tempComments, tempGroupSize,
                    tempReservationId, tempIsCancelled, tempIsCheckedIn,
                    tempIsCheckedOut);
                ReservationSystem.processing();
                reservationList.add(tempReservation);
            }
            result = new Reservation[reservationList.size()];
            reservationList.toArray(result);
        }
        catch (SQLException e){
            System.out.println("User.fetchReservations()");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
            System.out.println(e);
        }
        ReservationSystem.ready();
        return result;
    }
    /**
     * Adds a new user to the database. Generates a salt and hashed password,
     * then inserts all user details in a new row in the database table 'users'
     * @param password actual password provided by user
     */
    public void push(String password) {
        Password pass = new Password();
        String salt = pass.getSalt();
        String hashedPassword = null;
        PreparedStatement p = null;
        Connection con = null;
        String sqlQuery = "insert into users " +
                    "(first_name, last_name, email, phone, hashed_password, salt)" +
                    " values (?, ?, ?, ?, ?, ?)";
        
        try {
            hashedPassword = pass.encrypt(password);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println(e);
        }

        con = ReservationSystem.getDatabaseConnection();

        try {
            p = con.prepareStatement(sqlQuery);
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
            System.out.println("User.push(password)");
        }
        ReservationSystem.ready();
    }
    /**
     * Pushes a password by default.
     */
    public void push(){
        PreparedStatement ps = null;
        String sqlQuery =
            "UPDATE users " +
            "SET first_name = '" + firstName +
            "', last_name = '" + lastName +
            "', email = '" + email +
            "', phone = '" + phone +
            "', is_employee = " + isEmployee +
            ", is_manager = " + isManager + 
            " WHERE user_id = " + userId;
        Connection con = ReservationSystem.getDatabaseConnection();

        try {
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
        }
        catch (SQLException e) {
            System.out.println(e);
            System.out.println("User not updated in database");
            System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        ReservationSystem.ready();
    }
}
