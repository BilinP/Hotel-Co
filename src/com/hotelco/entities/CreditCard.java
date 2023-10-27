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
 * @author John Lee
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
    /**
     * Sets the expiration date
     * @param newExpDate the expiration date to be associated with this credit card
     */
    public void setExpDate(LocalDate newExpDate){expDate = newExpDate;}
    /**
     * Sets the credit card type
     * @param newCreditCardType the credit card type to be associated
     * with this credit card
     */
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
/**
 * Creates a credit card by explicitly defining every member
 * @param newCreditCardNum
 * @param newCVVNum
 * @param newExpDate
 * @param newCreditCardType
 * @param newUser
 */
    public CreditCard(
        String newCreditCardNum, String newCVVNum,
        LocalDate newExpDate,  CreditCardType newCreditCardType, User newUser){
        
        creditCardNum = newCreditCardNum;
        cvvNum = newCVVNum;
        expDate = newExpDate;
        creditCardType = newCreditCardType;
        user = newUser;
        }
/**
 * Determines and sets the card type. If first character/length combination is
 * not a possibly valid card of the types in CreditCardType.java,
 * creditCardType becomes null. 
 */
    public void setValidCardType(){
        creditCardType = null;
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
/**
 * Uses the Luhn algorithm to check if a card is invalid.
 * @return true if passes Luhn algorithm check, false if it fails
 */
    public Boolean luhnCheck(){
        Integer cardNumLen = creditCardNum.length();
        Boolean isSecond = false;
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
    /**
     * Verifies the credit card is a valid type of card, passes Luhn algorithm check,
     * and is not expired
     * @return true if credit card is possibly valid, false if it cannot be valid
     */
    public Boolean verify(){
        setValidCardType();
        return creditCardType != null &&
            luhnCheck() &&
            expDate.isAfter(LocalDate.now());
    }
    /**
     * Database push function that assigns a credit card to CreditCard's user.
     * Adds a new entry if the user has no credit card, replaces the entry if
     * the user already has a credit card.
     */
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
    /**
     * Fetches a credit card from the database by user.
     */
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
    /**
     * Checks if the CreditCard's user has a card associated with them in the
     * database.
     * @return true if the user has a card in the database already, false if they
     * do not
     */
    public Boolean userHasOneCard(){
        PreparedStatement ps = null;
        Connection con = null;
        String sqlQuery = null;
        ResultSet rs = null;
        Boolean result = false;
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


