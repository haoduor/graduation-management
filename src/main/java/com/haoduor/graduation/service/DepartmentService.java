package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Department;

public interface DepartmentService {
    public Department addDepartment(String name);
    public Department getOrAddDepartment(String name);
    public Department getDepartmentByName(String name);
    public Department getDepartmentById(long id);
}
