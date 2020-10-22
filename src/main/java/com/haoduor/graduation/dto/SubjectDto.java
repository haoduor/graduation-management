package com.haoduor.graduation.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectDto {
    private String       title;
    private String       content;
    private long         teacherId;
    private int          difficult;
    private String       source;
    private List<String> tags;
}
