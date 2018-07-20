package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.response.BaseResponse;
import com.booksroo.classroom.common.util.LayuiUtil;
import com.booksroo.classroom.common.vo.PageList;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujianjian
 * @date 2018/6/2 23:19
 */
public class BaseController {
    protected final Logger log = Logger.getLogger(this.getClass());

    protected BaseResponse success() {
        return BaseResponse.success();
    }

    protected <T> BaseResponse<T> success(T data) {
        return BaseResponse.success(data);
    }

    protected BaseResponse fail(String code, String msg) {
        return BaseResponse.fail(code, msg);
    }

    protected BaseResponse error(String msg) {
        return BaseResponse.error(msg);
    }

    protected Map<String, Object> tableResp(PageList pageList) {
        return LayuiUtil.tableResp(pageList);
    }
}
