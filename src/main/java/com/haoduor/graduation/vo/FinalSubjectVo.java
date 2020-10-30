package com.haoduor.graduation.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class FinalSubjectVo extends SubjectVo {
    private long   studentId;
    private String studentName;
    private Date   chosenTime;
}
