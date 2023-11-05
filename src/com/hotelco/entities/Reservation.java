package com.hotelco.entities;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private Boolean isCancelled;
    /**
     * Marks this reservation as checked in when true.
     */
    private Boolean isCheckedIn;
    /**
     * Marks this reservation as checked out when true.
     */ 
    private Boolean isCheckedOut;
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
     * Creates a new reservation, ready to be added to the database. Programmer
     * must explcitly call push() to add the reservation to the database.
     * @param newRoom
     * @param newStartDate
     * @param newEndDate
     * @param newUser
     * @param newGroupSize
     */
    public Reservation(
        Room newRoom, LocalDate newStartDate, LocalDate newEndDate,
        User newUser, Integer newGroupSize) {
            room = newRoom;
            startDate = newStartDate;
            endDate = newEndDate;
            user = newUser;
            groupSize = newGroupSize;
    }
    /**
     * Creates a reservation with most fields explicitly defined.
     * Invoice details are fetched from the database.
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
        String newComments, Integer newGroupSize, Integer newReservationId,
        Boolean newIsCancelled, Boolean newIsCheckedIn, Boolean newIsCheckedOut) {
            room = newRoom;
            startDate = newStartDate;
            endDate = newEndDate;
            user = newUser;
            comments = newComments;
            groupSize = newGroupSize;
            reservationId = newReservationId;
            isCancelled = newIsCancelled;
            isCheckedIn = newIsCheckedIn;
            isCheckedOut = newIsCheckedOut;
            fetchInvoiceDetails();
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
    public Boolean getIsCancelled(){return isCancelled;}
    /**
     * Gets the checked in status of this reservation.
     * @return true when reservation is checked in,
     * false when it is not checked in
     */
    public Boolean getIsCheckedIn(){return isCheckedIn;}
    /**
     * Gets the checked out status of this reservation.
     * @return true when reservation is checked out,
     * false when it is not checked out
     */
    public Boolean getIsCheckedOut(){return isCheckedOut;}
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
    public void setInvoiceDetails(InvoiceDetails newInvoiceDetails){
        invoiceDetails = newInvoiceDetails;
    }
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
     * Sets the status of a reservation's cancellation.
     * @param newIsCancelled cancellation status to be associated with this reservation
     */
    public void setIsCancelled(Boolean newIsCancelled){isCancelled = newIsCancelled;}
     /**
     * Sets the check in status of a reservation.
     * @param newIsCheckedIn check in status to be associated with this reservation
     */
    public void setIsCheckedIn(Boolean newIsCheckedIn){isCheckedIn = newIsCheckedIn;}
     /**
     * Sets the check out status of a reservation.
     * @param newIsCheckedOut check out status to be associated with this reservation
     */
    public void setIsCheckedOut(Boolean newIsCheckedOut){isCheckedOut = newIsCheckedOut;}
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
            sqlQuery = "SELECT * FROM reservations WHERE reservation_id = "
                + reservationId;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                room = new Room(rs.getInt("room_num"));
                startDate = rs.getDate("start_date").toLocalDate();
                endDate = rs.getDate("end_date").toLocalDate();
                user = new User(rs.getInt("user_id"));
                isCancelled = rs.getBoolean("is_cancelled");
                isCheckedIn = rs.getBoolean("is_checked_in");
                isCheckedOut = rs.getBoolean("is_checked_out");
                groupSize = rs.getInt("group_size");
                fetchInvoiceDetails();
                comments = rs.getString("comments");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
    public void fetchInvoiceDetails(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        try {
            sqlQuery = "SELECT * FROM adjustments WHERE reservation_id = " + reservationId;
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                invoiceDetails = new InvoiceDetails(
                          new Adjustment("Rate discount",
                        rs.getBigDecimal("rate_discount")),
                        fetchAdjustments());
            }
            else {
                invoiceDetails = new InvoiceDetails(
                          new Adjustment(
                            "Rate discount", new BigDecimal(0)),
                            Adjustment.emptyAdjustments);
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
        ResultSet rs = null;
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
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() as id");
            rs = ps.executeQuery();
            if(rs.next()){
                reservationId = rs.getInt("id");
            }
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
        String strRateDiscount = invoiceDetails == null ? "" :
        ", rate_discount = " + invoiceDetails.getRateDiscount().getAmount();
        String sqlQuery = "UPDATE reservations " +
            "SET room_num = " + room.getRoomNum() +
            ", start_date = '" + Date.valueOf(startDate) + 
            "', end_date = '" + Date.valueOf(endDate) +
            "', group_size = " + groupSize +
            ", is_cancelled = " + isCancelled +
            ", is_checked_in = " + isCheckedIn +
            ", is_checked_out = " + isCheckedOut +
            ", comments = " + comments +
            strRateDiscount +
            " WHERE reservation_id = " + reservationId;
        try {
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            ps.execute();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

public Adjustment[] fetchAdjustments(){
    PreparedStatement ps = null;
    Connection con = null;
    String sqlQuery = null;
    ResultSet rs = null;
    ArrayList<Adjustment> adjustmentList = new ArrayList<Adjustment>();
    Adjustment[] result = null;
    try {
        sqlQuery = "SELECT * FROM adjustments WHERE reservation_id = " +
        reservationId + " AND NOT comment = 'Rate discount'";
        con = ReservationSystem.getDatabaseConnection();
        ps = con.prepareStatement(sqlQuery);
        rs = ps.executeQuery();
        while(rs.next()){
            adjustmentList.add(
                new Adjustment(
                    rs.getString("comment"),
                    rs.getBigDecimal("amount")));
        }
        result = new Adjustment[adjustmentList.size()];
        adjustmentList.toArray(result);
    }
    catch (SQLException e){
        System.out.println(e);
    }
    return result;
}
public void pushNewAdjustment(Adjustment adjustment){
    PreparedStatement ps = null;
    Connection con = null;
    String sqlQuery = null;
    try {
        sqlQuery = "INSERT INTO adjustments" +
        "SET comment = '" + adjustment.getComment() +
        "', amount = " + adjustment.getAmount() +
        ", reservation_id = " + reservationId;
        con = ReservationSystem.getDatabaseConnection();
        ps = con.prepareStatement(sqlQuery);
        ps.execute();
    }
    catch (SQLException e){
        System.out.println(e);
    }
}
public void createNewAdjustment(Adjustment adjustment){
    pushNewAdjustment(adjustment);
    fetch();
}
/**
 * Holds various payment details in one object. Inner class of Reservation
 */
public class InvoiceDetails {
    /**
     * An array of adjustments that have been applied to this reservation.
     */
    private Adjustment[] adjustments;
    /**
     * A discount on the daily rate, to be applied to the entire stay
     */
    private Adjustment rateDiscount;
    /**
     * Gets the adjustments associated with this InvoiceDetails
     * @return the adjustments associated with this InvoiceDetails
     */
    public InvoiceDetails(Adjustment newRateDiscount, Adjustment[] newAdjustments){
        adjustments = newAdjustments;
        rateDiscount = newRateDiscount;
    }
    public Adjustment[] getAdjustments(){return adjustments;}
    /**
     * Gets the rate discount associated with this InvoiceDetails
     * @return the rate discount associated with this InvoiceDetails
     */
    public Adjustment getRateDiscount(){return rateDiscount;}
    /**
     * Sets the adjustments to be associated with this InvoiceDetails
     * @param newAdjustments the adjustments to be associated
     * with this InvoiceDetails
     */
    public void setAdjustments(Adjustment[] newAdjustments){
        adjustments = newAdjustments;
    }
    /**
     * Sets the rate discount to be associated with this InvoiceDetails
     * @param newRateDiscount the rate discount to be associated
     * with this InvoiceDetails
     */
    public void setRateDiscount(Adjustment newRateDiscount){
        rateDiscount = newRateDiscount;
    }
    //unlikely to use this function
    public void addAdjustment(Adjustment adjustmentToAdd){
        Integer adjustmentsLength = adjustments.length;
        Adjustment[] newAdjustments = new Adjustment[adjustmentsLength + 1];
        System.arraycopy(
            adjustments, 0, newAdjustments, 0, adjustmentsLength);
        newAdjustments[adjustmentsLength] = adjustmentToAdd;
        adjustments = newAdjustments;
    }
    public BigDecimal getTotalAdustments(){
        Integer adjustmentsLength = adjustments.length;
        BigDecimal total = new BigDecimal(0);
        for(Integer i = 0; i < adjustmentsLength; i++){
            total = total.add(adjustments[i].getAmount());
        }
        return total;
    }
}
/**
 * Checks out this reservation and ensures that it is updated to
 * ReservationSystem members.
 */
public void checkOut(){
    isCheckedIn = false;
    isCheckedOut = true;
    if(ReservationSystem.makePayment(this)){
        push();
        //System.out.println(reservationId + " checked out");
    }
    else {
        String subject = "Reservation " + getReservationId() + " payment";
        String message = "Dear " + user.getFirstName() + " " +
        user.getLastName() + ", it has come to our attention that " +
        "your payment could not be processed at the time of checkout. " +
        "Please ensure that payment is promptly issued to Hotel Co. to avoid " +
        "further charges.\n\t\tSincerely, Hotel Co.";
        //Email.send(ReservationSystem.getCurrentUser().getEmail(), subject, message);
    }
}
    /**
     * Checks in this reservation and ensures that it is updated to
     * ReservationSystem members.
     */
    public void checkIn(){
        isCheckedIn = true;
        push();
        ReservationSystem.update();
        // System.out.println("Reservation " + reservationId +
        //     "'s isCheckedIn = " + this.getIsCheckedIn().toString());
    }
}