package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.ExerciseClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExerciseClassMapper {

    int delECMapper(ExerciseClass exerciseClass);

    List<ExerciseClass>  selectByTCId(@Param("teacherClassId") Long teacherClassId);

    /**
     * exercise_class数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int deleteByPrimaryKey(Long id);

    /**
     * exercise_class数据表的操作方法: insert  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int insert(ExerciseClass record);

    /**
     * exercise_class数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int insertSelective(ExerciseClass record);

    /**
     * exercise_class数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-04 13:04:26
     */
    ExerciseClass selectByPrimaryKey(Long id);

    /**
     * exercise_class数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int updateByPrimaryKeySelective(ExerciseClass record);

    /**
     * exercise_class数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-04 13:04:26
     */
    int updateByPrimaryKey(ExerciseClass record);
}