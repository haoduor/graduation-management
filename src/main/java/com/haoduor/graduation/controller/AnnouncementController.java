package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haoduor.graduation.model.Announcement;
import com.haoduor.graduation.model.Subject;
import com.haoduor.graduation.service.AnnouncementService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.PageMessage;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public PageMessage list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "30") int pageSize) {

        PageHelper.startPage(page, pageSize);

        List<Announcement> data = announcementService.getAnnouncement();
        PageInfo<Announcement> pages = new PageInfo<>(data);
        PageHelper.clearPage();

        PageMessage pm = new PageMessage();
        pm.setTotal(pages.getTotal());
        pm.setNowPage(pages.getPageNum());
        pm.setTotalPage(pages.getPages());
        pm.setData(data);

        return pm;
    }


    @PostMapping("/add")
    public BaseMessage add(@RequestParam String content) {
        if (StrUtil.isEmpty(content)) {
            return new BaseMessage(2, "公告内容不能为空");
        }

        if (content.length() > 500) {
            return new BaseMessage(3, "公告内容不能大于500字");
        }

        if (announcementService.addAnnouncement(content)) {
            return new BaseMessage(1, "公告创建成功");
        } else {
            return new BaseMessage(4, "数据库错误");
        }
    }

    @PostMapping("/delete")
    public BaseMessage delete(@RequestParam String id) {
        long _id;
        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (announcementService.deleteAnnouncement(_id)) {
            return new BaseMessage(1, "删除成功");
        } else {
            return new BaseMessage(3, "数据库错误");
        }
    }

    @PostMapping("/set")
    public BaseMessage set(@RequestParam String id, @RequestParam String content) {
        if (StrUtil.isEmpty(content)) {
            return new BaseMessage(2, "公告内容不能为空");
        }

        if (content.length() > 500) {
            return new BaseMessage(3, "公告内容不能大于500字");
        }

        long _id;
        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        if (announcementService.setAnnouncement(_id, content)) {
            return new BaseMessage(1, "更新成功");
        } else {
            return new BaseMessage(3, "数据库错误");
        }
    }
}
