package com.haoduor.graduation.init;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.RoleMapper;
import com.haoduor.graduation.model.Period;
import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.RoleExample;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.PeriodService;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.util.EncryptedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PeriodService periodService;

    @Resource(name = "periodMap")
    private Map<String, Period> periodMap;

    @Autowired
    @Qualifier("snowflake")
    private Snowflake snowflake;

    @Resource(name = "studentFilter")
    private BitMapBloomFilter studentFilter;

    @Resource(name = "teacherFilter")
    private BitMapBloomFilter teacherFilter;

    // 服务器初始化
    @Override
    public void run(String... args) throws Exception {
        log.info("系统初始化");

        // 初始化角色库
        if (!roleIsGenerate()) {
            log.info("首次初始化 角色库");
            List<Role> roles = getAllRoles();

            for (Role r : roles) {
                roleMapper.insert(r);
            }
        }

        Role student = roleService.getStudentRole();
        Role teacher = roleService.getTeacherRole();

        log.info("初始化用户库");
        List<User> students = userService.getUserByRole(student.getId());

        for (User u: students) {
            studentFilter.add(u.getUsername());
        }

        log.info("初始化教师库");
        List<User> teachers = userService.getUserByRole(teacher.getId());

        for (User u: teachers) {
            teacherFilter.add(u.getUsername());
        }

        if (!userService.hasAdmin()) {
            log.info("添加初始化管理员");
            User admin = getDefaultAdmin();

            if (!userService.addUser(admin)) {
                log.info("添加默认管理员失败, 请检查数据库");
                System.exit(-1);
            }
        }

        if (periodService.getPeriod().size() < 1) {
            log.info("初始化阶段设置");

            Date startTime = DateUtil.parse("1997-01-01 12:00");
            Date endTime = DateUtil.parse("3000-01-01 12:00");

            periodService.addPeriod("选择课题-chose", startTime, endTime);
            periodService.addPeriod("开题报告-opening", startTime, endTime);
            periodService.addPeriod("中期检查-interim", startTime, endTime);
            periodService.addPeriod("验收/答辩-check", startTime, endTime);
            periodService.addPeriod("论文审核-paper", startTime, endTime);
        }

        log.info("初始化阶段缓存");
        List<Period> periods = periodService.getPeriod();
        for (Period p: periods) {
            String name = p.getName().split("-")[1];
            periodMap.put(name, p);
        }

        log.info("初始化完成");
    }

    // 生成默认admin
    private User getDefaultAdmin() {
        User u = new User();
        u.setUsername("admin");
        String salt = EncryptedUtil.getSalt();
        String password = EncryptedUtil.encryptedPassword("123456", salt);

        u.setSalt(salt);
        u.setPassword(password);
        u.setId(snowflake.nextId());
        Role admin = roleService.getAdminRole();

        u.setRoleId(admin.getId());

        return u;
    }

    // 生成所有角色
    private List<Role> getAllRoles() {
        List<Role> roles = new LinkedList<>();

        Role admin = new Role();
        Role student = new Role();
        Role teacher = new Role();

        admin.setId(snowflake.nextId());
        student.setId(snowflake.nextId());
        teacher.setId(snowflake.nextId());

        admin.setName("admin");
        student.setName("student");
        teacher.setName("teacher");

        roles.add(admin);
        roles.add(student);
        roles.add(teacher);
        return roles;
    }

    // 判断角色是否生成成功
    private boolean roleIsGenerate() {
        List<Role> res = roleMapper.selectByExample(new RoleExample());

        return res.size() == 3;
    }

}
