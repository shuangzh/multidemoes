package com.shuangzh.dao.controllers;

import com.shuangzh.dao.mybatis.domain.Student;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2017/3/17.
 */
@RestController
public class RestActions {

    @Autowired
    SqlSessionFactory sessionFactory;

    @RequestMapping("/rest/getStudent")
    public Student getStudent(@RequestParam("id") int id) {
        SqlSession sqlSession=sessionFactory.openSession();
        Student st1 = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentWithAddress", id);
        sqlSession.close();
        return  st1;
    }
}
