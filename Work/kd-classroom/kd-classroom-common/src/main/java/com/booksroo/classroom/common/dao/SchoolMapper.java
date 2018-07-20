package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.query.SchoolQuery;

import java.util.List;

public interface SchoolMapper {

    List<School> querySchoolList(SchoolQuery schoolQuery);

    long count(SchoolQuery schoolQuery);

    List<School> queryAllSchoolInfo();
    /**
     * school数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * school数据表的操作方法: insert  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(School record);

    /**
     * school数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(School record);

    /**
     * school数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    School selectByPrimaryKey(Long id);

    /**
     * school数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(School record);

    /**
     * school数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(School record);
}