package com.haoduor.graduation.model;

import java.io.Serializable;

public class Role implements Serializable {
    private Long id;

    private String name;

    private static final long serialVersionUID = -4764538714984109385L;

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