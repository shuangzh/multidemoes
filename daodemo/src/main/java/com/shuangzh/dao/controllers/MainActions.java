package com.shuangzh.dao.controllers;

import com.shuangzh.dao.mybatis.domain.Student;
import com.shuangzh.dao.mybatis.domain.Tutor;
import com.shuangzh.dao.utils.JsonUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2017/3/14.
 */

@Controller
public class MainActions {

    @Autowired
    SqlSessionFactory sessionFactory;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/index")
    public ModelAndView Index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/info")
    public ModelAndView Info() {
        return new ModelAndView("info").addObject("name", "zhoushuang");
    }


    @RequestMapping("/login-do")
    public String loginAction(HttpServletRequest request, ModelMap modelMap) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        logger.info("LOGIN-DO: receive request name:{}, email:{}, password :{}, role:{}", name, email, password, role);
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            if (role.equals("student")) {
                Student student = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentByName", name);
                if (student == null) {
                    modelMap.addAttribute("errmsg", name + " is not exist as student");
                    return "login";
                } else {
                    request.getSession().setAttribute("s_user", student);
                    request.getSession().setAttribute("s_role", "student");
                    return "main";
                }
            } else if (role.equals("tutor")) {
                modelMap.addAttribute("errmsg", "tutor is int to-do list");
                return "login";
            } else if (role.equals("admin")) {
                modelMap.addAttribute("errmsg", "admin is int to-do list");
                return "login";
            } else {
                modelMap.addAttribute("errmsg", " cant' reginized role");
                return "login";
            }
        } finally {
            sqlSession.close();
        }

    }


    public String userInfo(HttpServletRequest request, ModelMap modelMap) {
        return null;
    }


    /**
     * 跳转到登录页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView toLoginPage(ModelMap modelMap) {
        return new ModelAndView("login").addAllObjects(modelMap);
    }

    /**
     * 显示学生主界面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/student/main")
    public ModelAndView toStudentMainPage(ModelMap modelMap) {
        return new ModelAndView("stud_main").addAllObjects(modelMap);
    }

    @RequestMapping("/student/info")
    public ModelAndView toStudentInfoPage(HttpServletRequest request, ModelMap modelMap) throws IOException {
        Student student = (Student) request.getSession().getAttribute("s_user");

        Map uuser = JsonUtil.toJsonMap(student);
        if (student.getPhone() != null) {
            uuser.put("phone", student.getPhone().getAsString());
        } else {
            uuser.put("phone", "");
        }
        modelMap.put("uuser", uuser);
        return new ModelAndView("stud_info").addAllObjects(modelMap);
    }

    @RequestMapping("/student/courses")
    public ModelAndView toStudentCoursePage(HttpServletRequest request, ModelMap modelMap) {
        return  new ModelAndView("stud_course").addAllObjects(modelMap);
    }


}
