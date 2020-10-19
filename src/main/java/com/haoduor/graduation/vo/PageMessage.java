package com.haoduor.graduation.vo;

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
}
