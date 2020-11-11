package com.haoduor.graduation.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class ResponseUtil {

    /**
     * 发送excel
     * @param response 响应对象
     * @param filename 文件名
     * @param writer excel 写出对象
     * @throws IOException
     */
    public static void sendExcel(HttpServletResponse response,
                                 String filename,
                                 ExcelWriter writer) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

        OutputStream out = response.getOutputStream();
        writer.flush(out);
    }

    /**
     * 发送文件
     * @param response 响应对象
     * @param filename 文件名
     * @param file 文件对象
     * @throws IOException
     */
    public static void sendFile(HttpServletResponse response,
                                String filename,
                                File file) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

        OutputStream os = response.getOutputStream();
        FileInputStream fs = new FileInputStream(file);

        IoUtil.copy(fs, os);

        IoUtil.close(os);
        IoUtil.close(fs);
    }
}
