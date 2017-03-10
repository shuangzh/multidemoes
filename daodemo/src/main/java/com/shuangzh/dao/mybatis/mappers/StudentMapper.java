package com.shuangzh.dao.mybatis.mappers;

import com.shuangzh.dao.mybatis.domain.Student;

import java.util.List;

/**
 * Created by admin on 2017/3/8.
 */
public interface StudentMapper {
    Student findStudentById(Integer id);
    int insertStudent(Student student);
    List<Student> findAllStudents();

}
