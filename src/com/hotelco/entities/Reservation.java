package com.hotelco.entities;
import java.util.Date;
import javafx.util.Pair;

public class Reservation {
    
    private Room room;

    private Date startDate;

    private Date endDate;
    
    private User user;

    private InvoiceDetails invoiceDetails;

    private String[] comments;

    private int groupSize;

    private int reservationId;
    //FIXME: reservation functions here

    public class InvoiceDetails{
        private double rateDiscount;

        private Pair<String, Integer>[] adjustments;
        //FIXME: invoicedetials functions here
    }
    
}
/*
 * -room : Room
-startDate : Date
-endDate : Date
-user : User
-invoiceDetails : InvoiceDetails
-comments : string[]
-groupSize : int
-reservationId : int
+GetRoom() : Room
+GetStartDate() : Date
+GetEndDate() : Date
+getInvoiceDetails() : InvoiceDetails
+GetComments() : string[]
+GetGroupSize() : int
+GetID() : int
+SetRoom(Room) : void
+SetStartDate(startDate) : void
+SetEndDate(endDate) : void
+SetComments(comment) : void
+SetGroupSize(groupSize) : void
+AddComment(comment) : void
+Fetch() : void
+Push() : void
+CalcTotal() : Currency

 */