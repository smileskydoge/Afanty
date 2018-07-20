package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.FileResources;
import com.booksroo.classroom.common.query.ResourceQuery;

import java.util.List;

public interface FileResourcesMapper {

    List<FileResources> select(ResourceQuery query);

    int count(ResourceQuery query);
    /**
     * file_resources数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * file_resources数据表的操作方法: insert  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(FileResources record);

    /**
     * file_resources数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(FileResources record);

    /**
     * file_resources数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    FileResources selectByPrimaryKey(Long id);

    /**
     * file_resources数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(FileResources record);

    /**
     * file_resources数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(FileResources record);
}