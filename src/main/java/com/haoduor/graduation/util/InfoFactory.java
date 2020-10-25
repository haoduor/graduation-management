package com.haoduor.graduation.util;

import com.haoduor.graduation.service.StudentService;
import com.haoduor.graduation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfoFactory {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    public static String studentRoleName = "student";

    public static String teacherRoleName = "teacher";

    public Object getDataByRoleAndId(String role, long id) {
        if (studentRoleName.equals(role)) {
            return studentService.getStudentById(id);
        }

        if (teacherRoleName.equals(role)) {
            return teacherService.getTeacherById(id);
        }

        return null;
    }
}
