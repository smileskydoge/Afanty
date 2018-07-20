package com.booksroo.classroom.common.request;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/13 11:59
 */
@Data
public class BaseRequest {

    private int pageNo = 1;
    private int pageSize = 10;

    public int getStart() {
        if (pageNo < 1) pageNo = 1;
        return (pageNo - 1) * pageSize;
    }

    public int getSize() {
        return pageSize;
    }

}
