package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.TeacherCatalog;
import com.booksroo.classroom.common.query.TeacherCatalogQuery;

import java.util.List;

public interface TeacherCatalogMapper {

    List<TeacherCatalog> select(TeacherCatalogQuery query);

    int count(TeacherCatalogQuery query);

    /**
     * teacher_catalog数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * teacher_catalog数据表的操作方法: insert
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(TeacherCatalog record);

    /**
     * teacher_catalog数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(TeacherCatalog record);

    /**
     * teacher_catalog数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-02 22:58:40
     */
    TeacherCatalog selectByPrimaryKey(Long id);

    /**
     * teacher_catalog数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(TeacherCatalog record);

    /**
     * teacher_catalog数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(TeacherCatalog record);
}