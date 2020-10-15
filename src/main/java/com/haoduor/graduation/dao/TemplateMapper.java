package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.Template;
import com.haoduor.graduation.model.TemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateMapper {
    int countByExample(TemplateExample example);

    int deleteByExample(TemplateExample example);

    int insert(Template record);

    int insertSelective(Template record);

    List<Template> selectByExample(TemplateExample example);

    int updateByExampleSelective(@Param("record") Template record, @Param("example") TemplateExample example);

    int updateByExample(@Param("record") Template record, @Param("example") TemplateExample example);
}