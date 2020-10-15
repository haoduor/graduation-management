package com.haoduor.graduation.model;

import java.io.Serializable;

public class Teacher implements Serializable {
    private Long userId;

    private Long departmentId;

    private String name;

    private static final long serialVersionUID = 3315967436954175516L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}