package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.PackageResource;
import com.booksroo.classroom.common.query.PackageResourceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackageResourceMapper {

    List<PackageResource> select(PackageResourceQuery query);
    List<PackageResource> selectJoinResource(PackageResourceQuery query);

    int count(PackageResourceQuery query);
    int countJoinResource(PackageResourceQuery query);

    List<Integer> selectPackageResourceType(long packageClassId);

    int updateByPackageClassId(PackageResource record);

    int deleteByPCIdAndRId(@Param("packageClassId") long packageClassId, @Param("resourceId") Long resourceId);

    int deleteByResourceId(@Param("resourceId") long resourceId);

    /**
     * package_resource数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-10 14:12:55
     */
    int deleteByPrimaryKey(Long id);

    /**
     * package_resource数据表的操作方法: insert
     * 创建时间 : 2018-06-10 14:12:55
     */
    int insert(PackageResource record);

    /**
     * package_resource数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-10 14:12:55
     */
    int insertSelective(PackageResource record);

    /**
     * package_resource数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-10 14:12:55
     */
    PackageResource selectByPrimaryKey(Long id);

    /**
     * package_resource数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-10 14:12:55
     */
    int updateByPrimaryKeySelective(PackageResource record);

    /**
     * package_resource数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-10 14:12:55
     */
    int updateByPrimaryKey(PackageResource record);
}