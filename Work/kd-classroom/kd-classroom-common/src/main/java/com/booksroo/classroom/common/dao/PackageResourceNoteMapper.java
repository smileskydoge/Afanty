package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.PackageResourceNote;
import com.booksroo.classroom.common.query.PackageResourceNoteQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackageResourceNoteMapper {

    List<PackageResourceNote> select(PackageResourceNoteQuery query);

    int count(PackageResourceNoteQuery query);

    int countJoinResource(PackageResourceNoteQuery query);

    List<PackageResourceNote> selectJoinResource(PackageResourceNoteQuery query);

    int updateTeacherShowFlag(@Param("packageClassId") long packageClassId, @Param("resourceId") long resourceId, @Param("teacherShowFlag") short teacherShowFlag);

    /**
     * package_resource_note数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-13 23:06:07
     */
    int deleteByPrimaryKey(Long id);

    /**
     * package_resource_note数据表的操作方法: insert
     * 创建时间 : 2018-06-13 23:06:07
     */
    int insert(PackageResourceNote record);

    /**
     * package_resource_note数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-13 23:06:07
     */
    int insertSelective(PackageResourceNote record);

    /**
     * package_resource_note数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-13 23:06:07
     */
    PackageResourceNote selectByPrimaryKey(Long id);

    /**
     * package_resource_note数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-13 23:06:07
     */
    int updateByPrimaryKeySelective(PackageResourceNote record);

    /**
     * package_resource_note数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-13 23:06:07
     */
    int updateByPrimaryKey(PackageResourceNote record);
}