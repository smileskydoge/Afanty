package com.booksroo.classroom.common.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class AdminDomain implements Serializable {
    /**
     * 管理员ID -- admin.id
     * 创建时间 : 2018-06-04 16:03:15
     */
    private Long id;

    /**
     * 管理员账号 -- admin.account
     * 创建时间 : 2018-06-04 16:03:15
     */
    private String account;

    /**
     * 管理员密码 -- admin.password
     * 创建时间 : 2018-06-04 16:03:15
     */
    private String password;

    /**
     * 创建账号时间 -- admin.create_time
     * 创建时间 : 2018-06-04 16:03:15
     */
    private Date createTime;

    /**
     * 修改账号时间 -- admin.update_time
     * 创建时间 : 2018-06-04 16:03:15
     */
    private Date updateTime;

    /**
     * admin表的操作属性:serialVersionUID
     * 创建时间 : 2018-06-04 16:03:15
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 admin.id的get方法 
     * 创建时间 : 2018-06-04 16:03:15
     */
    public Long getId() {
        return id;
    }

    /**
     * 数据字段 admin.id的set方法
     * 创建时间 : 2018-06-04 16:03:15
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 数据字段 admin.account的get方法 
     * 创建时间 : 2018-06-04 16:03:15
     */
    public String getAccount() {
        return account;
    }

    /**
     * 数据字段 admin.account的set方法
     * 创建时间 : 2018-06-04 16:03:15
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 数据字段 admin.password的get方法 
     * 创建时间 : 2018-06-04 16:03:15
     */
    public String getPassword() {
        return password;
    }

    /**
     * 数据字段 admin.password的set方法
     * 创建时间 : 2018-06-04 16:03:15
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 数据字段 admin.create_time的get方法 
     * 创建时间 : 2018-06-04 16:03:15
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 数据字段 admin.create_time的set方法
     * 创建时间 : 2018-06-04 16:03:15
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 数据字段 admin.update_time的get方法 
     * 创建时间 : 2018-06-04 16:03:15
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 数据字段 admin.update_time的set方法
     * 创建时间 : 2018-06-04 16:03:15
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}