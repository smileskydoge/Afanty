package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.TeacherClass;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.ClassDomainQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TeacherClassMapper {

    Long queryIdByCidAndTid(TeacherClass teacherClass);

    Set<Long> queryClassIdsByTeacherId(@Param("teacherId") Long teacherId);

    List<TeacherClass> queryTeacherIdsByClass(@Param("classIds") Set<Long> classIds);

    int deleteOriMappingInfo(Long teacherId);

    int deleteOriRetainInfo(BaseQuery baseQuery);

    int updateTeacherClassInfo(TeacherClass teacherClass);

    List<TeacherClass> select(ClassDomainQuery query);
    /**
     * teacher_class数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-04 09:52:19
     */
    int deleteByPrimaryKey(Long id);

    /**
     * teacher_class数据表的操作方法: insert  
     * 创建时间 : 2018-06-04 09:52:19
     */
    int insert(TeacherClass record);

    /**
     * teacher_class数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-04 09:52:19
     */
    int insertSelective(TeacherClass record);

    /**
     * teacher_class数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-04 09:52:19
     */
    TeacherClass selectByPrimaryKey(Long id);

    /**
     * teacher_class数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-04 09:52:19
     */
    int updateByPrimaryKeySelective(TeacherClass record);

    /**
     * teacher_class数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-04 09:52:19
     */
    int updateByPrimaryKey(TeacherClass record);
}