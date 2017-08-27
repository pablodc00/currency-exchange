package com.searchmetrics.currencyexchange.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchmetrics.currencyexchange.dao.RateRepository;
import com.searchmetrics.currencyexchange.model.Rate;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

@RunWith(PowerMockRunner.class)
@PrepareForTest(YahooFinance.class)
@SpringBootTest
public class CurrencyExchangeServiceTest {

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();
    
    @Mock
    private RateRepository rateRepositoryMock;
    
    @Test
    public void testCheckCurrenyExchange() throws IOException {
        
        FxQuote usdeur = new FxQuote(FxSymbols.EURUSD, new BigDecimal(1.17));
        
        mockStatic(YahooFinance.class);
        when(YahooFinance.getFx(FxSymbols.EURUSD)).thenReturn(usdeur);
        
        Rate rate = new Rate();
        rate.setDate(new Date());
        rate.setRate(new BigDecimal(1.17));
        
        when(this.rateRepositoryMock.save(any(Rate.class))).thenReturn(rate);
        currencyExchangeService.checkCurrenyExchange();

        assertEquals(usdeur.getPrice(), rate.getRate());  

    }

}
