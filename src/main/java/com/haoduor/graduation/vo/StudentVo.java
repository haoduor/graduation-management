package com.haoduor.graduation.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.haoduor.graduation.model.Department;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@NotNull
public class StudentVo {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private long id;
    private String studentId;
    private String name;
    private String classname;
    private String department;
}
