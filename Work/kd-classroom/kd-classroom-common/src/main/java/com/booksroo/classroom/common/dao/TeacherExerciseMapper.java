package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.TeacherExercise;
import com.booksroo.classroom.common.query.BaseQuery;

import java.util.List;

public interface TeacherExerciseMapper {

    int delEByEIdAndTid(TeacherExercise teacherExercise);

    List<TeacherExercise> getExcerciseInfoByTeacherId(BaseQuery query);

    /**
     * teacher_exercise数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int deleteByPrimaryKey(Long id);

    /**
     * teacher_exercise数据表的操作方法: insert  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int insert(TeacherExercise record);

    /**
     * teacher_exercise数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int insertSelective(TeacherExercise record);

    /**
     * teacher_exercise数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-04 13:04:26
     */
    TeacherExercise selectByPrimaryKey(Long id);

    /**
     * teacher_exercise数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int updateByPrimaryKeySelective(TeacherExercise record);

    /**
     * teacher_exercise数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int updateByPrimaryKey(TeacherExercise record);
}