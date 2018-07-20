package com.booksroo.classroom.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujianjian
 * @date 2018/6/7 19:32
 */
public class HttpUtil {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getHeader("x-requested-with"));
    }
}
