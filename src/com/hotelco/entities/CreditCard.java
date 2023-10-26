package com.hotelco.entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.hotelco.constants.CreditCardType;

/**
 * Maintains a credit card associated with a single user. Assumes that
 * every User has at most 1 credit cards.
 */
public class CreditCard{
    /**
     * Credit card number
     */
    private String creditCardNum;
    /**
     * CVV number
     */

    private String cvvNum;
    /**
     * Expiration date. LocalDate created with first day of expDate's year-month
     * 
     */
    private LocalDate expDate;
    /**
     * Type of credit card. See CreditCardType.java for types available.
     */
    private CreditCardType creditCardType;
    /**
     * User associated with this card.
     */
    private User user;
    /**
     * Gets the credit card number
     * @return credit card number
     */
    public String getCreditCardNum(){return creditCardNum;}
    /**
     * Gets the CVV number
     * @return the CVV number
     */
    public String getCvvNum(){return cvvNum;}
    /**
     * Gets the expiration date
     * @return the expiration date
     */
    public LocalDate getExpDate(){return expDate;}
    /**
     * Gets the credit card type
     * @return the credit card type
     */
    public CreditCardType getCreditCardType(){return creditCardType;}
    /**
     * Sets the credit card number
     * @param newCreditCardNum the credit card number to be associated with
     * this card
     */
    public void setCreditCardNum(String newCreditCardNum){
        creditCardNum = newCreditCardNum;
    }
    /** 
     * Sets the CVV number
     * @param newCvvNum the CVV number to be associated wiih this credit card
     */
    public void setCvvNum(String newCvvNum){cvvNum = newCvvNum;}

    public void setExpDate(LocalDate newExpDate){expDate = newExpDate;}

    public void setCreditCardType(CreditCardType newCreditCardType){
        creditCardType = newCreditCardType;
    }
/**
 * Creates CreditCard by fetching from database by userId.
 * @param owner user to be associated with this credit card
 */
    public CreditCard(User owner){
        user = owner;
        fetch();
    }

    public CreditCard(
        String newCreditCardNum, String newCVVNum,
        LocalDate newExpDate,  CreditCardType newCreditCardType, User newUser){
        
        creditCardNum = newCreditCardNum;
        cvvNum = newCVVNum;
        expDate = newExpDate;
        creditCardType = newCreditCardType;
        user = newUser;
        }

    public void determineCardType(){
        Integer cardNumLen = creditCardNum.length();
        if (cardNumLen == 15 && creditCardNum.charAt(0) == '3'){
            creditCardType = CreditCardType.AMEX;
        }
        if(cardNumLen == 16){
            switch(creditCardNum.charAt(0)){
                case '4':
                creditCardType = CreditCardType.VISA;
                break;
                case '5':
                creditCardType = CreditCardType.MASTERCARD;
                break;
                case '6':
                creditCardType = CreditCardType.DISCOVER;
                break;
            }
        }
    }

    public boolean luhnCheck(){
        Integer cardNumLen = creditCardNum.length();
        boolean isSecond = false;
        Integer totalSum = 0;
        for (int i = cardNumLen - 1; i >= 0; --i){ 

                int currDigit = creditCardNum.charAt(i) - '0';

                if (isSecond == true){
                    currDigit = currDigit * 2;
                }
                totalSum += currDigit / 10;
                totalSum += currDigit % 10;

                isSecond = !isSecond;
            }
        return (totalSum % 10 == 0);
    }

    public boolean verify(){
        determineCardType();
        return creditCardType != null &&
            luhnCheck() &&
            expDate.isAfter(LocalDate.now());
    }

    public void assign(){
        if(user != null && verify()){
            PreparedStatement ps = null;
            Connection con = null;
            String sqlQuery = null;
            if (userHasOneCard()){
                sqlQuery = "UPDATE credit_cards" +
                "SET card_num = '" + creditCardNum +
                "', cvv = '" + cvvNum +
                "', exp_date = '" + Date.valueOf(expDate) +
                "' WHERE user_id = " + user.getUserId();
            }
            else {
                sqlQuery = "INSERT INTO credit_cards" +
                "SET card_num = '" + creditCardNum +
                "', cvv = '" + cvvNum +
                "', exp_date = '" + Date.valueOf(expDate) +
                "', user_id = " + user.getUserId();
            }
            try {
                con = ReservationSystem.getDatabaseConnection();
                ps = con.prepareStatement(sqlQuery);
                ps.execute();
            }
            catch (SQLException e){
                System.out.println(e);
            }
        }
    }

    public void fetch(){
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT * FROM credit_cards " +
            "WHERE user_id = " + user.getUserId();;
        try {
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                creditCardNum = rs.getString("card_num");
                cvvNum = rs.getString("cvv");
                expDate = rs.getDate("exp_date").toLocalDate();
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public boolean userHasOneCard(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            sqlQuery = "SELECT COUNT(*) AS total " +
            "FROM credit_cards " +
            "WHERE user_id = " + user.getUserId();
            con = ReservationSystem.getDatabaseConnection();
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("total") == 1;
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }

}


