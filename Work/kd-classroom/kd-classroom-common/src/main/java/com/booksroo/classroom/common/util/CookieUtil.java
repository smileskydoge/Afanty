package com.booksroo.classroom.common.util;

import javax.servlet.http.Cookie;

/**
 * @author liujianjian
 * @date 2018/6/6 9:13
 */
public class CookieUtil {

    public static Cookie createCookie(String key, String value) {
        return new Cookie(key, value);
    }

    public static String getCookieVal(String key, Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) return "";

        for (Cookie cookie : cookies) {//遍历cookie数组
            if (key.equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
