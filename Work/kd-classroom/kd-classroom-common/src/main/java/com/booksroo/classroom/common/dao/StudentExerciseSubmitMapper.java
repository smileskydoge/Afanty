package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.StudentExerciseSubmit;
import com.booksroo.classroom.common.domain.StudentExerciseSubmitCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentExerciseSubmitMapper {

    List<Long> selectSubmitStuId(long uteId);

    List<Long> selectSubmitNoSelectStuId(long uteId);

    List<Long> selectRightStuId(long uteId);

    List<Long> selectWrongStuId(long uteId);

    int countSubmitNum(long uteId);

    int countSubmitNoSelectNum(long uteId);

    int countRightNum(long uteId);

    int countWrongNum(long uteId);

    int insertBatch(@Param(value = "records") List<StudentExerciseSubmit> records);

    /**
     * student_exercise_submit数据表的操作方法: countByExample
     * 创建时间 : 2018-07-12 17:03:48
     */
    int countByExample(StudentExerciseSubmitCriteria example);

    /**
     * student_exercise_submit数据表的操作方法: deleteByExample
     * 创建时间 : 2018-07-12 17:03:48
     */
    int deleteByExample(StudentExerciseSubmitCriteria example);

    /**
     * student_exercise_submit数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-07-12 17:03:48
     */
    int deleteByPrimaryKey(Long id);

    /**
     * student_exercise_submit数据表的操作方法: insert
     * 创建时间 : 2018-07-12 17:03:48
     */
    int insert(StudentExerciseSubmit record);

    /**
     * student_exercise_submit数据表的操作方法: insertSelective
     * 创建时间 : 2018-07-12 17:03:48
     */
    int insertSelective(StudentExerciseSubmit record);

    /**
     * student_exercise_submit数据表的操作方法: selectByExample
     * 创建时间 : 2018-07-12 17:03:48
     */
    List<StudentExerciseSubmit> selectByExample(StudentExerciseSubmitCriteria example);

    /**
     * student_exercise_submit数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-07-12 17:03:48
     */
    StudentExerciseSubmit selectByPrimaryKey(Long id);

    /**
     * student_exercise_submit数据表的操作方法: updateByExampleSelective
     * 创建时间 : 2018-07-12 17:03:48
     */
    int updateByExampleSelective(@Param("record") StudentExerciseSubmit record, @Param("example") StudentExerciseSubmitCriteria example);

    /**
     * student_exercise_submit数据表的操作方法: updateByExample
     * 创建时间 : 2018-07-12 17:03:48
     */
    int updateByExample(@Param("record") StudentExerciseSubmit record, @Param("example") StudentExerciseSubmitCriteria example);

    /**
     * student_exercise_submit数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-07-12 17:03:48
     */
    int updateByPrimaryKeySelective(StudentExerciseSubmit record);

    /**
     * student_exercise_submit数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-07-12 17:03:48
     */
    int updateByPrimaryKey(StudentExerciseSubmit record);
}