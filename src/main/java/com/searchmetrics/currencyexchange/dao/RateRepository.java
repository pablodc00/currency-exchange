package com.searchmetrics.currencyexchange.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.searchmetrics.currencyexchange.model.Rate;

public interface RateRepository extends CrudRepository<Rate, Long> {

    
    List<Rate> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date startDate, Date endDate);
}
