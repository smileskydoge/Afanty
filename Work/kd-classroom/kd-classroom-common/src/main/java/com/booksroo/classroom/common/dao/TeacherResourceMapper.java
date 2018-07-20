package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.TeacherResource;
import com.booksroo.classroom.common.query.TeacherResourceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherResourceMapper {

    List<TeacherResource> selectJoinResource(TeacherResourceQuery query);

    List<TeacherResource> select(TeacherResourceQuery query);

    int count(TeacherResourceQuery query);

    int countJoinResource(TeacherResourceQuery query);

    int delete(@Param("teacherId") long teacherId, @Param("resourceId") long resourceId);

    /**
     * teacher_resource数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-06 19:11:47
     */
    int deleteByPrimaryKey(Long id);

    /**
     * teacher_resource数据表的操作方法: insert
     * 创建时间 : 2018-06-06 19:11:47
     */
    int insert(TeacherResource record);

    /**
     * teacher_resource数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-06 19:11:47
     */
    int insertSelective(TeacherResource record);

    /**
     * teacher_resource数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-06 19:11:47
     */
    TeacherResource selectByPrimaryKey(Long id);

    /**
     * teacher_resource数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-06 19:11:47
     */
    int updateByPrimaryKeySelective(TeacherResource record);

    /**
     * teacher_resource数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-06 19:11:47
     */
    int updateByPrimaryKey(TeacherResource record);
}