package com.haoduor.graduation.dao;

import com.haoduor.graduation.model.Announcement;
import com.haoduor.graduation.model.AnnouncementExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnnouncementMapper {
    int countByExample(AnnouncementExample example);

    int deleteByExample(AnnouncementExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    List<Announcement> selectByExample(AnnouncementExample example);

    Announcement selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Announcement record, @Param("example") AnnouncementExample example);

    int updateByExample(@Param("record") Announcement record, @Param("example") AnnouncementExample example);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);
}