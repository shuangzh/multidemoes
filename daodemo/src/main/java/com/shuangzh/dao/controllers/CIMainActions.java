package com.shuangzh.dao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by admin on 2017/4/6.
 */

@Controller
public class CIMainActions {

    @RequestMapping("/ci/cies")
    public String toCIPage() {
        return "ci_main";
    }

}
