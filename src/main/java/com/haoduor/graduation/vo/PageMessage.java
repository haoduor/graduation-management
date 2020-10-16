package com.haoduor.graduation.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageMessage {
    int nowPage;
    long totalPage;
    Object data;
}
