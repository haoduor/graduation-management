package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.model.TagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    int countByExample(TagExample example);

    int deleteByExample(TagExample example);

    int insert(Tag record);

    int insertSelective(Tag record);

    List<Tag> selectByExample(TagExample example);

    int updateByExampleSelective(@Param("record") Tag record, @Param("example") TagExample example);

    int updateByExample(@Param("record") Tag record, @Param("example") TagExample example);
}