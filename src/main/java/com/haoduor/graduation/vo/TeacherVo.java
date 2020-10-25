package com.haoduor.graduation.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeacherVo {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private long id;
    private String teacherId;
    private String name;
    private String department;
}
