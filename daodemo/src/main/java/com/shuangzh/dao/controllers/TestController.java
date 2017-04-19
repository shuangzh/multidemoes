package com.shuangzh.dao.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2017/4/19.
 */
@Controller
public class TestController {

    @RequestMapping("/reqinfo")
    @ResponseBody
    public String requestInfo(HttpServletRequest request, HttpServletResponse response)
    {
        String cookie=request.getHeader("cookie");
        System.out.println("cookie:" + cookie);
        response.setHeader("set-cookie","newcookie=cookies");
        return "HELLO !";
    };

}
