package com.booksroo.classroom.web.controller.pc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 跳转页面用的controller
 *
 * @author liujianjian
 * @date 2018/6/3 19:53
 */
@Controller
@RequestMapping("/pc/teacher")
public class PageViewController {

    @RequestMapping("/index")
    public String index() {

        return "teacher/index";
    }

    @RequestMapping("/resource")
    public String resource(String type) {

        if ("all".equalsIgnoreCase(type)) {
            return "teacher/resource-all";
        }
        return "teacher/resource";
    }
}
