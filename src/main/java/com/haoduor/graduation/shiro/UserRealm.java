package com.haoduor.graduation.shiro;

import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.User;
import com.haoduor.graduation.service.RoleService;
import com.haoduor.graduation.service.UserService;
import com.haoduor.graduation.util.EncryptedUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        User user = userService.getUserByName(username);
        if (user != null) {
            Role role = roleService.getRoleById(user.getRoleId());
            if (role != null) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.addRole(role.getName());
                return info;
            }
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken t = (UsernamePasswordToken)token;

        // 获取用户名
        String username = t.getUsername();

        // 更具用户名 获取数据库的用户
        User dbuser = userService.getUserByName(username);
        if (dbuser == null) {
            // 如果数据库中没有用户 返回空用户错误
            throw new UnknownAccountException();
        }

        String pass = new String(t.getPassword());
        String salt = dbuser.getSalt();

        // 设置加密的密码
        t.setPassword(EncryptedUtil.encryptedPassword(pass, salt).toCharArray());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(), dbuser.getPassword(), getName());
        return info;
    }
}
