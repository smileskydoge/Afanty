package com.booksroo.classroom.web.controller;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.service.interf.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.booksroo.classroom.common.constant.BizConstant.WEB_SESSION_USER_KEY;

/**
 * @author liujianjian
 * @date 2018/6/5 17:14
 */
@RestController
@RequestMapping("/common")
public class UtilController extends BaseController {

    @Autowired
    private CacheService redisCacheService;

    @RequestMapping("/setCache")
    public Object setRedisCache(String key, String val) {
        redisCacheService.set(key, val);

        return redisCacheService.get(key);
    }

    @RequestMapping("/getCache")
    public Object setRedisCache(String key) {
        return redisCacheService.get(key);
    }

    @RequestMapping("/delSession")
    public Object delSessionUser(HttpSession session) {

        Object obj = session.getAttribute(WEB_SESSION_USER_KEY);
        session.removeAttribute(WEB_SESSION_USER_KEY);

        String s = JSON.toJSONString(obj) + "_" + session.getAttribute(WEB_SESSION_USER_KEY);
        return success(s);
    }
}
