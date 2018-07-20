package com.booksroo.classroom.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */
public enum StudentExercisesEnum {
    GOT_RIGHT(1, "right"), GOT_WRONG(2, "wrong");

    private Integer result;
    private String desc;

    StudentExercisesEnum(Integer result, String desc) {
        this.result = result;
        this.desc = desc;
    }

    public static boolean isRight(Short r) {
        return r != null && StudentExercisesEnum.GOT_RIGHT.getResult() == r.intValue();
    }

    public static boolean isWrong(Short r) {
        return r != null && StudentExercisesEnum.GOT_WRONG.getResult() == r.intValue();
    }

    public static Integer getResult(String desc) {
        if (StringUtils.isBlank(desc)) return null;

        for (StudentExercisesEnum e : StudentExercisesEnum.values()) {
            if (e.getDesc().equals(desc)) return e.getResult();
        }

        return null;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
