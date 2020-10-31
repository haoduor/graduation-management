package com.haoduor.graduation.controller;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import com.haoduor.graduation.model.Period;
import com.haoduor.graduation.service.PeriodService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.DataMessage;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/period")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping("/list")
    public DataMessage list() {
        DataMessage dm = new DataMessage(1, "获取成功");

        List<Period> periods = periodService.getPeriod();
        if (periods.size() == 0) {
            return new DataMessage(2, "数据库初始化失败");
        }
        dm.setData(periods);

        return dm;
    }

    @PostMapping("/set")
    @RequiresRoles("admin")
    public BaseMessage set(String id, String startTime, String endTime) {
        Long _id;
        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "格式化错误");
        }

        Date st;
        Date et;

        try {
            st = DateUtil.parse(startTime);
            et = DateUtil.parse(endTime);
        } catch (DateException e) {
            return new BaseMessage(3, "日期格式化失败");
        }

        if (st.after(et)) {
            return new BaseMessage(5, "开始时间不能再结束时间之后");
        }

        if (periodService.setPeriod(_id, st, et)) {
            return new BaseMessage(1, "更新成功");
        } else {
            return new BaseMessage(4, "数据库出错");
        }
    }

}
