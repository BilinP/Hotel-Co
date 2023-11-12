package com.hotelco.constants;
/**
* Contains the email signature constant for sent emails.
 */
public class EmailSignature {
   private static String signature = "Phone: 818 - 555 - 1337\r\n" + //
            "Email: HotelCoDesk@gmail.com\r\n" + //
            "Website: hotelco.hotel.co\r\n" + //
            "Address: 18111 Nordhoff St, Northridge, CA 91330\r\n" + //
            "Remember, at Hotel Co., we do hotels~";
    public static String getSignature(){return signature;}
}
