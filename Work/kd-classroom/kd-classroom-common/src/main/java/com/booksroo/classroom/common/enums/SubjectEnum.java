package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */
public enum SubjectEnum {

//    语文 数学 英语 物理 化学 生物 政治 历史 地理 信息

    Chinese("Chinese", "YuWen", "语文", 1), Math("Math", "ShuXue", "数学", 2),
    English("English", "YingYu", "英语", 3), Physics("Physics", "WuLi", "物理", 4),
    Chemistry("Chemistry", "HuaXue", "化学", 5), Biology("Biology", "ShengWu", "生物", 6),
    Politics("Politics", "ZhengZhi", "政治", 7), History("History", "LiShi", "历史", 8),
    Geography("Geography", "DiLi", "地理", 9), IT("IT", "XinXiJiShu", "信息技术", 10),;
    private String subjectEN;
    private String subjectPinyin;
    private String subjectName;
    private Integer type;
    private int orderNo;//序号

    SubjectEnum() {
    }

    SubjectEnum(String subjectEN, String subjectPinyin, String subjectName, int orderNo) {
        this.subjectName = subjectName;
        this.subjectPinyin = subjectPinyin;
        this.orderNo = orderNo;
        this.subjectEN = subjectEN;
    }

    public static SubjectEnum getByName(String name) {
        if (name == null || name.equals("")) return null;

        name = name.trim();
        for (SubjectEnum e : SubjectEnum.values()) {
            if (e.getSubjectName().equals(name)) return e;
        }
        return null;
    }

    public static String getPinyinByName(String name) {
        if (name == null || name.equals("")) return "";

        name = name.trim();
        for (SubjectEnum e : SubjectEnum.values()) {
            if (e.getSubjectName().equals(name)) return e.getSubjectPinyin();
        }
        return "";
    }

//    public static Map<>

    public String getSubjectPinyin() {
        return subjectPinyin;
    }

    public void setSubjectPinyin(String subjectPinyin) {
        this.subjectPinyin = subjectPinyin;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getSubjectEN() {
        return subjectEN;
    }

    public void setSubjectEN(String subjectEN) {
        this.subjectEN = subjectEN;
    }
}
