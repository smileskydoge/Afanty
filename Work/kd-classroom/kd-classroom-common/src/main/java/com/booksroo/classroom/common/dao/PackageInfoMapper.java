package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.PackageInfo;
import com.booksroo.classroom.common.query.PackageInfoQuery;

import java.util.List;

public interface PackageInfoMapper {

    List<PackageInfo> select(PackageInfoQuery query);

    int count(PackageInfoQuery query);

    /**
     * package_info数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-07 10:41:19
     */
    int deleteByPrimaryKey(Long id);

    /**
     * package_info数据表的操作方法: insert
     * 创建时间 : 2018-06-07 10:41:19
     */
    int insert(PackageInfo record);

    /**
     * package_info数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-07 10:41:19
     */
    int insertSelective(PackageInfo record);

    /**
     * package_info数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-07 10:41:19
     */
    PackageInfo selectByPrimaryKey(Long id);

    /**
     * package_info数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-07 10:41:19
     */
    int updateByPrimaryKeySelective(PackageInfo record);

    /**
     * package_info数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-07 10:41:19
     */
    int updateByPrimaryKey(PackageInfo record);
}