package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Subject extends BaseDomain {

    /**
     * 学科名 -- subject.subject_name
     * 创建时间 : 2018-06-27 14:02:55
     */
    private String subjectName;

    /**
     * 封面图片 -- subject.cover_img
     * 创建时间 : 2018-06-27 14:02:55
     */
    private String coverImg;

    /**
     * 是否删除，0-否，1-是 -- subject.del_flag
     * 创建时间 : 2018-06-27 14:02:55
     */
    private Boolean delFlag;

    /**
     * subject表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-27 14:02:55
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 subject.subject_name的get方法 
     * 创建时间 : 2018-06-27 14:02:55
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 数据字段 subject.subject_name的set方法
     * 创建时间 : 2018-06-27 14:02:55
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 数据字段 subject.cover_img的get方法 
     * 创建时间 : 2018-06-27 14:02:55
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 数据字段 subject.cover_img的set方法
     * 创建时间 : 2018-06-27 14:02:55
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }

    /**
     * 数据字段 subject.del_flag的get方法 
     * 创建时间 : 2018-06-27 14:02:55
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 数据字段 subject.del_flag的set方法
     * 创建时间 : 2018-06-27 14:02:55
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}