package com.haoduor.graduation.service;

import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.model.User;

import java.util.List;

public interface UserService {
    public User getUserByName(String username);
    public List<User> getUserByRole(long roleId);
    public List<User> getAllUsers();
    public boolean addStudentDto(StudentDto s);
}
