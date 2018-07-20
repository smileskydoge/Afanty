package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class ResourceClass extends BaseDomain {

    /**
     * 资源id -- resource_class.resource_id
     * 创建时间 : 2018-06-13 10:21:04
     */
    private Long resourceId;

    /**
     * 老师班级关系表的id -- resource_class.teacher_class_id
     * 创建时间 : 2018-06-13 10:21:04
     */
    private Long teacherClassId;

    /**
     * 0-否，1-是 -- resource_class.del_flag
     * 创建时间 : 2018-06-13 10:21:04
     */
    private Boolean delFlag;

    /**
     * resource_class表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-13 10:21:04
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 resource_class.resource_id的get方法 
     * 创建时间 : 2018-06-13 10:21:04
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * 数据字段 resource_class.resource_id的set方法
     * 创建时间 : 2018-06-13 10:21:04
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 数据字段 resource_class.teacher_class_id的get方法 
     * 创建时间 : 2018-06-13 10:21:04
     */
    public Long getTeacherClassId() {
        return teacherClassId;
    }

    /**
     * 数据字段 resource_class.teacher_class_id的set方法
     * 创建时间 : 2018-06-13 10:21:04
     */
    public void setTeacherClassId(Long teacherClassId) {
        this.teacherClassId = teacherClassId;
    }

    /**
     * 数据字段 resource_class.del_flag的get方法 
     * 创建时间 : 2018-06-13 10:21:04
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 数据字段 resource_class.del_flag的set方法
     * 创建时间 : 2018-06-13 10:21:04
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}