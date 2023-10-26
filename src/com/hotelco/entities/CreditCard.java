package com.hotelco.entities;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


public class CreditCard{

    private String CreditCardNum;

    private String CVVNum;

    private String ExpDate;

    private String CreditCardType;



public String getCreditCardNum(){return this.CreditCardNum;}

public String getCVVNum(){return this.CVVNum;}

public String getExpDate(){return this.ExpDate;}

//public String getCreditCardType(){return this.CreditCardType;}

public void setCreditCardNum(String newCreditCardNum){CreditCardNum = newCreditCardNum;}

public void setCVVNum(String newCVVNum){CVVNum = newCVVNum;}

public void setExpDate(String newExpDate){ExpDate = newExpDate;}

//public void setCreditCardType(String newCreditCardType){CreditCardType = newCreditCardType;}

public CreditCard(CreditCard CreditCardInfo)
    {
        //fetch(CreditCardInfo);
    }

public CreditCard(
        String newCreditCardNum, String newCVVNum, String newExpDate) {
            CreditCardNum = newCreditCardNum;
            CVVNum = newCVVNum;
            ExpDate = newExpDate;
            //CreditCardType = newCreditCardType;
    }

public boolean verifyCreditCard(CreditCard CardInfo)throws NoSuchAlgorithmException{

        int cardSize = CardInfo.CreditCardNum.length();
        int totalSum = 0;
        boolean secondNum = false;
        String temp = LocalDate.now().toString();
        String currentDate = "";



        //Checks the credit card number for validity.
        if (CardInfo.CreditCardNum.charAt(0) == '4' && (CardInfo.CreditCardNum.length() == 16 ||  CardInfo.CreditCardNum.length() == 13)){} //VISA
        else if (CardInfo.CreditCardNum.charAt(0) == '4' && CardInfo.CreditCardNum.length() == 16){} //Mastercard
        else if (CardInfo.CreditCardNum.charAt(0) == '3' && CardInfo.CreditCardNum.length() == 15 
            && (CardInfo.CreditCardNum.charAt(1) == '4' || CardInfo.CreditCardNum.charAt(1) == '7')){} //American Express
        else if (CardInfo.CreditCardNum.charAt(0) == '6' && CardInfo.CreditCardNum.length() == 16){} //Discover
        else {return false;}
        for (int i = cardSize - 1; i >= 0; i--){ 

            int j = CardInfo.CreditCardNum.charAt(i) - '0';

            if (secondNum == true)
                j = j * 2;
            
            
            totalSum += j / 10;
            totalSum += j % 10;

            secondNum = !secondNum;
        }
        if (totalSum%10 != 0){
            return false;
        }

        for (int i = 0; i < 3; i++) { currentDate = currentDate + temp.charAt(i); } //Checks if credit card expired
        for (int i = 5; i < 6; i++) { currentDate = currentDate + temp.charAt(i); }
        if (Integer.parseInt(currentDate) > Integer.parseInt(CardInfo.ExpDate)) {return false;}

        //Dont know if CVV is needed.
        

        return true;
    }


}


