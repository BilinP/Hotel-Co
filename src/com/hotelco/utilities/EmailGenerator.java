package com.hotelco.utilities;

import java.time.format.DateTimeFormatter;

import com.hotelco.entities.Reservation;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;

/**
* Contains the email signature constant for sent emails.
* @author      John Lee
* @version     %I%, %G%
*/
public class EmailGenerator {

    private EmailGenerator() {}
    
    /**
     * Signature for end of emails
     */
    public static final String SIGNATURE = "Phone: 818 - 555 - 1337\r\n" + //
            "Email: HotelCoDesk@gmail.com\r\n" + //
            "Website: hotelco.hotel.co\r\n" + //
            "Address: 18111 Nordhoff St, Northridge, CA 91330\r\n\n" + //
            "Remember, at Hotel Co.,\n~We do hotels~";

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
            "\n\n\t\tSincerely,\n\t\t\tHotel Co.\n";
        SendMail.startSend(reservation.getUser().getEmail(), subject, message);
    }

    public static void paymentWarning(Integer reservationID, User user) {
            String subject = "Reservation " + reservationID + " payment";
            String message = "Dear " + user.getFirstName() + " " +
            user.getLastName() + ", it has come to our attention that " +
            "your payment could not be processed at the time of checkout. " +
            "Please ensure that payment is promptly issued to Hotel Co. to avoid " +
            "further charges.\n\t\tSincerely, Hotel Co.";
            SendMail.startSend(
                    ReservationSystem.getCurrentUser().getEmail(), subject, message);
    }
    public static void resetPassword(String tempPassword, User user) {
        String subject = "Password Reset";
        String message = "Hello," + user.getFirstName()+" "+ user.getLastName()+","+
            "\n\nYou have requested a password reset for your account. Your new password is:\n\n" +
            tempPassword +
            "\n\nPlease use this  password to log in" +
             "\n\nSincerely,\nHotel Co.";
        SendMail.startSend(user.getEmail(), subject, message);
    }
}