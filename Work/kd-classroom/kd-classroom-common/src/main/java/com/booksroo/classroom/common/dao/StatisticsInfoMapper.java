package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.StatisticsInfo;
import com.booksroo.classroom.common.domain.StatisticsInfoCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticsInfoMapper {
    int insertBatch(@Param(value = "records") List<StatisticsInfo> records);

    /**
     * statistics_info数据表的操作方法: countByExample  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int countByExample(StatisticsInfoCriteria example);

    /**
     * statistics_info数据表的操作方法: deleteByExample  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int deleteByExample(StatisticsInfoCriteria example);

    /**
     * statistics_info数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int deleteByPrimaryKey(Long id);

    /**
     * statistics_info数据表的操作方法: insert  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int insert(StatisticsInfo record);

    /**
     * statistics_info数据表的操作方法: insertSelective  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int insertSelective(StatisticsInfo record);

    /**
     * statistics_info数据表的操作方法: selectByExample  
     * 创建时间 : 2018-07-12 19:05:35
     */
    List<StatisticsInfo> selectByExample(StatisticsInfoCriteria example);

    /**
     * statistics_info数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-07-12 19:05:35
     */
    StatisticsInfo selectByPrimaryKey(Long id);

    /**
     * statistics_info数据表的操作方法: updateByExampleSelective  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int updateByExampleSelective(@Param("record") StatisticsInfo record, @Param("example") StatisticsInfoCriteria example);

    /**
     * statistics_info数据表的操作方法: updateByExample  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int updateByExample(@Param("record") StatisticsInfo record, @Param("example") StatisticsInfoCriteria example);

    /**
     * statistics_info数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int updateByPrimaryKeySelective(StatisticsInfo record);

    /**
     * statistics_info数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-07-12 19:05:35
     */
    int updateByPrimaryKey(StatisticsInfo record);
}