package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.SubjectToTag;
import com.haoduor.graduation.model.SubjectToTagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectToTagMapper {
    int countByExample(SubjectToTagExample example);

    int deleteByExample(SubjectToTagExample example);

    int insert(SubjectToTag record);

    int insertSelective(SubjectToTag record);

    List<SubjectToTag> selectByExample(SubjectToTagExample example);

    int updateByExampleSelective(@Param("record") SubjectToTag record, @Param("example") SubjectToTagExample example);

    int updateByExample(@Param("record") SubjectToTag record, @Param("example") SubjectToTagExample example);
}