package com.haoduor.graduation.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;

public class Tag implements Serializable {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    private String name;

    private static final long serialVersionUID = 903365344298430061L;

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
}