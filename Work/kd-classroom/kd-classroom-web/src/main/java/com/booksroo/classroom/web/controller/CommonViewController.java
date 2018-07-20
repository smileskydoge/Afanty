package com.booksroo.classroom.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liujianjian
 * @date 2018/6/7 22:55
 */

@Controller
@RequestMapping("/common")
public class CommonViewController {

    @RequestMapping("/toTLogin")
    public String toTLogin() {
        return "teacher/login";
    }

}
