package com.currencyexchange.controller;

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

import com.currencyexchange.model.Rate;
import com.currencyexchange.service.CurrencyExchangeService;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    private static Logger LOG = LoggerFactory.getLogger(CurrencyExchangeController.class);
    
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    
    @RequestMapping("/v1/all")
    public ResponseEntity<List<Rate>> getAll() {
        List<Rate> rates = currencyExchangeService.getAll();
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }
    
    @RequestMapping("/v1/latestrate")
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
            LOG.info("/latestrate there is not content");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }


    @RequestMapping("/v1/historicalrates")
    public ResponseEntity<List<Rate>> historicalRates(@RequestParam(value="startdate") String startDate,
            @RequestParam(value="enddate") String endDate) {

        LOG.info("Calling /historicalrates with startDate:{} and endDate:{}", startDate, endDate);
        
        Date ldStartDate;
        Date ldEndDate;
        List<Rate> rates;

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
           LOG.info("/historicalrates with startDate:{} and endDate:{} there is not content", startDate, endDate);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       
       return new ResponseEntity<>(rates, HttpStatus.OK);

    }

}
