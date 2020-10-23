package com.haoduor.graduation.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubjectForm {
    private String       title;
    private String       content;
    private String       teacherId;
    private int          difficult;
    private String       source;
    private List<String> tags;
}
