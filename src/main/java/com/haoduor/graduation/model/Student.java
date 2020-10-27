package com.haoduor.graduation.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;

public class Student implements Serializable {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;

    private String className;

    private String name;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long departmentId;

    private static final long serialVersionUID = -7285024712971552809L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}