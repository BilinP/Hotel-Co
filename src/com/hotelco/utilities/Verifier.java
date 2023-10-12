package com.hotelco.utilities;

import java.security.NoSuchAlgorithmException;

import com.hotelco.entities.Password;
import com.hotelco.entities.User;

public class Verifier {
    public static boolean verify(String email, String password)
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
}
