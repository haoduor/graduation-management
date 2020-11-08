package com.haoduor.graduation.controller;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import com.haoduor.graduation.model.Period;
import com.haoduor.graduation.service.PeriodService;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.DataMessage;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/period")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @Resource(name = "periodMap")
    private Map<String, Period> periodMap;

    /**
     * 获取每个阶段
     * @return
     */
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

    /**
     * 管理员设置阶段的时间
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/set")
    @RequiresRoles("admin")
    public BaseMessage set(@RequestParam String id,
                           @RequestParam String startTime,
                           @RequestParam String endTime) {
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
            Period p = periodService.getPeriodById(_id);
            if (p != null) {
                updatePeriodCache(p);
            } else {
                return new BaseMessage(6, "数据出错请查看控制台");
            }

            return new BaseMessage(1, "更新成功");
        } else {
            return new BaseMessage(4, "数据库出错");
        }
    }

    /**
     * 更新缓存中的阶段时间
     * @param p
     */
    private void updatePeriodCache(Period p) {
        String name = p.getName().split("-")[1];
        periodMap.put(name, p);
    }

}
