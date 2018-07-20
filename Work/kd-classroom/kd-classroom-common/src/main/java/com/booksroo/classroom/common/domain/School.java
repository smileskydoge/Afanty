package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.Date;

public class School extends BaseDomain {

    /**
     * 所属父学校id -- school.parent_id
     * 创建时间 : 2018-06-02 22:58:40
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school.school_name
     * 创建时间 : 2018-06-02 22:58:40
     */
    private String schoolName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column school.area
     * 创建时间 : 2018-06-02 22:58:40
     */
    private String area;

    /**
     * 学制，1-九年制，2-八年制 -- school.education_length
     * 创建时间 : 2018-06-02 22:58:40
     */
    private Byte educationLength;

    /**
     * 是否删除，0-否，1-是 -- school.del_flag
     * 创建时间 : 2018-06-02 22:58:40
     */
    private Boolean delFlag;

    /**
     * school表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-02 22:58:40
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 school.parent_id的get方法 
     * 创建时间 : 2018-06-02 22:58:40
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 数据字段 school.parent_id的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 数据字段 school.school_name的get方法 
     * 创建时间 : 2018-06-02 22:58:40
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 数据字段 school.school_name的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * 数据字段 school.area的get方法 
     * 创建时间 : 2018-06-02 22:58:40
     */
    public String getArea() {
        return area;
    }

    /**
     * 数据字段 school.area的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 数据字段 school.education_length的get方法 
     * 创建时间 : 2018-06-02 22:58:40
     */
    public Byte getEducationLength() {
        return educationLength;
    }

    /**
     * 数据字段 school.education_length的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setEducationLength(Byte educationLength) {
        this.educationLength = educationLength;
    }

    /**
     * 数据字段 school.del_flag的get方法 
     * 创建时间 : 2018-06-02 22:58:40
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 数据字段 school.del_flag的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}