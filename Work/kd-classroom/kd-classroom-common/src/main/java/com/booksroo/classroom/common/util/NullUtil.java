package com.booksroo.classroom.common.util;

/**
 * @author liujianjian
 * @date 2018/6/3 10:49
 */
public class NullUtil {

    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }
}
