package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/6 19:26
 */
public enum UserResourceEnum {

    CREATOR(1);

    private Integer relation;//用户跟资源关系

    UserResourceEnum(int relation) {
        this.relation = relation;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }
}
