package com.haoduor.graduation.service;

import com.haoduor.graduation.model.UploadFile;

import java.io.File;
import java.util.List;

public interface UploadFileService {
    public boolean addUploadFile(File f, long studentId, long subjectId);
    public boolean addUploadFile(File f, String filename, String sha256, long studentId, long subjectId);
    public boolean addUploadFile(int size, String filename, String sha256, long studentId, long subjectId);
    public UploadFile buildModel(int size, String filename, String sha256, long studentId, long subjectId);
    public boolean hasUploadFile(long studentId, long fileId);
    public UploadFile getFileBySha256(String sha256);
    public UploadFile getFileById(long id);
    public List<UploadFile> getFileByStudentId(long studentId);
    public List<UploadFile> getAllUploadFile();
    public boolean deleteUploadFile(long studentId, long fileId);
}
