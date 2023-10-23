package com.hotelco.entities;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.util.Pair;
/**
 * Maintains details of a particular reservation and facilitates database transactions.
 * @author Daniel Schwartz
 * @version %I%, %G%
 */
public class Reservation {
    /**
     * Represents the room associated with this reservation.
     */
    private Room room;
    /**
     * Represents the starting date of a reservation.
     */
    private LocalDate startDate;
    /**
     * Represents the end date of a reservation.
     */
    private LocalDate endDate;
    /**
     * Represents the user associated with this reservation.
     */
    private User user;
    /**
     * Represents billing details specific to this reservation.
     */
    private InvoiceDetails invoiceDetails;
    /**
     * Represents a comment section for additional information outside of set data.
     */
    private String comments;
    /**
     * Represents the number of guests for this reservation.
     */
    private Integer groupSize;
    /**
     * Represents the identification of user.
     */
    private Integer reservationId;
    /**
     * Marks this reservation as cancelled when true.
     */
    private boolean isCancelled;
    /**
     * Constructs a reservation and fills it with details from the database.
     * Looks up the reservation in the database based on the supplied parameter
     * @param reservationIdNum Unique reservation id
     */
    public Reservation(Integer reservationIdNum)
    {
        fetch(reservationIdNum);
    }

    /**
     * Constructor for creating a new reservation that will be added to the
     * database after construction. After construction, the reservation is ready
     * for push() to be called, which will add the reservation to the database.
     * @param newRoom
     * @param newStartDate
     * @param newEndDate
     * @param newUser
     * @param newGroupSize
     */
    public Reservation(
        Room newRoom, LocalDate newStartDate, LocalDate newEndDate, User newUser, Integer newGroupSize) {
            room = newRoom;
            startDate = newStartDate;
            endDate = newEndDate;
            user = newUser;
            groupSize = newGroupSize;
    }
    /**
     * Constructor for creating a reservation, explicitly defining every member of Reservation
     * @param newRoom
     * @param newStartDate
     * @param newEndDate
     * @param newUser
     * @param newInvoiceDetails
     * @param newComments
     * @param newGroupSize
     * @param newReservationId
     * @param newIsCancelled
     */
    public Reservation(
        Room newRoom, LocalDate newStartDate, LocalDate newEndDate, User newUser,
        InvoiceDetails newInvoiceDetails, String newComments, Integer newGroupSize,
        Integer newReservationId, boolean newIsCancelled) {
            room = newRoom;
            startDate = newStartDate;
            endDate = newEndDate;
            user = newUser;
            invoiceDetails = newInvoiceDetails;
            comments = newComments;
            groupSize = newGroupSize;
            reservationId = newReservationId;
            isCancelled = newIsCancelled;
    }
    /**
    * Gets room of this reservation.
    * @return room associated with this reservation
    */
    public Room getRoom(){return room;}
    /**
     * Gets start date of this reservation.
     * @return start date associated with this reservation
     */
    public LocalDate getStartDate(){return startDate;}
    /**
     * Gets end date of this reservation.
     * @return end date associated with this reservation
     */
    public LocalDate getEndDate(){return endDate;}
    /**
     * Gets User.
     * @return User object associated with this reservation
     */
    public User getUser(){return user;}
    /**
     * Gets the details of the invoice of reservation.
     * @return InvoiceDetails object with invoice details for this reservation
     */
    public InvoiceDetails getInvoiceDetails(){return invoiceDetails;}
    /**
     * Gets the comments of a reservvation for additional info.
     * @return comments associated with this reservation
     */
    public String getComments(){return comments;}
    /**
     * Gets the group size of the reservation.
     * @return the number of members in this group
     */
    public Integer getGroupSize(){return groupSize;}
    /**
     * Gets the unique reservation.
     * @return unique reservation id of this reservation
     */
    public Integer getReservationId(){return reservationId;}
    /**
     * Gets the cancellation status of this reservation.
     * @return true when reservation is cancelled, false when it is not cancelled
     */
    public boolean getIsCancelled(){return isCancelled;}
    /**
     * Sets a room to this reservation.
     * @param newRoom the room to be associated with this reservation
     */
    public void setRoom(Room newRoom){room = newRoom;}
    /**
    * Sets the start date of this reservation.
    * @param newStartDate the start date to be associated with this reservation
    */
    public void setStartDate(LocalDate newStartDate){startDate = newStartDate;}
    /**
     * Sets the end date of this reservation.
     * @param newEndDate the end date to be associated with this reservation
     */
    public void setEndDate(LocalDate newEndDate){endDate = newEndDate;}
    /**
     * Sets the user for this reservation.
     * @param newUser the user to be associated with this reservation
     */
    public void setUser(User newUser){user = newUser;}
    /**
     * Sets the details of a reservation invoice.
     * @param newInvoiceDetails invoice details to be associated with this reservation.
     */
    public void setInvoiceDetails(InvoiceDetails newInvoiceDetails){invoiceDetails = newInvoiceDetails;}
    /**
     * Sets the reservation's comments.
     * @param newComments comments to be associated with this reservation
     */
    public void setComments(String newComments){comments = newComments;}
    /**
     * Sets the group size.
     * @param newGroupSize group size to be associated with this reservation
     */
    public void setGroupSize(Integer newGroupSize){groupSize = newGroupSize;}
    /**
     * Sets the reservation id.
     * @param newReservationId reservation id to be associated with this reservation
     */
    public void setReservationId(Integer newReservationId){reservationId = newReservationId;}
    /**
     * Sets the status of a room's cancellation.
     * @param newIsCancelled cancellation status to be associated with this reservation
     */
    public void setIsCancelled(boolean newIsCancelled){isCancelled = newIsCancelled;}
    /**
     * Adds comments to the end of the existing comments. Does not alter previous comments.
     * @param newComment comment to add to comments.
     */
    public void addComment(String newComment){
        comments += newComment;
    }
    /**
     * Fetches a reservation's data from the database by reservationId.
     * @param reservationIdToFetch unique reservation id
     */
    public void fetch(Integer reservationIdToFetch){
        reservationId = reservationIdToFetch;
        fetch();
    }
    /**
     * Fetches a reservation's data from the database.
     */
    public void fetch(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                room = new Room(rs.getInt("room_num"));
                startDate = rs.getDate("start_date").toLocalDate();
                endDate = rs.getDate("end_date").toLocalDate();
                user = new User(rs.getInt("user_id"));
                isCancelled = rs.getBoolean("is_cancelled");
                invoiceDetails.rateDiscount = rs.getBigDecimal("rate_discount");
                comments = rs.getString("comments");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    /**
     * Inserts the caller's reservation into a new row in the database table 'reservations'.
     */
    public void create(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        try {
            sqlQuery = "INSERT INTO reservations" +
            "(room_num, start_date, end_date, user_id, group_size) " + 
            "Values (" +
            room.getRoomNum() + ", '" +
            Date.valueOf(startDate) + "', '" +
            Date.valueOf(endDate) + "', " +
            user.getUserId() + ", " +
            groupSize + ")";
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    /**
     * Updates the reservation object's data in the database.
     */
    public void push(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        try {
            sqlQuery = "UPDATE reservations " +
            "SET room_num = " + room.getRoomNum() +
            ", start_date = '" + Date.valueOf(startDate) + 
            "', end_date = '" + Date.valueOf(endDate) +
            "',  group_size = " + groupSize +
            ", is_cancelled = " + isCancelled +
            ", comments = " + comments +
            //", rateDiscount = " + invoiceDetails.rateDiscount +
            " WHERE reservation_id = " + reservationId;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }

    /**
     * Invoice class to show the details of the reservation pricing.
     */
    public class InvoiceDetails{
        private BigDecimal rateDiscount;

        private Pair<String, Integer>[] adjustments;
        //FIXME:write adjustment class

        public InvoiceDetails(){}

        public BigDecimal getRateDiscount(){return rateDiscount;}

        // public InvoiceDetails(Integer reservationId){
        //     PreparedStatement ps = null;
        //     Connection con = null;
        //     String sqlQuery = null;
        //     ResultSet rs = null;
        //     Integer adjustmentsLength = 0;
        //     try {
        //         sqlQuery = "SELECT * FROM adjustments WHERE reservation_id = " + reservationId;
        //         con = ReservationSystem.getDatabaseConnection();
        //         ps = con.prepareStatement(sqlQuery);
        //         rs = ps.executeQuery();
        //         rs.last();
        //         adjustmentsLength = rs.getRow();
        //         adjustments = new Pair<String,Integer>[adjustmentsLength];
        //         rs.beforeFirst();
        //         while (rs.next()){
        //             adjustments
        //             list.add(temp);
        //         }
        //     }
        //     catch (SQLException e){
        //         System.out.println(e);
        //     }
        // }
    }
    
}