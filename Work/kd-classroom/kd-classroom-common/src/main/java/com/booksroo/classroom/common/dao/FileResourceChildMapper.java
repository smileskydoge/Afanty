package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.query.ResourceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileResourceChildMapper {

    List<FileResourceChild> select(ResourceQuery query);

    int count(ResourceQuery query);

    void insertBatch(@Param(value = "records") List<FileResourceChild> records);

    /**
     * file_resource_child数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * file_resource_child数据表的操作方法: insert
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(FileResourceChild record);

    /**
     * file_resource_child数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(FileResourceChild record);

    /**
     * file_resource_child数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-02 22:58:40
     */
    FileResourceChild selectByPrimaryKey(Long id);

    /**
     * file_resource_child数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(FileResourceChild record);

    /**
     * file_resource_child数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(FileResourceChild record);
}