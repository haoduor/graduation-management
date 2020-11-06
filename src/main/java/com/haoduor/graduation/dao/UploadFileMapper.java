package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.UploadFile;
import com.haoduor.graduation.model.UploadFileExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UploadFileMapper {
    int countByExample(UploadFileExample example);

    int deleteByExample(UploadFileExample example);

    int insert(UploadFile record);

    int insertSelective(UploadFile record);

    List<UploadFile> selectByExample(UploadFileExample example);

    int updateByExampleSelective(@Param("record") UploadFile record, @Param("example") UploadFileExample example);

    int updateByExample(@Param("record") UploadFile record, @Param("example") UploadFileExample example);
}