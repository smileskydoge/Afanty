package com.booksroo.classroom.common.exception;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/3 9:37
 */
@Data
public class BizException extends Exception {
    private String code;

    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }
}
