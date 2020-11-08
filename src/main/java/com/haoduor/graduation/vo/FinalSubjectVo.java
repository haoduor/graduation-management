package com.haoduor.graduation.vo;

import com.haoduor.graduation.model.UploadFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class FinalSubjectVo extends SubjectVo {
    private long             studentId;
    private String           studentName;
    private Date             chosenTime;
    private int              score;
    private List<UploadFile> uploadFileList;
}
