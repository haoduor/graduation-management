package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    public boolean addAnnouncement(String content);
    public boolean deleteAnnouncement(long id);
    public List<Announcement> getAnnouncement();
    public boolean setAnnouncement(long id, String content);
}
