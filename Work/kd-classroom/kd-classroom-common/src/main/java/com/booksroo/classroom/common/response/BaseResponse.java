package com.booksroo.classroom.common.response;

import lombok.Data;

import java.io.Serializable;

import static com.booksroo.classroom.common.constant.CommonConstant.SYSTEM_ERROR;

/**
 * 通用响应对象
 *
 * @author liujianjian
 * @date 2018/6/2 23:20
 */
@Data
public class BaseResponse<T> implements Serializable {

    private boolean success;

    private int result;

    private String msgCode;

    private String message;

    private T data;

    public BaseResponse() {
        this.success = true;
        this.result = 1;
        this.msgCode = "success";
        this.message = "成功";
    }

    public BaseResponse(boolean success, int result, String msgCode, String message, T data) {
        this.success = success;
        this.result = result;
        this.msgCode = msgCode;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse success() {
        return new BaseResponse();
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> success(String code, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        response.setMsgCode(code);
        return response;
    }

    public static BaseResponse<String> fail(String msgCode, String message) {
        return new BaseResponse<>(false, 0, msgCode, message, null);
    }

    public static BaseResponse<String> error(String message) {
        return new BaseResponse<>(false, 0, SYSTEM_ERROR, message, null);
    }
}
