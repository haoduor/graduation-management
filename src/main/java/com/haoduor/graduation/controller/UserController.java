package com.haoduor.graduation.controller;

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
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public BaseMessage renewPassword(@RequestParam long id, @RequestParam String password) {
        if (StrUtil.isEmpty(password)) {
            return new BaseMessage(2, "密码不能为空");
        }

        User u = userService.getUserById(id);
        Role admin = roleService.getAdminRole();
        if (u == null) {
            return new BaseMessage(3, "无效id");
        }

        if (u.getRoleId().equals(admin.getId())) {
            return new BaseMessage(5, "无法更改管理员id");
        }

        if (userService.setUserPasswordById(id, password, u.getSalt())) {
            return new BaseMessage(1, "更改密码成功");
        } else {
            return new BaseMessage(4, "数据库错误");
        }
    }

    // 用户更改密码
    @ResponseBody
    @PostMapping("/repass")
    public BaseMessage resetPassword(@RequestParam String id, @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        if (StrUtil.isEmpty(oldPassword) || StrUtil.isEmpty(newPassword)) {
            return new BaseMessage(3, "参数不能为空");
        }

        Subject cu = SecurityUtils.getSubject();
        userUtil.cacheId(cu);

        Long dbId = (Long) cu.getSession().getAttribute("id");

        long _id;
        try {
            _id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new BaseMessage(2, "id格式化失败");
        }

        if (!dbId.equals(_id)) {
            return new BaseMessage(4, "不能更改其他人的密码");
        }

        User u = userService.getUserById(dbId);
        String dbpass = u.getPassword();
        String salt = u.getSalt();
        String tdbpass = EncryptedUtil.encryptedPassword(dbpass, salt);
        String inpass = EncryptedUtil.encryptedPassword(oldPassword, salt);

        if (!tdbpass.equals(inpass)) {
            return new BaseMessage(5, "旧密码不符合");
        }

        u.setPassword(EncryptedUtil.encryptedPassword(newPassword, salt));

        if (userService.setUserPasswordByUser(dbId, u)) {
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
        String seUsername = (String) se.getAttribute("username");
        if (StrUtil.isEmpty(seUsername)) {
            return new DataMessage(2, "用户未登录");
        }

        if (seUsername.equals(username) || cu.hasRole("admin")) {
            User user = userService.getUserByName(username);
            if (user == null) {
                return new DataMessage(4, "用户名不存在");
            }

            Role r = roleService.getRoleById(user.getRoleId());
            DataMessage dm = new DataMessage(1, "获取成功");

            if (!cu.hasRole("admin")) {
                se.setAttribute("id", user.getId());
            }

            Object tmpData = infoFactory.getDataByRoleAndId(r.getName(), user.getId());

            JSONObject jo = (JSONObject) JSONObject.toJSON(tmpData);
            Long departmentId = (Long) jo.get("departmentId");

            Department tmpD = departmentService.getDepartmentById(departmentId);
            if (tmpD != null) {
                return new DataMessage(4, "数据库出错");
            }

            jo.put("departmentName", tmpD.getName());
            dm.setData(jo);

            return dm;
        } else {
            return new DataMessage(3, "权限不足");
        }
    }
}
