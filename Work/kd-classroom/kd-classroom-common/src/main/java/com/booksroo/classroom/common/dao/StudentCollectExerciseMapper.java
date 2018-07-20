package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.StudentCollectExercise;
import com.booksroo.classroom.common.query.StudentCollectExercisesQuery;

import java.util.List;

public interface StudentCollectExerciseMapper {

    List<StudentCollectExercise> select(StudentCollectExercisesQuery query);
    int count(StudentCollectExercisesQuery query);

    int delCollect(StudentCollectExercise studentCollectExercise);
    /**
     * student_collect_exercise数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-12 19:21:52
     */
    int deleteByPrimaryKey(Long id);

    /**
     * student_collect_exercise数据表的操作方法: insert  
     * 创建时间 : 2018-07-12 19:21:52
     */
    int insert(StudentCollectExercise record);

    /**
     * student_collect_exercise数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-12 19:21:52
     */
    int insertSelective(StudentCollectExercise record);

    /**
     * student_collect_exercise数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-12 19:21:52
     */
    StudentCollectExercise selectByPrimaryKey(Long id);

    /**
     * student_collect_exercise数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-12 19:21:52
     */
    int updateByPrimaryKeySelective(StudentCollectExercise record);

    /**
     * student_collect_exercise数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-12 19:21:52
     */
    int updateByPrimaryKey(StudentCollectExercise record);
}