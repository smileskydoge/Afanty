package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class FileResourceChild extends BaseDomain {

    public FileResourceChild() {
    }

    public FileResourceChild(long resourceId, String content, int orderNo) {
        this.resourceId = resourceId;
        this.content = content;
        this.orderNo = orderNo;
    }

    /**
     * 父资源id -- file_resource_child.resource_id
     * 创建时间 : 2018-06-02 22:58:40
     */
    private Long resourceId;

    /**
     * 资源内容，一般是资源文件的url -- file_resource_child.content
     * 创建时间 : 2018-06-02 22:58:40
     */
    private String content;

    /**
     * 序号，比如ppt的页码 -- file_resource_child.order_no
     * 创建时间 : 2018-06-02 22:58:40
     */
    private Integer orderNo;

    /**
     * file_resource_child表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-02 22:58:40
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 file_resource_child.resource_id的get方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * 数据字段 file_resource_child.resource_id的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 数据字段 file_resource_child.content的get方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public String getContent() {
        return content;
    }

    /**
     * 数据字段 file_resource_child.content的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 数据字段 file_resource_child.order_no的get方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 数据字段 file_resource_child.order_no的set方法
     * 创建时间 : 2018-06-02 22:58:40
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}