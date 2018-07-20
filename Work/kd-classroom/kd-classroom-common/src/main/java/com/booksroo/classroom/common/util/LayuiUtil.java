package com.booksroo.classroom.common.util;

import com.booksroo.classroom.common.vo.PageList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujianjian
 * @date 2018/6/7 18:48
 */
public class LayuiUtil {

    public static Map<String, Object> tableResp(PageList pageList) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", pageList.getRows());
        map.put("count", pageList.getTotal());
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }
}
