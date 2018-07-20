package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */
public enum UserEnum {

    TEACHER("teacher", 1), STUDENT("student", 2);
    private String type;
    private Integer value;

    UserEnum() {
    }

    UserEnum(String type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public static boolean isTeacher(String type) {
        return UserEnum.TEACHER.getType().equalsIgnoreCase(type);
    }

    public static boolean isStudent(String type) {
        return UserEnum.STUDENT.getType().equalsIgnoreCase(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
