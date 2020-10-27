package com.haoduor.graduation.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.haoduor.graduation.dao.DepartmentMapper;
import com.haoduor.graduation.model.Department;
import com.haoduor.graduation.model.DepartmentExample;
import com.haoduor.graduation.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public Department addDepartment(String name) {
        Department department = new Department();
        department.setId(snowflake.nextId());
        department.setName(name);

        Department tmp = getDepartmentByName(name);
        if (tmp != null) {
            return tmp;
        }

        departmentMapper.insert(department);
        return department;
    }

    @Override
    public Department getOrAddDepartment(String name) {
        Department department = getDepartmentByName(name);
        if (department == null) {
            return addDepartment(name);
        }

        return department;
    }

    @Override
    public Department getDepartmentByName(String name) {
        DepartmentExample de = new DepartmentExample();
        de.createCriteria().andNameEqualTo(name);

        List<Department> res = departmentMapper.selectByExample(de);

        if (res.size() == 1) {
            return res.get(0);
        }

        return null;
    }

    @Override
    public Department getDepartmentById(long id) {
        DepartmentExample de = new DepartmentExample();
        de.createCriteria().andIdEqualTo(id);

        List<Department> res = departmentMapper.selectByExample(de);

        if (res.size() == 1) {
            return res.get(0);
        }

        return null;
    }
}
