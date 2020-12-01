package com.haoduor.graduation.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.crypto.SecureUtil;
import com.haoduor.graduation.annotation.TimeCount;
import com.haoduor.graduation.model.UploadFile;
import com.haoduor.graduation.service.FinalSubjectService;
import com.haoduor.graduation.service.UploadFileService;
import com.haoduor.graduation.util.ResponseUtil;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.BaseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private FinalSubjectService finalSubjectService;

    @Autowired
    private UploadFileService uploadFileService;

    @Value("${file.save-path}")
    private String savePath;

    @Value("${file.tmp-path}")
    private String tmpPath;

    /**
     * 学生上传文件接口
     * @param file 文件
     * @param studentId 学生id
     * @param subjectId 课题id
     * @return
     */
    @TimeCount
    @PostMapping("/upload")
    @RequiresRoles("student")
    public BaseMessage upload(MultipartFile file,
                              @RequestParam String studentId,
                              @RequestParam String subjectId) {
        Subject currentUser = SecurityUtils.getSubject();

        Long _studentId;
        Long _subjectId;
        // 格式化id
        try {
            _studentId = Long.parseLong(studentId);
            _subjectId = Long.parseLong(subjectId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (!userUtil.isMe(_studentId, currentUser)) {
            return new BaseMessage(3, "不能为其他人上传文件");
        }

        // 判断学生拥有该 最终选题
        if (!finalSubjectService.ownFinalSubject(_studentId, _subjectId)) {
            return new BaseMessage(7, "学生不拥有最终选题");
        }

        if (file != null) {
            String filename = file.getOriginalFilename();
            File tmpFile = new File(tmpPath + filename);

            if (tmpFile.exists()) {
                FileUtil.del(tmpFile);
            }
            FileUtil.touch(tmpFile);

            try {
                file.transferTo(tmpFile);
            } catch (IOException e) {
                return new BaseMessage(4, "文件io 出错");
            }

            String sha256 = SecureUtil.sha256(tmpFile);
            File saveFile = new File(savePath + sha256);

            try {
                FileUtil.move(tmpFile, saveFile, true);
            } catch (IORuntimeException e) {
                return new BaseMessage(4, "文件转换出错");
            }
            int size = (int) file.getSize();

            boolean res = uploadFileService.addUploadFile(size, filename, sha256, _studentId, _subjectId);

            if (res) {
                return new BaseMessage(1, "上传成功");
            } else {
                return new BaseMessage(7, "数据库错误");
            }

        }

        return new BaseMessage(5, "文件不能为空");
    }

    /**
     * 文件下载
     * @param response
     * @param sha256 文件的sha256值
     * @return
     */
    @TimeCount
    @GetMapping("/download/{sha256}")
    public BaseMessage download(HttpServletResponse response, @PathVariable String sha256) {
        Subject currentUser = SecurityUtils.getSubject();

        UploadFile uf = uploadFileService.getFileBySha256(sha256);
        if (uf == null) {
            return new BaseMessage(2, "文件不存在");
        }

        if (currentUser.hasRole("student")) {
            if (!userUtil.isMe(uf.getStudentId(), currentUser)) {
                return new BaseMessage(3, "不能下载其他学生的文件");
            }
        }

        File f = new File(savePath + sha256);

        if (!f.exists()) {
            return new BaseMessage(4, "文件异常丢失");
        }

        try {
            ResponseUtil.sendFile(response, uf.getFilename(), f);
        } catch (IOException e) {
            return new BaseMessage(5, "文件io错误");
        }

        return new BaseMessage(1, "文件下载成功");
    }

    /**
     * 删除用户的文件
     * @param studentId 学生id
     * @param fileId 文件id
     * @return
     */
    @PostMapping("/delete")
    @RequiresRoles(value = {"student", "admin"}, logical = Logical.OR)
    public BaseMessage delete(@RequestParam String studentId,
                              @RequestParam String fileId) {
        Subject currentUser = SecurityUtils.getSubject();

        Long _studentId;
        Long _fileId;

        try {
            _studentId = Long.parseLong(studentId);
            _fileId = Long.parseLong(fileId);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        // 判断 用户是否删除的是自己的文件
        if (currentUser.hasRole("student")) {
            if(!userUtil.isMe(_studentId, currentUser)) {
                return new BaseMessage(4, "不能删除其他学生的文件");
            }
        }

        // 判断 文件是否存在与数据库中
        if (!uploadFileService.hasUploadFile(_studentId, _fileId)) {
            return new BaseMessage(3, "文件不存在");
        }

        // 删除数据库中的记录
        if (uploadFileService.deleteUploadFile(_studentId, _fileId)) {
            return new BaseMessage(1, "删除成功");
        } else {
            return new BaseMessage(5, "数据库错误");
        }
    }
}
