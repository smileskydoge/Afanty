package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.StatusDomain;
import com.booksroo.classroom.common.domain.Student;
import com.booksroo.classroom.common.query.StudentQuery;

import java.util.List;

public interface StudentMapper {

    List<String> getStuNameByClass(long classId);

    int updateStatus(StatusDomain statusDomain);

    List<Student> selectForWeb(StudentQuery query);
    List<Student> select(StudentQuery query);

    long countWeb(StudentQuery query);
    long count(StudentQuery query);

    /**
     * student数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int deleteByPrimaryKey(Long id);

    /**
     * student数据表的操作方法: insert  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int insert(Student record);

    /**
     * student数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int insertSelective(Student record);

    /**
     * student数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-03 15:54:05
     */
    Student selectByPrimaryKey(Long id);

    /**
     * student数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int updateByPrimaryKeySelective(Student record);

    /**
     * student数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-03 15:54:05
     */
    int updateByPrimaryKey(Student record);
}