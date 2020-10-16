package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Role;
import com.haoduor.graduation.model.User;

public interface RoleService {
    public Role getRoleById(long id);
    public Role getRoleByName(String name);
    public Role getAdminRole();
    public Role getStudentRole();
    public Role getTeacherRole();
}
