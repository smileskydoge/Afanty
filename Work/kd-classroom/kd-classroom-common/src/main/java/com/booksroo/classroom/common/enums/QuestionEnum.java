package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */
public enum QuestionEnum {

    //1.选择题 2.判断题 3.填空题 4.主观题
    CHOICE(1, "选择题"), JUDGMENT(2, "判断题"),
    COMPLETION(3, "填空题"), SUBJECTIVE(4, "主观题");
    private Integer type;
    private String name;

    QuestionEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static boolean isChoice(int type) {
        return QuestionEnum.CHOICE.getType() == type;
    }

    public static boolean isJudgment(int type) {
        return QuestionEnum.JUDGMENT.getType() == type;
    }

    public static boolean isCompletion(int type) {
        return QuestionEnum.COMPLETION.getType() == type;
    }

    public static boolean isSubjective(int type) {
        return QuestionEnum.SUBJECTIVE.getType() == type;
    }

    public static boolean hasOptions(int type) {
        return QuestionEnum.CHOICE.getType() == type || QuestionEnum.JUDGMENT.getType() == type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
