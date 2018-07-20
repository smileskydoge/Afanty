package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.PackageClass;
import com.booksroo.classroom.common.query.PackageInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackageClassMapper {

    List<PackageClass> select(PackageInfoQuery query);

    int count(PackageInfoQuery query);

    List<PackageClass> getHistoryList(@Param("classId") long classId, @Param("subjectId") String subjectId, @Param("start") int start, @Param("size") int size);

    int countHistory(@Param("classId") long classId, @Param("subjectId") String subjectId);

    int updateByTCIdAndPid(PackageClass record);

    int deleteByTCIdAndPid(@Param("teacherClassId") long teacherClassId, @Param("packageId") long packageId);

    int updatePackageIdByTCIdAndPid(@Param("teacherClassId") long teacherClassId, @Param("oldPackageId") long oldPackageId, @Param("newPackageId") long newPackageId);

    Long selectPCIdByPIdAndCId(PackageClass packageClass);

    int addPackageClassMapper(PackageClass record);
    /**
     * package_class数据表的操作方法: deleteByPrimaryKey
     * 创建时间 : 2018-06-10 14:12:55
     */
    int deleteByPrimaryKey(Long id);

    /**
     * package_class数据表的操作方法: insert
     * 创建时间 : 2018-06-10 14:12:55
     */
    int insert(PackageClass record);

    /**
     * package_class数据表的操作方法: insertSelective
     * 创建时间 : 2018-06-10 14:12:55
     */
    int insertSelective(PackageClass record);

    /**
     * package_class数据表的操作方法: selectByPrimaryKey
     * 创建时间 : 2018-06-10 14:12:55
     */
    PackageClass selectByPrimaryKey(Long id);

    /**
     * package_class数据表的操作方法: updateByPrimaryKeySelective
     * 创建时间 : 2018-06-10 14:12:55
     */
    int updateByPrimaryKeySelective(PackageClass record);

    /**
     * package_class数据表的操作方法: updateByPrimaryKey
     * 创建时间 : 2018-06-10 14:12:55
     */
    int updateByPrimaryKey(PackageClass record);
}