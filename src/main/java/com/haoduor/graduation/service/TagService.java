package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Tag;

import java.util.List;

public interface TagService {
    public Tag addTagByName(String name);
    public Tag getTagByName(String name);
    public Tag getTagById(long id);
    public Tag getOrAddTagByName(String name);
    public List<Tag> getTagsBySubjectId(long id);
}
