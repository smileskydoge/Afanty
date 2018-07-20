package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.PackageResourceMapper;
import com.booksroo.classroom.common.domain.PackageResource;
import com.booksroo.classroom.common.enums.FileEnum;
import com.booksroo.classroom.common.query.PackageResourceQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/10 14:29
 */
@Service("bizPackageResourceService")
public class BizPackageResourceService extends BaseService {

    @Autowired
    private PackageResourceMapper packageResourceMapper;


    public Set<Integer> getPackageResourceType(long packageClassId) {
        List<Integer> list = packageResourceMapper.selectPackageResourceType(packageClassId);
        if (CollectionUtils.isEmpty(list)) return null;

        return new HashSet<>(list);
    }

    public boolean isPPTExist(long packageClassId) {
        return isResourceTypeExist(packageClassId, FileEnum.PPT.getType());
    }

    public boolean isResourceTypeExist(long packageClassId, int type) {
        Set<Integer> set = getPackageResourceType(packageClassId);
        if (CollectionUtils.isEmpty(set)) return false;
        return set.contains(type);
    }

    public void addNotExist(long packageClassId, long resourceId) {

        if (isResourceExist(packageClassId, resourceId)) return;

        PackageResource record = new PackageResource();
        record.setPackageClassId(packageClassId);
        record.setResourceId(resourceId);
        packageResourceMapper.insertSelective(record);
    }

    public int add(long packageClassId, long resourceId) {
        PackageResource record = new PackageResource();
        record.setPackageClassId(packageClassId);
        record.setResourceId(resourceId);
        return packageResourceMapper.insertSelective(record);
    }

    public boolean isResourceExist(long packageClassId, long resourceId) {
        PackageResourceQuery query = new PackageResourceQuery();
        query.setPackageClassId(packageClassId);
        query.setResourceId(resourceId);
        return count(query) > 0;
    }

    public Map<Long, Set<Long>> getResourcePackageMapByRids(Set<Long> rids) {
        if (CollectionUtils.isEmpty(rids)) return new HashMap<>();

        PackageResourceQuery query = new PackageResourceQuery();
        query.noLimit();
        query.setResourceIds(rids);

        List<PackageResource> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return new HashMap<>();

        Map<Long, Set<Long>> map = new HashMap<>();
        for (PackageResource o : list) {
            Set<Long> pcIds = map.get(o.getResourceId());
            if (pcIds == null) {
                pcIds = new HashSet<>();
            }
            pcIds.add(o.getPackageClassId());
            map.put(o.getResourceId(), pcIds);
        }
        return map;
    }

    public List<PackageResource> getListByPCId(long packageClassId) {

        PackageResourceQuery query = new PackageResourceQuery();
        query.setPackageClassId(packageClassId);
        query.noLimit();
        return getList(query);
    }

    public List<PackageResource> getList(PackageResourceQuery query) {
        if (query.getResourceType() == null || query.getResourceType() <= 0)
            return packageResourceMapper.select(query);
        return packageResourceMapper.selectJoinResource(query);
    }

    public int count(PackageResourceQuery query) {
        if (query.getResourceType() == null || query.getResourceType() <= 0)
            return packageResourceMapper.count(query);
        return packageResourceMapper.countJoinResource(query);
    }

    public void delByPCidAndRid(long packageClassId, Long resourceId) {
//        PackageResource record = new PackageResource();
//        record.setPackageClassId(packageClassId);
//        record.setResourceId(resourceId);
//        record.setDelFlag(true);
//        packageResourceMapper.updateByPackageClassId(record);
        packageResourceMapper.deleteByPCIdAndRId(packageClassId, resourceId);
    }

    public void delByPackageClassId(long packageClassId) {
//        PackageResource record = new PackageResource();
//        record.setPackageClassId(packageClassId);
//        record.setDelFlag(true);
//        packageResourceMapper.updateByPackageClassId(record);
        delByPCidAndRid(packageClassId, null);
    }

    public int updateCurrentNo(long packageClassId, Long resourceId, Integer currentNo) {
        if (resourceId == null || resourceId <= 0 || currentNo == null || currentNo < 0) return 0;

        PackageResource record = new PackageResource();
        record.setPackageClassId(packageClassId);
        record.setResourceId(resourceId);
        record.setCurrentNo(currentNo);

        return packageResourceMapper.updateByPackageClassId(record);
    }

    public void delByResourceId(long resourceId) {
        packageResourceMapper.deleteByResourceId(resourceId);
    }

    public Set<Long> getPCIdsByResourceId(long resourceId) {
        PackageResourceQuery query = new PackageResourceQuery();
        query.setResourceId(resourceId);
        query.setLimit(100000);
        List<PackageResource> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Set<Long> set = new HashSet<>(list.size());
        for (PackageResource o : list) {
            set.add(o.getPackageClassId());
        }

        return set;
    }
}
