package com.hotelco.utilities;

import java.time.format.DateTimeFormatter;

import com.hotelco.entities.Reservation;

/**
* Contains the email signature constant for sent emails.
* @author      John Lee
* @version     %I%, %G%
*/
public class EmailGenerator {
    /**
     * Signature for end of emails
     */
    public final static String SIGNATURE = "Phone: 818 - 555 - 1337\r\n" + //
            "Email: HotelCoDesk@gmail.com\r\n" + //
            "Website: hotelco.hotel.co\r\n" + //
            "Address: 18111 Nordhoff St, Northridge, CA 91330\r\n" + //
            "Remember, at Hotel Co., we do hotels~";

    public static void reservationConfirmation(Reservation reservation) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        String subject = "Reservation confirmation";
        String message = "Hello " + reservation.getUser().getFirstName() + " " +
            reservation.getUser().getLastName() +
            ", and thank you for booking with Hotel Co.\n\n" +
            "Please find your booking details below:\n\n" +
            "Reservation number: " + reservation.getReservationId() +
            "\nStart Date: "  + reservation.getStartDate().format(dateTimeFormatter) +
            "\nEnd Date: "  + reservation.getEndDate().format(dateTimeFormatter) +
            "\nNumber of guests: "  + reservation.getGroupSize() +
            "\nRoom type: "  + reservation.getRoom().getRoomType().toPrettyString() +
            "\n\nWe hope you thoroughly enjoy your stay with us." +
            "\n\n\t\tSincerely,\n\t\t\tHotel Co.";
        SendMail.startSend(reservation.getUser().getEmail(), subject, message);
    }
}