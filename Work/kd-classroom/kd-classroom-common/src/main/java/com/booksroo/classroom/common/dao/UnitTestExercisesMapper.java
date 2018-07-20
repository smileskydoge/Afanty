package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.UnitTestExercises;
import com.booksroo.classroom.common.domain.UnitTestExercisesCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitTestExercisesMapper {

    int insertBatch(@Param(value = "records") List<UnitTestExercises> records);

    /**
     * unit_test_exercises数据表的操作方法: countByExample  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int countByExample(UnitTestExercisesCriteria example);

    /**
     * unit_test_exercises数据表的操作方法: deleteByExample  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int deleteByExample(UnitTestExercisesCriteria example);

    /**
     * unit_test_exercises数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int deleteByPrimaryKey(Long id);

    /**
     * unit_test_exercises数据表的操作方法: insert  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int insert(UnitTestExercises record);

    /**
     * unit_test_exercises数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int insertSelective(UnitTestExercises record);

    /**
     * unit_test_exercises数据表的操作方法: selectByExample  
     * 创建时间 : 2018-07-12 15:22:12
     */
    List<UnitTestExercises> selectByExample(UnitTestExercisesCriteria example);

    /**
     * unit_test_exercises数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-12 15:22:12
     */
    UnitTestExercises selectByPrimaryKey(Long id);

    /**
     * unit_test_exercises数据表的操作方法: updateByExampleSelective  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int updateByExampleSelective(@Param("record") UnitTestExercises record, @Param("example") UnitTestExercisesCriteria example);

    /**
     * unit_test_exercises数据表的操作方法: updateByExample  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int updateByExample(@Param("record") UnitTestExercises record, @Param("example") UnitTestExercisesCriteria example);

    /**
     * unit_test_exercises数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int updateByPrimaryKeySelective(UnitTestExercises record);

    /**
     * unit_test_exercises数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-12 15:22:12
     */
    int updateByPrimaryKey(UnitTestExercises record);
}