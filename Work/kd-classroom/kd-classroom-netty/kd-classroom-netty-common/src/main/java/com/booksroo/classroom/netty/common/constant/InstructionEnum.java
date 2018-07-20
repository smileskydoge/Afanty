package com.booksroo.classroom.netty.common.constant;

/**
 * @author liujianjian
 * @date 2018/6/21 22:15
 */
public enum InstructionEnum {

    TEACHER_START_CLASS("teacher_start_class", "教师开始上课"),
    TEACHER_END_CLASS("teacher_end_class", "教师结束上课"),
    STUDENT_RECONNECT("student_reconnect", "学生端重连"),;

    private String key;
    private String desc;


    InstructionEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean isTeacherStartClass(String instruction) {
        return InstructionEnum.TEACHER_START_CLASS.getKey().equals(instruction);
    }

    public static boolean isTeacherEndClass(String instruction) {
        return InstructionEnum.TEACHER_END_CLASS.getKey().equals(instruction);
    }

    public static boolean isStudentReconnect(String instruction) {
        return InstructionEnum.STUDENT_RECONNECT.getKey().equals(instruction);
    }
}
