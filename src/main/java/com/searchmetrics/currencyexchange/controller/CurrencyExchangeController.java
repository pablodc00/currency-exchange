package com.searchmetrics.currencyexchange.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.searchmetrics.currencyexchange.model.Rate;
import com.searchmetrics.currencyexchange.service.CurrencyExchangeService;

@RestController
public class CurrencyExchangeController {

    private static Logger LOG = LoggerFactory.getLogger(CurrencyExchangeController.class);
    
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    
    @RequestMapping("/latestrate")
    public ResponseEntity<Rate> latestRate() {
        LOG.info("Calling /latestrate");
        Rate rate;
        try {
            rate = currencyExchangeService.latestRate();
        } catch (Exception e) {
            LOG.error("Error trying to get last rate", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
        
        if (null == rate) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }


    @RequestMapping("/historicalrates")
    public ResponseEntity<List<Rate>> historicalRates(@RequestParam(value="startdate") String startDate,
            @RequestParam(value="enddate") String endDate) {

        Date ldStartDate;
        Date ldEndDate;
        List<Rate> rates;        
        
        LOG.info("Calling /historicalrates with startDate:{} and endDate:{}", startDate, endDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ldStartDate = df.parse(startDate);
            ldEndDate = df.parse(endDate);
        } catch (ParseException e) {
            LOG.error("Bad request parameters, startdate:{}, endDate:{}", startDate, endDate, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            rates = currencyExchangeService.historicalRates(ldStartDate, ldEndDate);
        } catch (Exception e) {
            LOG.error("Error trying to get historical rates, startdate:{}, endDate:{}", startDate, endDate, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
       if (rates.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       
       return new ResponseEntity<>(rates, HttpStatus.OK);

    }

}