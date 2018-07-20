package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.SystemMenu;

public interface SystemMenuMapper {
    /**
     * system_menu数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * system_menu数据表的操作方法: insert  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insert(SystemMenu record);

    /**
     * system_menu数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int insertSelective(SystemMenu record);

    /**
     * system_menu数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    SystemMenu selectByPrimaryKey(Integer id);

    /**
     * system_menu数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKeySelective(SystemMenu record);

    /**
     * system_menu数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-02 22:58:40
     */
    int updateByPrimaryKey(SystemMenu record);
}