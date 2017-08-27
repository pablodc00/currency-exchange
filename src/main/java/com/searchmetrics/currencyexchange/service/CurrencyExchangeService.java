package com.searchmetrics.currencyexchange.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchmetrics.currencyexchange.dao.RateRepository;
import com.searchmetrics.currencyexchange.model.Rate;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

@Service
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
            //TODO: This is a workaround just in case YahooFinance return null I call to a mocked service
            rate.setRate((null == usdeur) ? this.mockedRate() : usdeur.getPrice());
            rateRepository.save(rate);
        } catch (IOException e) {
            LOG.error("Error trying to get currency exchange from external service.", e);
        }
        LOG.info("Exit checkCurrenyExchange()");
    }

    private BigDecimal mockedRate() {
        int randomNum = ThreadLocalRandom.current().nextInt(17, 23 + 1);
        return new BigDecimal("1."+randomNum);
        
    }
    
    public Rate latestRate() {
        return rateRepository.findTopByOrderByDateDesc();
    }

    public List<Rate> historicalRates(Date startDate, Date endDate) {
        return rateRepository.findByDateGreaterThanEqualAndDateLessThanEqual(startDate, endDate);
    }
    
    public List<Rate> getAll() {
        return StreamSupport.stream(rateRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    
}
