package com.booksroo.classroom.common.util;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * <pre>
 * Author: liujianjian
 * DateTime: 16/12/19 下午9:18.
 * </pre>
 */
public class DruidUtil {
    public static void main(String[] args) throws Exception {
        String username = "root", pwd = "KdsOnline0627@2018";
        System.out.println("username=====" + ConfigTools.encrypt(username));
        System.out.println("pwd==========" + ConfigTools.encrypt(pwd));
        System.out.println("pwd==========" + ConfigTools.decrypt(ConfigTools.encrypt(pwd)));
    }
}
