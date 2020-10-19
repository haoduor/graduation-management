package com.haoduor.graduation.util;

import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResponseUtil {

    public static void sendExcel(HttpServletResponse response, String filename, ExcelWriter writer) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

        OutputStream out = response.getOutputStream();
        writer.flush(out);
    }
}
