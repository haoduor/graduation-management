package com.haoduor.graduation.vo;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.classfile.PMGClass;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * nowPage 当前页面
 * totalPage 全部页码
 * data 数据
 */
@Data
public class PageMessage implements Message{
    int id;
    String message;

    long nowPage;
    long totalPage;
    long total;
    Object data;

    public PageMessage() { }

    public PageMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public static PageMessage instance(PageInfo pages) {
        PageMessage pm = new PageMessage();
        pm.setTotal(pages.getTotal());
        pm.setNowPage(pages.getPageNum());
        pm.setTotalPage(pages.getPages());
        return pm;
    }

    @Override
    public Object invoke(int id, String message) {
        return new PageMessage(id, message);
    }
}
