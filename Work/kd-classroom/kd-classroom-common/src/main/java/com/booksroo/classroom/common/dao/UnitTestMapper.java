package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.UnitTestCriteria;
import com.booksroo.classroom.common.domain.UnitTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitTestMapper {

    int insertGetId(UnitTest record);

    /**
     * unit_test数据表的操作方法: countByExample  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int countByExample(UnitTestCriteria example);

    /**
     * unit_test数据表的操作方法: deleteByExample  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int deleteByExample(UnitTestCriteria example);

    /**
     * unit_test数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int deleteByPrimaryKey(Long id);

    /**
     * unit_test数据表的操作方法: insert  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int insert(UnitTest record);

    /**
     * unit_test数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int insertSelective(UnitTest record);

    /**
     * unit_test数据表的操作方法: selectByExample  
     * 创建时间 : 2018-07-12 13:49:55
     */
    List<UnitTest> selectByExample(UnitTestCriteria example);

    /**
     * unit_test数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-12 13:49:55
     */
    UnitTest selectByPrimaryKey(Long id);

    /**
     * unit_test数据表的操作方法: updateByExampleSelective  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int updateByExampleSelective(@Param("record") UnitTest record, @Param("example") UnitTestCriteria example);

    /**
     * unit_test数据表的操作方法: updateByExample  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int updateByExample(@Param("record") UnitTest record, @Param("example") UnitTestCriteria example);

    /**
     * unit_test数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int updateByPrimaryKeySelective(UnitTest record);

    /**
     * unit_test数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-12 13:49:55
     */
    int updateByPrimaryKey(UnitTest record);
}