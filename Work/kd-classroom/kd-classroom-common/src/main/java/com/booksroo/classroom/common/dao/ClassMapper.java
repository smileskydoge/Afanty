package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.query.ClassDomainQuery;
import com.booksroo.classroom.common.query.ClassQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassMapper {

    int addStudentNum(Long classId);

    int subtractStudentNum(Long classId);

    List<ClassDomain> queryTeacherClassInfoByTeacherId(Long teacherId);

    List<ClassDomain> queryClassBySchool(@Param("schoolId") Long school);

    List<ClassDomain> queryClassList(ClassQuery classQuery);

    Long count(ClassQuery classQuery);

    List<ClassDomain> select(ClassDomainQuery query);
    /**
     * class数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-02 18:10:27
     */
    int deleteByPrimaryKey(Long id);

    /**
     * class数据表的操作方法: insert  
     * 创建时间 : 2018-06-02 18:10:27
     */
    int insert(ClassDomain record);

    /**
     * class数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-02 18:10:27
     */
    int insertSelective(ClassDomain record);

    /**
     * class数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-02 18:10:27
     */
    ClassDomain selectByPrimaryKey(Long id);

    /**
     * class数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-02 18:10:27
     */
    int updateByPrimaryKeySelective(ClassDomain record);

    /**
     * class数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-02 18:10:27
     */
    int updateByPrimaryKey(ClassDomain record);

}