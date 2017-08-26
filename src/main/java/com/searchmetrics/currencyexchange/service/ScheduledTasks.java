package com.searchmetrics.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    
    @Scheduled(fixedDelayString = "${fixed.delay.prop}")
    public void checkCurrenyExchange() {
        currencyExchangeService.checkCurrenyExchange();
    }
}
