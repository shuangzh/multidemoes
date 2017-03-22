package com.shuangzh.dao.controllers;

import com.shuangzh.dao.mybatis.domain.Student;
import com.shuangzh.dao.utils.JsonUtil;
import com.sun.javafx.collections.MappingChange;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2017/3/17.
 */
@RestController
public class RestActions {

    @Autowired
    SqlSessionFactory sessionFactory;

    @RequestMapping("/rest/getStudent")
    public Student getStudent(@RequestParam("id") int id) {
        SqlSession sqlSession = sessionFactory.openSession();
        Student st1 = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentWithAddress", id);
        sqlSession.close();
        return st1;
    }

    @RequestMapping("/rest/login")
    public RestResponse restLogin(HttpServletRequest request) {
        RestResponse resp = new RestResponse();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            if (role.equals("student")) {
                Student student = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentByName", name);
                if (student != null) {
                    request.getSession().setAttribute("s_user", student);
                    request.getSession().setAttribute("s_role", "student");
                    Map<String, java.lang.Object> datamap = JsonUtil.toJsonMap(student);
                    datamap.put("role", "student");
                    resp.setData(datamap);
                } else {
                    resp.setCode(1);
                    resp.setMessage("Don't exist");
                }
            } else {
                resp.setCode(1);
                resp.setMessage("Role must be student");
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.setCode(1);
            resp.setMessage("internal exception");
        } finally {
            sqlSession.close();
        }
        return resp;
    }

}
