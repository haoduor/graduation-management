package com.haoduor.graduation.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.util.InfoFactory;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.DataMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private InfoFactory infoFactory;

    // 管理员设置(更改)密码
    @ResponseBody
    @PostMapping("/pass")
    public BaseMessage renewPassword(@RequestParam long id, @RequestParam String password) {
        if (StrUtil.isEmpty(password)) {
            return new BaseMessage(2, "密码不能为空");
        }

        User u = userService.getUserById(id);
        if (u == null) {
            return new BaseMessage(3, "无效id");
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
                                     @RequestParam String password) {
        return null;
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
            return new DataMessage(2, "未知错误");
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

            dm.setData(infoFactory.getDataByRoleAndId(r.getName(), user.getId()));

            return dm;
        } else {
            return new DataMessage(3, "权限不足");
        }
    }
}
