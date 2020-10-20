package com.haoduor.graduation.adapter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.lang.Validator;
import com.haoduor.graduation.dto.StudentDto;
import com.haoduor.graduation.dto.TeacherDto;
import com.haoduor.graduation.exception.DuplicateIdException;
import com.haoduor.graduation.exception.ExcelFormatException;
import com.haoduor.graduation.exception.NameParseException;
import org.apache.commons.collections.ListUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.naming.InvalidNameException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class CommonAdapter {

    @Resource(name = "studentFilter")
    private BitMapBloomFilter studentFilter;

    @Resource(name = "teacherFilter")
    private BitMapBloomFilter teacherFilter;

    public static final List<String> studentTitle = Arrays.asList("学号","姓名","班级","系部");
    public static final List<String> teacherTitle = Arrays.asList("工号", "姓名", "系部");

    public boolean studentTitleEqual(List<Object> list) {
        return ListUtils.isEqualList(list, studentTitle);
    }

    public boolean teacherTitleEqual(List<Object> list) {
        return ListUtils.isEqualList(list, teacherTitle);
    }

    public StudentDto toStudent(List<Object> list, BitMapBloomFilter filter) throws Exception {
        if (list.size() == studentTitle.size()) {
            // 学号 需要仅数字校验
            long id = Long.parseLong(list.get(0).toString());

            StudentDto s = new StudentDto();
            // 学号 非重复校验
            if (studentFilter.contains(Long.toString(id)) || filter.contains(Long.toString(id))) {
                throw new DuplicateIdException(id);
            }
            s.setId(id);

            String name = (String)list.get(1);
            // 姓名 中只能有中文
            if (Validator.isChinese(name)) {
                s.setName(name);
            } else {
                throw new NameParseException();
            }

            // 班级和系部不能为空
            String classname = (String)list.get(2);
            String department = (String)list.get(3);

            s.setClassname(classname);
            s.setDepartment(department);
            return s;
        } else {
            throw new ExcelFormatException();
        }
    }

    public TeacherDto toTeacher(List<Object> list, BitMapBloomFilter fileter) throws DuplicateIdException, NameParseException, ExcelFormatException {
        if (list.size() == teacherTitle.size()) {
            String id = list.get(0).toString();

            if (fileter.contains(id) || teacherFilter.contains(id)) {
                throw new DuplicateIdException(id);
            }

            String username = (String) list.get(1);
            if (!Validator.isChinese(username)) {
                throw new NameParseException();
            }

            String department = (String)list.get(2);

            TeacherDto res = new TeacherDto();
            res.setId(id);
            res.setName(username);
            res.setDepartment(department);

            return res;
        } else {
            throw new ExcelFormatException();
        }
    }

}
