package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.ResourceClass;
import com.booksroo.classroom.common.query.ResourceQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceClassMapper {

    List<ResourceClass> select(ResourceQuery query);

    List<ResourceClass> selectByJoin(ResourceQuery query);

    int count(ResourceQuery query);

    int countByJoin(ResourceQuery query);

    int updateByTCIdAndRid(ResourceClass query);

    int deleteByTCIdAndRid(@Param("teacherClassId") long teacherClassId, @Param("resourceId") long resourceId);

    int deleteByPackageClassId(@Param("packageClassId") long packageClassId);

    int deleteByResourceId(@Param("resourceId") long resourceId);

    /**
     * resource_class数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-07 10:41:19
     */
    int deleteByPrimaryKey(Long id);

    /**
     * resource_class数据表的操作方法: insert
     * 创建时间 : 2018-06-07 10:41:19
     */
    int insert(ResourceClass record);

    /**
     * resource_class数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-07 10:41:19
     */
    int insertSelective(ResourceClass record);

    /**
     * resource_class数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-07 10:41:19
     */
    ResourceClass selectByPrimaryKey(Long id);

    /**
     * resource_class数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-07 10:41:19
     */
    int updateByPrimaryKeySelective(ResourceClass record);

    /**
     * resource_class数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-07 10:41:19
     */
    int updateByPrimaryKey(ResourceClass record);
}