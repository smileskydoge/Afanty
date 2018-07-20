package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class FileResources extends BaseDomain {
    /**
     * 教师id -- file_resources.teacher_id
     * 创建时间 : 2018-06-07 17:19:44
     */
    private Long teacherId;

    /**
     * 资源的名称 -- file_resources.resource_name
     * 创建时间 : 2018-06-07 17:19:44
     */
    private String resourceName;

    /**
     * 资源类型，1-ppt，2-图片 -- file_resources.resource_type
     * 创建时间 : 2018-06-07 17:19:44
     */
    private Byte resourceType;

    /**
     * 是否删除，0-否，1-是 -- file_resources.del_flag
     * 创建时间 : 2018-06-07 17:19:44
     */
    private Boolean delFlag;

    /**
     * file_resources表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-07 17:19:44
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 file_resources.teacher_id的get方法 
     * 创建时间 : 2018-06-07 17:19:44
     */
    public Long getTeacherId() {
        return teacherId;
    }

    /**
     * 数据字段 file_resources.teacher_id的set方法
     * 创建时间 : 2018-06-07 17:19:44
     */
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 数据字段 file_resources.resource_name的get方法 
     * 创建时间 : 2018-06-07 17:19:44
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 数据字段 file_resources.resource_name的set方法
     * 创建时间 : 2018-06-07 17:19:44
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * 数据字段 file_resources.resource_type的get方法 
     * 创建时间 : 2018-06-07 17:19:44
     */
    public Byte getResourceType() {
        return resourceType;
    }

    /**
     * 数据字段 file_resources.resource_type的set方法
     * 创建时间 : 2018-06-07 17:19:44
     */
    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 数据字段 file_resources.del_flag的get方法 
     * 创建时间 : 2018-06-07 17:19:44
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 数据字段 file_resources.del_flag的set方法
     * 创建时间 : 2018-06-07 17:19:44
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}