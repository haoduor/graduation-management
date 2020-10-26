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

    public void cacheId(Subject subject) {
        if (!subject.isAuthenticated()) {
            return;
        }

        Session se = subject.getSession();
        if (se.getAttribute("id") != null) {
            return;
        }

        String username = (String) se.getAttribute("username");

        User u = userService.getUserByName(username);
        if (u != null) {
            se.setAttribute("id", u.getId());
        }
    }
}
