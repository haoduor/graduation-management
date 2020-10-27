package com.haoduor.graduation.service;

import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.dto.TeacherDto;
import com.haoduor.graduation.model.User;

import java.util.List;

public interface UserService {
    public User getUserByName(String username);
    public User getUserById(long id);
    public List<User> getUserByRole(long roleId);
    public List<User> getAllUsers();
    public boolean setUserPasswordByUser(long id, User user);
    public boolean setUserPasswordById(long id, String password, String salt);
    public boolean addStudentDto(StudentDto s);
    public boolean addTeacherDto(TeacherDto t);
}
