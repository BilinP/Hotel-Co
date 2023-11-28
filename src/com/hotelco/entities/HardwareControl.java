package com.hotelco.entities;

import com.hotelco.utilities.RoomCard;

public class HardwareControl {

    private static RoomCard topCard;
    /**
     * Dummy function to dispense a card.
     * @param roomCard the card to dispense
     */
    public void dispenseNewCard(RoomCard roomCard){
        topCard.magnetizeCard(roomCard);
        topCard.dispense();
    }
    /**
     * Dummy function to magnetize a card.
     * @param roomCard the card to magnetize
     */
    public void magnetizeCard(RoomCard roomCard){};
    /**
     * Dummy function representing the machine dispensing the top card from
     * the physical stack of cards
     */
    public void dispense(){}
}
