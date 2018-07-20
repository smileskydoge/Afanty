package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/2 23:53
 */
@Data
public class BaseQuery {
    private Long id;

    private Set<Long> ids;

    private int page = 1;

    private int limit = 10;

    private String orderByStr;

    private boolean delFlag = false;

    public int getStart() {
        if (page < 1) page = 1;
        return (page - 1) * limit;
    }

    public int getSize() {
        return limit > 10000 ? 10000 : limit;
    }

    public void noLimit() {
        this.page = 1;
        this.limit = 100000;
    }

//    public void noDel() {
//        this.delFlag = false;
//    }

    public void limitOne() {
        this.limit = 1;
    }
}
