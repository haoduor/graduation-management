package com.haoduor.graduation.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.AnnouncementMapper;
import com.haoduor.graduation.model.Announcement;
import com.haoduor.graduation.model.AnnouncementExample;
import com.haoduor.graduation.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public boolean addAnnouncement(String content) {
        Announcement a = new Announcement();
        a.setId(snowflake.nextId());
        a.setContent(content);
        a.setCreateTime(DateUtil.date());

        int res = announcementMapper.insert(a);

        return res == 1;
    }

    @Override
    public boolean deleteAnnouncement(long id) {
        AnnouncementExample ae = new AnnouncementExample();
        ae.createCriteria().andIdEqualTo(id);

        int res = announcementMapper.deleteByExample(ae);

        return res == 1;
    }

    @Override
    public List<Announcement> getAnnouncement() {
        AnnouncementExample ae = new AnnouncementExample();

        return announcementMapper.selectByExample(ae);
    }

    @Override
    public boolean setAnnouncement(long id, String content) {
        AnnouncementExample ae = new AnnouncementExample();
        ae.createCriteria().andIdEqualTo(id);

        Announcement a = new Announcement();
        a.setId(id);
        a.setContent(content);

        int res = announcementMapper.updateByExample(a, ae);
        return res == 1;
    }
}
