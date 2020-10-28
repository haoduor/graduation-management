package com.haoduor.graduation.vo;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.classfile.PMGClass;
import lombok.Data;

/**
 * nowPage 当前页面
 * totalPage 全部页码
 * data 数据
 */
@Data
public class PageMessage {
    long nowPage;
    long totalPage;
    long total;
    Object data;

    public static PageMessage instance(PageInfo pages) {
        PageMessage pm = new PageMessage();
        pm.setTotal(pages.getTotal());
        pm.setNowPage(pages.getPageNum());
        pm.setTotalPage(pages.getPages());
        return pm;
    }
}
