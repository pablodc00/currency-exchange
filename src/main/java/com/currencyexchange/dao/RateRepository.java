package com.currencyexchange.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.currencyexchange.model.Rate;

public interface RateRepository extends CrudRepository<Rate, Long> {

    Rate findTopByOrderByDateDesc();
    
    List<Rate> findByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate);
}
