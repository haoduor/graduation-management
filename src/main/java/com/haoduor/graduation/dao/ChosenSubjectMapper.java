package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.ChosenSubject;
import com.haoduor.graduation.model.ChosenSubjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChosenSubjectMapper {
    int countByExample(ChosenSubjectExample example);

    int deleteByExample(ChosenSubjectExample example);

    int insert(ChosenSubject record);

    int insertSelective(ChosenSubject record);

    List<ChosenSubject> selectByExample(ChosenSubjectExample example);

    int updateByExampleSelective(@Param("record") ChosenSubject record, @Param("example") ChosenSubjectExample example);

    int updateByExample(@Param("record") ChosenSubject record, @Param("example") ChosenSubjectExample example);
}