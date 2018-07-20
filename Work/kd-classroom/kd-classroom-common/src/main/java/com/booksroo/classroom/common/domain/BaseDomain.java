package com.booksroo.classroom.common.domain;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liujianjian
 * @date 2018/6/2 17:34
 */
public class BaseDomain implements Serializable {

    private Long id;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTimeStr() {
        return formatDateTime(createTime);
    }

    public String getUpdateTimeStr() {
        return formatDateTime(updateTime);
    }

    protected String toViewStr(Object obj) {
        return obj == null ? "-" : String.valueOf(obj);
    }

    protected String formatDateTime(Date date) {
        return date != null ? DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss") : "";
    }

    protected String formatDate(Date date) {
        return date != null ? DateFormatUtils.format(date, "yyyy.MM.dd") : "";
    }

}
