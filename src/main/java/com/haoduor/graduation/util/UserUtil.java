package com.haoduor.graduation.util;

import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.UserService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    @Autowired
    private UserService userService;

    /**
     * 缓存用户id
     * @param subject
     */
    public void cacheId(Subject subject) {
        // 检查用户是否登录
        if (!subject.isAuthenticated()) {
            return;
        }

        // 获取shiro session
        Session se = subject.getSession();
        // 判断id 是否为空
        if (se.getAttribute("id") != null) {
            return;
        }

        // 获取用户名
        String username = (String) se.getAttribute("username");

        // 根据用户名获取用户实体
        User u = userService.getUserByName(username);

        // 用户是否存在
        if (u != null) {
            // 设置用户id
            se.setAttribute("id", u.getId());
        }
    }

    /**
     * 判断用户是否是自己
     * @param id 用户的id
     * @param subject 用户subject
     * @return
     */
    public boolean isMe(Long id, Subject subject) {
        // 获取shiro session
        Session se = subject.getSession();
        // 判断id 是否为空 是则尝试缓存
        if (se.getAttribute("id") == null) {
            cacheId(subject);
        }

        // 从session 中获取用户id
        Long seId = (Long) se.getAttribute("id");

        // 判断id 是否是自己
        // seId 可能为空所以放在后面
        return id.equals(seId);
    }
}
