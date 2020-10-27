package com.haoduor.graduation.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ChosenSubjectVo extends SubjectVo {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private long studentId;
    private String studentName;
}
