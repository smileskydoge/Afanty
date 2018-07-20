package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.query.SubjectQuery;

import java.util.List;

public interface SubjectMapper {

    List<Subject> queryAllSubjectInfo();

    /**
     * 查询全部学科信息
     * @return
     */
    List<Subject> querySubjectListInfo(SubjectQuery subjectQuery);

    /**
     * 查询学科总数
     * @return
     */
    long count(SubjectQuery subjectQuery);
    /**
     * subject数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * subject数据表的操作方法: insert  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(Subject record);

    /**
     * subject数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(Subject record);

    /**
     * subject数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    Subject selectByPrimaryKey(Long id);

    /**
     * subject数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(Subject record);

    /**
     * subject数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(Subject record);
}