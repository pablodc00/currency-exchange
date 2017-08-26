package com.searchmetrics.currencyexchange.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.searchmetrics.currencyexchange.dao.RateRepository;
import com.searchmetrics.currencyexchange.model.Rate;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

public class CurrencyExchangeService {

    @Autowired
    private RateRepository rateRepository;
    
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyExchangeService.class);
    
    public void checkCurrenyExchange() {
        LOG.info("Calling checkCurrenyExchange()");
        Rate rate;
        try {
            FxQuote usdeur = YahooFinance.getFx(FxSymbols.EURUSD);
            rate = new Rate();
            rate.setDate(new Date());
            rate.setRate(usdeur.getPrice());
            rateRepository.save(rate);
        } catch (IOException e) {
            LOG.error("Error trying to get currency exchange from external service.", e);
        }        
        LOG.info("Exit checkCurrenyExchange()");
    }
    
    public Rate latestRate() {
        return null;
    }
    
    public List<Rate> historicalRates(Date startDate, Date endDate) {
        return null;
    }
}
