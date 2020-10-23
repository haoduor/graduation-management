package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.Period;
import com.haoduor.graduation.model.PeriodExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeriodMapper {
    int countByExample(PeriodExample example);

    int deleteByExample(PeriodExample example);

    int insert(Period record);

    int insertSelective(Period record);

    List<Period> selectByExample(PeriodExample example);

    int updateByExampleSelective(@Param("record") Period record, @Param("example") PeriodExample example);

    int updateByExample(@Param("record") Period record, @Param("example") PeriodExample example);
}