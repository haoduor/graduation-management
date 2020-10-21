package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.FinalSubject;
import com.haoduor.graduation.model.FinalSubjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinalSubjectMapper {
    int countByExample(FinalSubjectExample example);

    int deleteByExample(FinalSubjectExample example);

    int insert(FinalSubject record);

    int insertSelective(FinalSubject record);

    List<FinalSubject> selectByExample(FinalSubjectExample example);

    int updateByExampleSelective(@Param("record") FinalSubject record, @Param("example") FinalSubjectExample example);

    int updateByExample(@Param("record") FinalSubject record, @Param("example") FinalSubjectExample example);
}