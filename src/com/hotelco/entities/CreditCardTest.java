package com.hotelco.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

import com.hotelco.constants.CreditCardType;

public class CreditCardTest {
    CreditCard cc =
        new CreditCard("", "0101", LocalDate.now(), new User("p@p.com", false));

    @Test
    public void testGetIssuerLipA() {
        cc.setCreditCardNum("300010001000100");
        assertEquals(cc.getIssuer(), CreditCardType.AMEX);

    }

    @Test
    public void testGetIssuerLipB() {
        cc.setCreditCardNum("200010001000100");
        assertNull(cc.getIssuer());
    }

    @Test
    public void testGetIssuerLipC() {
        cc.setCreditCardNum("1");
        assertNull(cc.getIssuer());
    }

    @Test
    public void testGetIssuerLipD() {
        cc.setCreditCardNum("4000100010001000");
        assertEquals(cc.getIssuer(), CreditCardType.VISA);
    }

    @Test
    public void testGetIssuerLipE() {
        cc.setCreditCardNum("5000100010001000");
        assertEquals(cc.getIssuer(), CreditCardType.MASTERCARD);
    }

    @Test
    public void testGetIssuerLipF() {
        cc.setCreditCardNum("6000100010001000");
        assertEquals(cc.getIssuer(), CreditCardType.DISCOVER);
    }

    @Test
    public void testGetIssuerLipG() {
        cc.setCreditCardNum("2000100010001000");
        assertNull(cc.getIssuer());
    }

    @Test
    public void testLuhnCheck() {

    }
}
