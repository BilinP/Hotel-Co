package com.hotelco.utilities;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hotelco.entities.Password;
import com.hotelco.entities.User;
/**
 * Utility class for verifications
 * @author BilinP
 * @verision 1.0
 */

public class Verifier {
    /**
    * Utility to check and verify if a proper password is inputted.
    * @return a boolean for a proper password.
    */
    public static boolean verifyPassword(String email, String password)
    {
            User temp = new User(email);
            Password pass = new Password(temp.getSalt());
            boolean isVerified = false;
            try {
                isVerified = pass.verify(password, temp.getHashedPassword());
            }
            catch (NoSuchAlgorithmException e)
            {
                System.out.println(e);
            }
            return isVerified;
    }

    /**
     * Method checks if given email address matches a regular expression pattern
     * which is checking the format of the email.
     * @param email The email address to be validated.
     * @return a boolean where true means the email is valid and false if not
     * @author Bilin Pattasseril
     */
    public boolean isValidEmail(String email){
        if(email.length()>320){return false;}
        final String regex = "^[A-Za-z0-9]+([. -][A-Za-z0-9]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
