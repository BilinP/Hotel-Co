package com.hotelco.entities;

import java.math.BigDecimal;

public class Adjustment {
    
    private String comment;

    private BigDecimal amount;

    public String getComment(){return comment;}

    public BigDecimal getAmount(){return amount;}

    public void setComment(String newComment){comment = newComment;}

    public void setAmount(BigDecimal newAmount){amount = newAmount;}
}
