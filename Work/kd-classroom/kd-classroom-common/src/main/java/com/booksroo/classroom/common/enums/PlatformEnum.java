package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */
public enum PlatformEnum {

    PC(1), MOBILE(2);
    private Integer type;

    PlatformEnum() {
    }

    PlatformEnum(Integer type) {
        this.type = type;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static boolean isPC(int platform) {
        return PlatformEnum.PC.type == platform;
    }

    public static boolean isMobile(int platform) {
        return PlatformEnum.MOBILE.type == platform;
    }
}
