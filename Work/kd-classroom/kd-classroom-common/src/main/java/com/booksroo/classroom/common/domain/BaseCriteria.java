package com.booksroo.classroom.common.domain;


import com.booksroo.classroom.common.query.BaseQuery;

public class BaseCriteria {

    protected String orderByClause;
    private int start;
    private int pageNo = 1;
    private int limit = 10;
    protected boolean distinct;

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public int getStart() {
        return start > 0 ? start : ((getPageNo() - 1) * limit);
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageNo() {
        return pageNo > 0 ? pageNo : 0;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getLimit() {
        return limit > 10000 ? 10000 : limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public void bindPage(BaseQuery query) {
        this.pageNo = query.getPage();
        this.limit = query.getLimit();
    }

    public void bindOrderBy(BaseQuery query) {
        this.orderByClause = query.getOrderByStr();
    }

    public void noLimit() {
        this.limit = 10000;
    }

    public void limitOne() {
        this.limit = 1;
    }
}