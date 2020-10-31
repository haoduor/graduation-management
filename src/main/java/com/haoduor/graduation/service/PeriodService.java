package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Period;

import java.util.Date;
import java.util.List;

public interface PeriodService {
    public List<Period> getPeriod();
    public boolean addPeriod(String name, Date startTime, Date endTime);
    public boolean addPeriod(Period p);
    public boolean setPeriod(long id, Date startTime, Date endTime);
}
