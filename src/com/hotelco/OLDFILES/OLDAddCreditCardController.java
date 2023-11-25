package com.hotelco.OLDFILES;

import com.hotelco.controllers.BaseController;

/**
 * Controller to add a credit card
 */
public class OLDAddCreditCardController extends BaseController{
    Integer x = 1;
    /* Please insert this code into the add credit card controller or wherever necessary:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
        //First, appropriately fill all the parameters of
        String cardNum = ?;
        String cvv = ?;
        LocalDate expDate = ?;
        //then
        CreditCard creditCard = new CreditCard(cardNum, cvv, expDate,
            creditCardType, ReservationSystem.getCurrentUser());
            if(creditCard.verify()){
                creditCard.assign();
                //go to thank you/booking complete page.
            }
            else{
                //inform user that credit card could not be real or something like that
            }
      
    
     */



}
