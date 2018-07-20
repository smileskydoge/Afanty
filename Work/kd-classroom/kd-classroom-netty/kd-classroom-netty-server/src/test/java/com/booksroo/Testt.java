package com.booksroo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.netty.common.bean.SocketData;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author liujianjian
 * @date 2018/6/24 13:09
 */
public class Testt {

    @Test
    public void test() {
        Set<String> list = new HashSet<>();
        list.add("a");
        list.add("b");

        list.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return "b".equals(s);
            }
        });

        System.out.println(list);
    }
}
