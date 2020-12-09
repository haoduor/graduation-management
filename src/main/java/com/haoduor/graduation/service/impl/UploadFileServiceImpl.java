package com.haoduor.graduation.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.UploadFileMapper;
import com.haoduor.graduation.model.UploadFile;
import com.haoduor.graduation.model.UploadFileExample;
import com.haoduor.graduation.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public boolean addUploadFile(File f, long studentId, long subjectId) {
        return false;
    }

    @Override
    public boolean addUploadFile(File f, String filename, String sha256, long studentId, long subjectId) {
        return false;
    }

    @Override
    public boolean addUploadFile(int size, String filename, String sha256, long studentId, long subjectId) {
        UploadFile uploadFile = buildModel(size, filename, sha256, studentId, subjectId);

        int res = uploadFileMapper.insert(uploadFile);

        return res == 1;
    }

    @Override
    public UploadFile buildModel(int size, String filename, String sha256, long studentId, long subjectId) {
        UploadFile res = new UploadFile();

        res.setId(snowflake.nextId());
        res.setSha256(sha256);
        res.setFilename(filename);
        res.setSize(size);
        res.setSubjectId(subjectId);
        res.setStudentId(studentId);
        res.setUploadTime(DateUtil.date());

        return res;
    }

    @Override
    public int countUploadFileByStuId(long studentId) {
        UploadFileExample ufe = new UploadFileExample();
        ufe.createCriteria()
           .andStudentIdEqualTo(studentId);

        return uploadFileMapper.countByExample(ufe);
    }

    @Override
    public boolean hasUploadFile(long studentId, long fileId) {
        UploadFileExample ufe = new UploadFileExample();
        ufe.createCriteria()
           .andStudentIdEqualTo(studentId)
           .andIdEqualTo(fileId);

        return uploadFileMapper.countByExample(ufe) == 1;
    }

    @Override
    public UploadFile getFileBySha256(String sha256) {
        UploadFileExample ufe = new UploadFileExample();
        ufe.createCriteria().andSha256EqualTo(sha256);

        return CollUtil.getFirst(uploadFileMapper.selectByExample(ufe));
    }

    @Override
    public UploadFile getFileById(long id) {
        UploadFileExample ufe = new UploadFileExample();
        ufe.createCriteria().andIdEqualTo(id);

        return CollUtil.getFirst(uploadFileMapper.selectByExample(ufe));
    }

    @Override
    public List<UploadFile> getFileByStudentId(long studentId) {
        UploadFileExample ufe = new UploadFileExample();
        ufe.createCriteria().andStudentIdEqualTo(studentId);

        return uploadFileMapper.selectByExample(ufe);
    }

    @Override
    public List<UploadFile> getAllUploadFile() {
        return uploadFileMapper.selectByExample(new UploadFileExample());
    }

    @Override
    public boolean deleteUploadFile(long studentId, long fileId) {
        UploadFileExample ufe = new UploadFileExample();
        ufe.createCriteria()
           .andStudentIdEqualTo(studentId)
           .andIdEqualTo(fileId);

        return uploadFileMapper.deleteByExample(ufe) == 1;
    }
}
