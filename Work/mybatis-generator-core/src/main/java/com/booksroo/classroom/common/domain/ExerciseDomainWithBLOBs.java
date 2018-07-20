package com.booksroo.classroom.common.domain;

import java.io.Serializable;

public class ExerciseDomainWithBLOBs extends ExerciseDomain implements Serializable {
    /**
     * 习题内容 -- exercise_info.content
     * 创建时间 : 2018-07-18 15:24:03
     */
    private String content;

    /**
     * 习题分析内容 -- exercise_info.analyze
     * 创建时间 : 2018-07-18 15:24:03
     */
    private String analyze;

    /**
     * exercise_info表的操作属性:serialVersionUID
     * 创建时间 : 2018-07-18 15:24:03
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据字段 exercise_info.content的get方法 
     * 创建时间 : 2018-07-18 15:24:03
     */
    public String getContent() {
        return content;
    }

    /**
     * 数据字段 exercise_info.content的set方法
     * 创建时间 : 2018-07-18 15:24:03
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 数据字段 exercise_info.analyze的get方法 
     * 创建时间 : 2018-07-18 15:24:03
     */
    public String getAnalyze() {
        return analyze;
    }

    /**
     * 数据字段 exercise_info.analyze的set方法
     * 创建时间 : 2018-07-18 15:24:03
     */
    public void setAnalyze(String analyze) {
        this.analyze = analyze == null ? null : analyze.trim();
    }
}