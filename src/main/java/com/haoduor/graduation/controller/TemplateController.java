package com.haoduor.graduation.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.adapter.CommonAdapter;
import com.haoduor.graduation.model.Template;
import com.haoduor.graduation.service.TemplateService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/template")
public class TemplateController {

    @Value("${file.save-path}")
    private String savePath;

    @Value("${file.tmp-path}")
    private String tmpPath;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private HttpServletRequest request;

    // 管理员上传样板文档
    @PostMapping("/upload")
    public BaseMessage upload(MultipartFile file) {
        Subject currentUser = SecurityUtils.getSubject();

        if (file != null){
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
                return new BaseMessage(3, "文件io 出错");
            }

            File saveFile = new File(savePath + filename);
            if (saveFile.exists()) {
                filename = RandomUtil.randomString(8) + " " + filename;
                saveFile = new File(savePath + filename);
            }

            try {
                FileUtil.move(tmpFile, saveFile, true);
            } catch (IORuntimeException e) {
                return new BaseMessage(4, "文件转换出错");
            }

            templateService.addTemplate(saveFile);

            log.info("管理员 上传了文件{}", filename);

            return new BaseMessage(1, "文件上传成功");
        }

        return new BaseMessage(2, "文件不能为空");
    }

    /**
     * 下载样板文档
     * @param sha256 文件的sha256值
     * @param response 用户的请求对象
     * @return
     */
    @GetMapping("/download/{sha256}")
    public BaseMessage download(@PathVariable String sha256, HttpServletResponse response) {
        if (StrUtil.isEmpty(sha256)) {
            return new BaseMessage(2, "参数不能为空");
        }

        Template te = templateService.getTemplateBySha256(sha256);
        if (te == null) {
            return new BaseMessage(3, "文件不存在");
        }

        String filename = te.getFileName();

        File file = new File(savePath + filename);
        if (file.exists()) {
            OutputStream os = null;

            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");

            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return new BaseMessage(6, "文件编码错误");
            }

            try {
                os = response.getOutputStream();
                FileInputStream fs = new FileInputStream(file);

                IoUtil.copy(fs, os);

                IoUtil.close(os);
                IoUtil.close(fs);
                return new BaseMessage(1, "文件上传成功");
            } catch (IOException e) {
                return new BaseMessage(5, "文件io错误");
            }
        } else {
            return new BaseMessage(4, "文件异常丢失");
        }
    }

    // 获取样板文档列表
    @GetMapping("/list")
    public PageMessage list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "30") int pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Template> res = templateService.getTemplate();
        PageInfo<Template> pageInfo = new PageInfo<>(res);

        PageMessage pm = new PageMessage();
        pm.setTotalPage(pageInfo.getTotal());
        pm.setNowPage(page);
        pm.setData(res);

        return pm;
    }

    // 下载批量导入学生导入excel
    @GetMapping("/student")
    public void getStudentTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.writeHeadRow(CommonAdapter.studentTitle);

        String filename = "学生导入样板.xls";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

        try {
            OutputStream out = response.getOutputStream();
            writer.flush(out);
        } catch (IOException e) {
            log.info("学生样板文件下载出错");
        }
    }

    // 下载批量导入教师excel
    @GetMapping("/teacher")
    public void getTeacherTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.writeHeadRow(CommonAdapter.teacherTitle);

        String filename = "教师导入样板.xls";

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

        try {
            OutputStream out = response.getOutputStream();
            writer.flush(out);
        } catch (IOException e) {
            log.info("教师样板文件下载出错");
        }
    }
}
