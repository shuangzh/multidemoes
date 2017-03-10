package com.shuangzh.dao.mybatis.services;

import com.shuangzh.dao.mybatis.domain.Student;
import com.shuangzh.dao.mybatis.mappers.StudentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2017/3/9.
 */

@Component
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("apifactory")
    SqlSessionFactory sqlSessionFactory;

    public List<Student> findAllStudents() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findAllStudents();
        } finally {
            sqlSession.close();
        }
    }


    public Student findStudentById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findStudentById(id);
        } finally {
            sqlSession.close();
        }
    }

    void createStudent(Student student){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            studentMapper.insertStudent(student);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
