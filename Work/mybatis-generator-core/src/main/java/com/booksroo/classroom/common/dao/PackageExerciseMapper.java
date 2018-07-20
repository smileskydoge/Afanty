package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.PackageExercise;

public interface PackageExerciseMapper {
    /**
     * package_exercise数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-05 22:01:17
     */
    int deleteByPrimaryKey(Long id);

    /**
     * package_exercise数据表的操作方法: insert  
     * 创建时间 : 2018-07-05 22:01:17
     */
    int insert(PackageExercise record);

    /**
     * package_exercise数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-05 22:01:17
     */
    int insertSelective(PackageExercise record);

    /**
     * package_exercise数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-05 22:01:17
     */
    PackageExercise selectByPrimaryKey(Long id);

    /**
     * package_exercise数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-05 22:01:17
     */
    int updateByPrimaryKeySelective(PackageExercise record);

    /**
     * package_exercise数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-05 22:01:17
     */
    int updateByPrimaryKey(PackageExercise record);
}