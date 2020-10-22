package com.haoduor.graduation.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.TagMapper;
import com.haoduor.graduation.model.Tag;
import com.haoduor.graduation.model.TagExample;
import com.haoduor.graduation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public Tag addTagByName(String name) {
        Tag t = new Tag();
        t.setId(snowflake.nextId());
        t.setName(name);

        int res = tagMapper.insert(t);
        if (res != 1) {
            return null;
        }

        return t;
    }

    @Override
    public Tag getTagByName(String name) {
        TagExample te = new TagExample();
        te.createCriteria().andNameEqualTo(name);

        List<Tag> tags = tagMapper.selectByExample(te);
        if (tags.size() == 1) {
            return tags.get(0);
        }

        return null;
    }

    @Override
    public Tag getTagById(long id) {
        TagExample te = new TagExample();
        te.createCriteria().andIdEqualTo(id);

        List<Tag> tags = tagMapper.selectByExample(te);
        if (tags.size() == 1) {
            return tags.get(0);
        }

        return null;
    }

    @Override
    public Tag getOrAddTagByName(String name) {
        Tag t = getTagByName(name);
        if (t == null) {
            return addTagByName(name);
        }

        return t;
    }

    @Override
    public List<Tag> getTagsBySubjectId(long id) {
        return tagMapper.selectBySubjectId(id);
    }
}
