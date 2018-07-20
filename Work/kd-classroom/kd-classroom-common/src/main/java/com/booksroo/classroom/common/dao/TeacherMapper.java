package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.StatusDomain;
import com.booksroo.classroom.common.domain.Teacher;
import com.booksroo.classroom.common.query.TeacherQuery;

import java.util.List;

public interface TeacherMapper {

    int updateStatus(StatusDomain statusDomain);

    List<Teacher> queryTeacherList(TeacherQuery teacherQuery);

    Long count(TeacherQuery teacherQuery);

    Teacher selectByUnique(String mobileNo);

    /**
     * teacher数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int deleteByPrimaryKey(Long id);

    /**
     * teacher数据表的操作方法: insert  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int insert(Teacher record);

    /**
     * teacher数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int insertSelective(Teacher record);

    /**
     * teacher数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-03 15:54:05
     */
    Teacher selectByPrimaryKey(Long id);

    /**
     * teacher数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int updateByPrimaryKeySelective(Teacher record);

    /**
     * teacher数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int updateByPrimaryKey(Teacher record);
}