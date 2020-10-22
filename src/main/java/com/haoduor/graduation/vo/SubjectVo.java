package com.haoduor.graduation.vo;

import com.haoduor.graduation.model.Tag;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class SubjectVo {
    private long      id;
    private String    title;
    private String    source;
    private String    content;
    private int       difficult;
    private Date      createTime;
    private List<Tag> tags;
    private String    teacherName;
    private long      teacherId;
}
