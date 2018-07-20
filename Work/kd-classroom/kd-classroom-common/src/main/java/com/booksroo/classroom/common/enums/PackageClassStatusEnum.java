package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/13 22:22
 */
public enum PackageClassStatusEnum {
    UN_START(0, "未上课"), STARTED(1,"已上课")
    ;

    private Integer status;
    private String desc;

    PackageClassStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
