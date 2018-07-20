package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.ExerciseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExerciseDomainMapper {


    int delExerciseOnAll(@Param("exerciseId") Long exerciseId);

    List<ExerciseDomain> getExcerciseIdsByTeacherId(BaseQuery query);

    List<ExerciseDomain> getEIdsByTId(ExerciseQuery exerciseQuery);

    Long countByTeacherId(BaseQuery query);

    List<ExerciseDomain> getExerciseByids(BaseQuery query);

    List<ExerciseDomain> getEInfoByids(ExerciseQuery exerciseQuery);

    Long count(BaseQuery query);
    /**
     * exercise_info数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-09 20:01:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * exercise_info数据表的操作方法: insert  
     * 创建时间 : 2018-07-09 20:01:40
     */
    int insert(ExerciseDomain record);

    /**
     * exercise_info数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-09 20:01:40
     */
    int insertSelective(ExerciseDomain record);

    /**
     * exercise_info数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-09 20:01:40
     */
    ExerciseDomain selectByPrimaryKey(Long id);

    /**
     * exercise_info数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-09 20:01:40
     */
    int updateByPrimaryKeySelective(ExerciseDomain record);

    /**
     * exercise_info数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-09 20:01:40
     */
    int updateByPrimaryKey(ExerciseDomain record);
}