package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Teacher extends BaseDomain {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher.school_id
     * 创建时间 : 2018-06-04 09:52:19
     */
    private Long schoolId;

    /**
     * 学科id，多个用逗号分隔 -- teacher.subject_id
     * 创建时间 : 2018-06-04 09:52:19
     */
    private String subjectId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher.teacher_name
     * 创建时间 : 2018-06-04 09:52:19
     */
    private String teacherName;

    /**
     * 手机号 -- teacher.mobile_no
     * 创建时间 : 2018-06-04 09:52:19
     */
    private String mobileNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher.password
     * 创建时间 : 2018-06-04 09:52:19
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher.age
     * 创建时间 : 2018-06-04 09:52:19
     */
    private Byte age;

    /**
     * 头像地址 -- teacher.head_img
     * 创建时间 : 2018-06-04 09:52:19
     */
    private String headImg;

    /**
     * 职称，1-班主任 -- teacher.job_title
     * 创建时间 : 2018-06-04 09:52:19
     */
    private Byte jobTitle;

    /**
     * 是否删除，0-否，1-是 -- teacher.del_flag
     * 创建时间 : 2018-06-04 09:52:19
     */
    private Boolean delFlag;

    /**
     * teacher表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-04 09:52:19
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 teacher.school_id的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public Long getSchoolId() {
        return schoolId;
    }

    /**
     * 数据字段 teacher.school_id的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 数据字段 teacher.subject_id的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * 数据字段 teacher.subject_id的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId == null ? null : subjectId.trim();
    }

    /**
     * 数据字段 teacher.teacher_name的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 数据字段 teacher.teacher_name的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    /**
     * 数据字段 teacher.mobile_no的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * 数据字段 teacher.mobile_no的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    /**
     * 数据字段 teacher.password的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public String getPassword() {
        return password;
    }

    /**
     * 数据字段 teacher.password的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 数据字段 teacher.age的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public Byte getAge() {
        return age;
    }

    /**
     * 数据字段 teacher.age的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * 数据字段 teacher.head_img的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * 数据字段 teacher.head_img的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    /**
     * 数据字段 teacher.job_title的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public Byte getJobTitle() {
        return jobTitle;
    }

    /**
     * 数据字段 teacher.job_title的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setJobTitle(Byte jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * 数据字段 teacher.del_flag的get方法 
     * 创建时间 : 2018-06-04 09:52:19
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 数据字段 teacher.del_flag的set方法
     * 创建时间 : 2018-06-04 09:52:19
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}