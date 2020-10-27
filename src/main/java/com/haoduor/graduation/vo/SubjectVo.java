package com.haoduor.graduation.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.haoduor.graduation.model.Tag;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class SubjectVo {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private long      id;
    private String    title;
    private String    source;
    private String    content;
    private int       difficult;
    private Date      createTime;
    private List<Tag> tags;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private long      teacherId;
    private String    teacherName;
}
