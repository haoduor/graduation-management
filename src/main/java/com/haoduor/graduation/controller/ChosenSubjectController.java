package com.haoduor.graduation.controller;

import com.haoduor.graduation.vo.PageMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chosen")
public class ChosenSubjectController {

    @GetMapping("/student")
    public PageMessage studentChosen(@RequestParam String studentId) {
        return null;
    }

    @GetMapping("/teacher")
    public PageMessage teacherChosen(@RequestParam String teacherId) {
        return null;
    }

    @GetMapping("/all")
    public PageMessage allChosen() {
        return null;
    }

}
