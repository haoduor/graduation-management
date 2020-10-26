package com.haoduor.graduation.model;

import java.io.Serializable;
import java.util.Date;

public class Announcement implements Serializable {
    private Long id;

    private String content;

    private Date createTime;

    private static final long serialVersionUID = 6672797738214284101L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}