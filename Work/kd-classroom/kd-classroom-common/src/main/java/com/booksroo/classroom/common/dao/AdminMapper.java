package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.AdminDomain;

public interface AdminMapper {
    /**
     * 根据账号查询密码
     * @return
     */

    AdminDomain queryAdminInfoByAccount(String account);

    /**
     * admin数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-04 16:03:15
     */
    int deleteByPrimaryKey(Long id);

    /**
     * admin数据表的操作方法: insert  
     * 创建时间 : 2018-06-04 16:03:15
     */
    int insert(AdminDomain record);

    /**
     * admin数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-04 16:03:15
     */
    int insertSelective(AdminDomain record);

    /**
     * admin数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-04 16:03:15
     */
    AdminDomain selectByPrimaryKey(Long id);

    /**
     * admin数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-04 16:03:15
     */
    int updateByPrimaryKeySelective(AdminDomain record);

    /**
     * admin数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-04 16:03:15
     */
    int updateByPrimaryKey(AdminDomain record);
}