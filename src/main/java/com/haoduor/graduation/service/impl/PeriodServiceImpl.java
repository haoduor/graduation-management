package com.haoduor.graduation.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.PeriodMapper;
import com.haoduor.graduation.model.Period;
import com.haoduor.graduation.model.PeriodExample;
import com.haoduor.graduation.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PeriodServiceImpl implements PeriodService {

    @Autowired
    private PeriodMapper periodMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public List<Period> getPeriod() {
        PeriodExample pe = new PeriodExample();
        return periodMapper.selectByExample(pe);
    }

    @Override
    public Period getPeriodById(Long id) {
        PeriodExample pe = new PeriodExample();
        pe.createCriteria().andIdEqualTo(id);

        List<Period> periods = periodMapper.selectByExample(pe);
        return CollUtil.getFirst(periods);
    }

    @Override
    public boolean addPeriod(String name, Date startTime, Date endTime) {
        Period p = new Period();
        p.setId(snowflake.nextId());
        p.setName(name);
        p.setStartTime(startTime);
        p.setEndTime(endTime);

        return addPeriod(p);
    }

    @Override
    public boolean addPeriod(Period p) {
        int res = periodMapper.insert(p);

        return res == 1;
    }

    @Override
    public boolean setPeriod(long id, Date startTime, Date endTime) {
        Period p = new Period();
        p.setStartTime(startTime);
        p.setEndTime(endTime);

        PeriodExample pe = new PeriodExample();
        pe.createCriteria().andIdEqualTo(id);

        int res = periodMapper.updateByExampleSelective(p, pe);

        return res == 1;
    }
}
