package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Tag;

public interface TagService {
    public Tag addTagByName(String name);
    public Tag getTagByName(String name);
    public Tag getTagById(long id);
    public Tag getOrAddTagByName(String name);
}
