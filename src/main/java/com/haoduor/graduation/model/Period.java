package com.haoduor.graduation.model;

import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {
    private Long id;

    private String name;

    private Date startTime;

    private Date endTime;

    private static final long serialVersionUID = 625968413324875254L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}