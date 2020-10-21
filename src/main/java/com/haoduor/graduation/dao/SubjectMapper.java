package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.model.SubjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper {
    int countByExample(SubjectExample example);

    int deleteByExample(SubjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Subject record);

    int insertSelective(Subject record);

    List<Subject> selectByExampleWithBLOBs(SubjectExample example);

    List<Subject> selectByExample(SubjectExample example);

    Subject selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Subject record, @Param("example") SubjectExample example);

    int updateByExampleWithBLOBs(@Param("record") Subject record, @Param("example") SubjectExample example);

    int updateByExample(@Param("record") Subject record, @Param("example") SubjectExample example);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKeyWithBLOBs(Subject record);

    int updateByPrimaryKey(Subject record);
}