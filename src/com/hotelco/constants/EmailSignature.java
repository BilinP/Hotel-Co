package com.hotelco.constants;
/**
* Contains the email signature constant for sent emails.
 */
public class EmailSignature {
   private static final String signature = "\n\nWon the VermaVroomVroom Roadtrip Award of the Year 2016\r\n" + //
            "Phone: 001 - 010 - 0011\r\n" + //
            "Email: HotelCoDesk@gmail.com\r\n" + //
            "Website: hotelco.hotel.co\r\n" + //
            "Address: 18111 Nordhoff St, Northridge, CA 91330\r\n" + //
            "Remember, at HotelCo we do hotels~";
    public static String getSignature(){return signature;}
}
