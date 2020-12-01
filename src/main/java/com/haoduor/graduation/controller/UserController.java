package com.haoduor.graduation.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.haoduor.graduation.model.Department;
import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.DepartmentService;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.util.EncryptedUtil;
import com.haoduor.graduation.util.InfoFactory;
import com.haoduor.graduation.util.UserUtil;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.DataMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private InfoFactory infoFactory;

    @Autowired
    private UserUtil userUtil;

    // 管理员设置(更改)密码
    @ResponseBody
    @PostMapping("/pass")
    @RequiresRoles("admin")
    public BaseMessage renewPassword(@RequestParam String id, @RequestParam String password) {

        Long _id;
        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(6, "id 格式化失败");
        }

        if (StrUtil.isEmpty(password)) {
            return new BaseMessage(2, "密码不能为空");
        }

        User u = userService.getUserById(_id);
        Role admin = roleService.getAdminRole();
        if (u == null) {
            return new BaseMessage(3, "无效id");
        }

        if (u.getRoleId().equals(admin.getId())) {
            return new BaseMessage(5, "无法更改管理员id");
        }

        if (userService.setUserPasswordById(_id, password, u.getSalt())) {
            return new BaseMessage(1, "更改密码成功");
        } else {
            return new BaseMessage(4, "数据库错误");
        }
    }

    /**
     * 用户更改密码
     * @param id 用户的id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @ResponseBody
    @PostMapping("/repass")
    public BaseMessage resetPassword(@RequestParam String id,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        // 判断传入参数是否为空
        if (StrUtil.isEmpty(oldPassword) || StrUtil.isEmpty(newPassword)) {
            return new BaseMessage(3, "参数不能为空");
        }

        Subject cu = SecurityUtils.getSubject();

        long _id;
        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "id格式化失败");
        }

        if (!userUtil.isMe(_id, cu)) {
            return new BaseMessage(4, "不能更改其他人的密码");
        }

        User u = userService.getUserById(_id);

        if (u == null) {
            return new BaseMessage(7, "用户不存在");
        }

        String dbpass = u.getPassword();
        String salt = u.getSalt();
        String inpass = EncryptedUtil.encryptedPassword(oldPassword, salt);

        if (!dbpass.equals(inpass)) {
            return new BaseMessage(5, "旧密码不符合");
        }

        u.setPassword(EncryptedUtil.encryptedPassword(newPassword, salt));

        if (userService.setUserPasswordByUser(_id, u)) {
            return new BaseMessage(1, "更改密码成功");
        } else {
            return new BaseMessage(6, "数据库错误");
        }
    }

    /**
     * 获取用户的个人信息
     * 必须登录之后才能访问
     * @param username
     * @return
     */
    @ResponseBody
    @GetMapping("/info/{username}")
    public DataMessage getUserInfo(@PathVariable String username) {
        Subject cu = SecurityUtils.getSubject();
        Session se = cu.getSession();

        // 获取session 中的用户名
        String seUsername = (String) se.getAttribute("username");
        if (StrUtil.isEmpty(seUsername)) {
            return new DataMessage(2, "用户未登录");
        }

        // 判断session 中的用户名 和 传入的用户名相同
        if (seUsername.equals(username) || cu.hasRole("admin")) {
            User user = userService.getUserByName(username);
            if (user == null) {
                return new DataMessage(4, "用户名不存在");
            }

            // 获取角色
            Role r = roleService.getRoleById(user.getRoleId());
            DataMessage dm = new DataMessage(1, "获取成功");

            // 管理员信息返回
            if (r.getName().equals("admin")) {
                Map<Object, Object> data = MapUtil.builder()
                                                  .put("username", user.getUsername())
                                                  .put("id", user.getId())
                                                  .build();

                dm.setData(data);
                return dm;
            }

            if (!cu.hasRole("admin")) {
                se.setAttribute("id", user.getId());
            }

            // 映射学生和教师的信息
            Object tmpData = infoFactory.getDataByRoleAndId(r.getName(), user.getId());

            // 获取系部的id
            JSONObject jo = (JSONObject) JSONObject.toJSON(tmpData);
            Long departmentId = (Long) jo.get("departmentId");

            // 从数据库获取系部实例
            Department tmpD = departmentService.getDepartmentById(departmentId);
            if (tmpD == null) {
                return new DataMessage(4, "数据库出错");
            }

            // 设置系部名
            jo.put("departmentName", tmpD.getName());
            dm.setData(jo);

            return dm;
        } else {
            return new DataMessage(3, "权限不足");
        }
    }
}
