package com.shuangzh.dao.controllers;

import com.shuangzh.dao.mybatis.domain.Course;
import com.shuangzh.dao.mybatis.domain.PhoneNumber;
import com.shuangzh.dao.mybatis.domain.Student;
import com.shuangzh.dao.utils.JsonUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/3/17.
 */
@RestController
public class RestActions {

    @Autowired
    SqlSessionFactory sessionFactory;

    Logger logger = LoggerFactory.getLogger(getClass());

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

//        String email = request.getParameter("email");
//        String password = request.getParameter("password");

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


    @RequestMapping("/rest/student/postInfo")
    public RestResponse restPostStudentInfo(HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        Student para = new Student();
        String n_name = request.getParameter("name;");
        if (n_name != null)
            para.setName(n_name);

        String n_email = request.getParameter("email");
        if (n_email != null)
            para.setEmail(n_email);

        String n_phone = request.getParameter("phone");
        if (n_phone != null)
            para.setPhone(new PhoneNumber(n_phone));
        String n_dob = request.getParameter("dob");
        if (n_dob != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(n_dob);
                para.setDob(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Student user = (Student) request.getSession().getAttribute("s_user");
        para.setStudId(user.getStudId());
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            int i = sqlSession.update("com.shuangzh.dao.mybatis.mappers.StudentMapper.updateStudent", para);
            logger.info("update student affected {} line", i);
            if (i == 1) {
                logger.info("update sutdent {}  {} success", para.getStudId(), para.getName());
                sqlSession.commit();

            } else {
                logger.error("update affected {} more thant 1", i);
                sqlSession.rollback();
                restResponse.setCode(1);
                restResponse.setMessage("more than one affected , update operation has rollback");
            }
        } catch (Exception exception) {
            restResponse.setCode(5);
            restResponse.setMessage("Exception:" + exception.getMessage());
        } finally {
            sqlSession.close();
        }
        return restResponse;
    }


    @RequestMapping("/rest/student/getAllCourses")
    public List<Course> restGetAllCourses() {
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            return sqlSession.selectList("com.shuangzh.dao.mybatis.mappers.StudentMapper.findAllCourses");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }


}
