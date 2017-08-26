package com.searchmetrics.currencyexchange.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private BigDecimal rate;
    private Date date;

    public BigDecimal getRate() {
        return rate;
    }
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
