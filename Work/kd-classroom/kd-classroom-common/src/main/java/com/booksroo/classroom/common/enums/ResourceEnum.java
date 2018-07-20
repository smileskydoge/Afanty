package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */
public enum ResourceEnum {

    PPT(1), IMAGE(2);
    private int type;

    ResourceEnum() {
    }

    ResourceEnum(int type) {
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
