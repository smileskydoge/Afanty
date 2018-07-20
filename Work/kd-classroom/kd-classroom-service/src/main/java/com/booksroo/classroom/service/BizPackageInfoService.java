package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.PackageInfoMapper;
import com.booksroo.classroom.common.domain.PackageInfo;
import com.booksroo.classroom.common.query.PackageInfoQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/7 10:45
 */
@Service("bizPackageInfoService")
public class BizPackageInfoService extends BaseService {

    @Autowired
    private PackageInfoMapper packageInfoMapper;

    public PackageInfo getOneByTeacherAndName(long teacherClassId, String packageName) {
        PackageInfoQuery query = new PackageInfoQuery();
        query.setTeacherClassId(teacherClassId);
        query.setPackageName(packageName);
        query.limitOne();
        List<PackageInfo> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        return list.get(0);
    }

    public Map<Long, PackageInfo> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        PackageInfoQuery query = new PackageInfoQuery();
        query.setPackageIds(ids);
        query.noLimit();
        List<PackageInfo> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, PackageInfo> map = new HashMap<>();
        for (PackageInfo o : list) {
            map.put(o.getId(), o);
        }
        return map;
    }

    public List<PackageInfo> getListByIds(Set<Long> ids) {
        PackageInfoQuery query = new PackageInfoQuery();
        query.noLimit();
        query.setPackageIds(ids);
        return getList(query);
    }

    public List<PackageInfo> getList(PackageInfoQuery query) {
        return packageInfoMapper.select(query);
    }

    public Long add(long teacherId, long teacherClassId, String packageName) {
        PackageInfo pi = new PackageInfo();
        pi.setTeacherId(teacherId);
        pi.setTeacherClassId(teacherClassId);
        pi.setPackageName(packageName);
        add(pi);
        return pi.getId();
    }

    public int add(PackageInfo record) {
        return packageInfoMapper.insertSelective(record);
    }

    public void updateByPK(PackageInfo record) {
        packageInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int delByPK(long id) {
        return packageInfoMapper.deleteByPrimaryKey(id);
    }

}
