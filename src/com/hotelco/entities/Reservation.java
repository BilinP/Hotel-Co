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
 * Maintains details of a particular reservation and mantains db transactions.
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
     * Represents the data inside reservation for the user to view.
     */
    private InvoiceDetails invoiceDetails;
    /**
     * Represents a comment section for additional information outside of set data.
     */
    private String comments;
    /**
     * Represents the size of the group.
     */
    private Integer groupSize;
    /**
     * Represents the identification of user.
     */
    private Integer reservationId;
    /**
     * Represents a mark if a user has canceled a reservation.
     */
    private boolean isCancelled;
    /**
     * fetches user data using reservationIdNum from database.
     * @param reservationIdNum
     */
    public Reservation(Integer reservationIdNum)
    {
        fetch(reservationIdNum);
    }

    /**
     * Constructor for creating a new reservation that will be added to database.
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
     * Constructor for creating a new reservation that will be added to database.
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
    * Getter for room id of reservation.
    * @return the room id
    */
    public Room getRoom(){return room;}
    /**
     * Getter for start date of reservation.
     * @return start date
     */
    public LocalDate getStartDate(){return startDate;}
    /**
     * Getter for end date of reservation.
     * @return end date
     */
    public LocalDate getEndDate(){return endDate;}
    /**
     * Getter for User.
     * @return user
     */
    public User getUser(){return user;}
    /**
     * Getter for the details of the invoice of reservation.
     * @return The invoice details
     */
    public InvoiceDetails getInvoiceDetails(){return invoiceDetails;}
    /**
     * Getter for the comments of a reservvation for additional info.
     * @return comments string
     */
    public String getComments(){return comments;}
    /**
     * Getter for the group size of the reservation.
     * @return an integer for the group size
     */
    public Integer getGroupSize(){return groupSize;}
    /**
     * Getter for the reservation id to make each reservation unique.
     * @return reservation id
     */
    public Integer getReservationId(){return reservationId;}
    /**
     * Getter to cancel reservations.
     * @return a boolean for canceled rooms
     */
    public boolean getIsCancelled(){return isCancelled;}
    /**
     * Setter to create a room entity.
     * @return a new room
     */
    public void setRoom(Room newRoom){room = newRoom;}
    /**
    * Setter to create or update a start date of a reservation.
    * @param newStartDate
    */
    public void setStartDate(LocalDate newStartDate){startDate = newStartDate;}
    /**
     * Setter to create or update an end date of a reservation.
     * @param newEndDate
     */
    public void setEndDate(LocalDate newEndDate){endDate = newEndDate;}
    /**
     * Setter to create or update a user.
     * @param newUser
     */
    public void setUser(User newUser){user = newUser;}
    /**
     * Setter to create or update the details of a reservation invoice.
     * @param newInvoiceDetails
     */
    public void setInvoiceDetails(InvoiceDetails newInvoiceDetails){invoiceDetails = newInvoiceDetails;}
    /**
     * Setter to create or update a reservation's comments.
     * @param newComments
     */
    public void setComments(String newComments){comments = newComments;}
    /**
     * Setter to create or update a group size.
     * @param newGroupSize
     */
    public void setGroupSize(Integer newGroupSize){groupSize = newGroupSize;}
    /**
     * Setter to create or update a reservation id.
     * @param newReservationId
     */
    public void setReservationId(Integer newReservationId){reservationId = newReservationId;}
    /**
     * Setter to update the status of a room's cancellation.
     * @param newIsCancelled
     */
    public void setIsCancelled(boolean newIsCancelled){isCancelled = newIsCancelled;}
    /**
     * An extension tool associated with comments to add on to an existing comment.
     * @param newComment
     */
    public void addComment(String newComment){
        comments += newComment;
    }
    /**
     * Function used to search a database by a unique reservation id to fetch a reservation's data.
     * @param reservationIdToFetch
     */
    public void fetch(Integer reservationIdToFetch){
        reservationId = reservationIdToFetch;
        fetch();
    }
    /**
     * Function used to search a database.
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
     * To create a reservation object.
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
     * To push the reservation object's data into the database.
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
            ", rateDiscount = " + invoiceDetails.rateDiscount +
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