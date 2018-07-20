package com.booksroo.classroom.netty.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.netty.common.bean.SocketData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujianjian
 * @date 2018/6/24 14:36
 */
public class BizSocketUtil {

    public static SocketData toSocketData(String str) {
        if (StringUtils.isBlank(str) || !str.startsWith("{")) return null;

        return JSON.parseObject(str, SocketData.class);
    }

    public static List<SocketData> toSocketDataList(String str) {
        if (StringUtils.isBlank(str) || !str.startsWith("[")) return null;

        JSONArray arr = JSONObject.parseArray(str);
        if (arr.size() <= 0) return null;

        List<SocketData> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            list.add(toSocketData(arr.getString(i)));
        }
        return list;
    }
}
