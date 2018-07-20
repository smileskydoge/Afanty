package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.ExerciseDomainWithBLOBs;

public interface ExerciseDomainMapper {
    /**
     * exercise_info数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-18 15:24:03
     */
    int deleteByPrimaryKey(Long id);

    /**
     * exercise_info数据表的操作方法: insert  
     * 创建时间 : 2018-07-18 15:24:03
     */
    int insert(ExerciseDomainWithBLOBs record);

    /**
     * exercise_info数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-18 15:24:03
     */
    int insertSelective(ExerciseDomainWithBLOBs record);

    /**
     * exercise_info数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-18 15:24:03
     */
    ExerciseDomainWithBLOBs selectByPrimaryKey(Long id);

    /**
     * exercise_info数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-18 15:24:03
     */
    int updateByPrimaryKeySelective(ExerciseDomainWithBLOBs record);

    /**
     * exercise_info数据表的操作方法: updateByPrimaryKeyWithBLOBs  
     * 创建时间 : 2018-07-18 15:24:03
     */
    int updateByPrimaryKeyWithBLOBs(ExerciseDomainWithBLOBs record);

    /**
     * exercise_info数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-18 15:24:03
     */
    int updateByPrimaryKey(ExerciseDomain record);
}