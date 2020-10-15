package com.haoduor.graduation.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.haoduor.graduation.adapter.CommonAdapter;
import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.exception.DuplicateIdException;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.vo.BaseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/import")
public class UploadController {

    @Value("${file.save-path}")
    private String savePath;

    @Value("${file.tmp-path}")
    private String tmpPath;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommonAdapter adapter;

    @Autowired
    private UserService userService;

    private static List<String> suffix = Arrays.asList("xlsx", "xls");

    @PostMapping("/student")
    public BaseMessage uploadStudent(MultipartFile file) {
        Subject currentUser = SecurityUtils.getSubject();

        if (file != null) {
            String filename = file.getOriginalFilename();
            File tmpFile = new File(tmpPath + filename);

            if (tmpFile.exists()) {
                FileUtil.del(tmpFile);
            } else {
                FileUtil.touch(tmpFile);
            }

            try {
                file.transferTo(tmpFile);
            } catch (IOException e) {
                return new BaseMessage(2, "文件io 出错");
            }

            String type = FileTypeUtil.getType(tmpFile);
            if (suffix.contains(type)) {
                ExcelReader reader = ExcelUtil.getReader(tmpFile);
                List<List<Object>> contents = reader.read();

                List<Object> title = contents.get(0);

                if (!adapter.studentTitleEqual(title)) {
                    return new BaseMessage(3, "表头不符合");
                }

                boolean isFirst = false;

                List<StudentDto> students = new LinkedList<>();
                for (List<Object> o: contents) {
                    StudentDto tmp = null;
                    try {
                        tmp = adapter.toStudent(o);
                    } catch (DuplicateIdException e) {
                        return new BaseMessage(4, "id重复 " + e.getId());
                    } catch (Exception e) {

                    }

                    students.add(tmp);
                }

                boolean commit = true;
                for (StudentDto student : students) {
                    boolean res = userService.addStudentDto(student);
                    if (!res) {
                        commit = false;
                    }
                }

                if (commit) {
                    return new BaseMessage(1, "导入成功");
                } else {
                    return new BaseMessage(6, "数据库出错");
                }
            }
        }

        return new BaseMessage(5, "文件不能为空");
    }

    @PostMapping("/teacher")
    public BaseMessage uploadTeacher(MultipartFile file) {
        return null;
    }
}
