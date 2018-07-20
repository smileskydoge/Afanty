package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.CatalogResources;

public interface CatalogResourcesMapper {
    /**
     * catalog_resources数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Long id);

    /**
     * catalog_resources数据表的操作方法: insert  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(CatalogResources record);

    /**
     * catalog_resources数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(CatalogResources record);

    /**
     * catalog_resources数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    CatalogResources selectByPrimaryKey(Long id);

    /**
     * catalog_resources数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(CatalogResources record);

    /**
     * catalog_resources数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(CatalogResources record);
}