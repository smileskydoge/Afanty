package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.ResourceClassMapper;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.domain.FileResources;
import com.booksroo.classroom.common.domain.PackageInfo;
import com.booksroo.classroom.common.domain.ResourceClass;
import com.booksroo.classroom.common.query.ResourceQuery;
import com.booksroo.classroom.common.vo.FileResourceVo;
import com.booksroo.classroom.common.vo.PageList;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizResourceClassService")
public class BizResourceClassService extends BaseService {

    @Autowired
    private ResourceClassMapper resourceClassMapper;
    @Autowired
    private BizFileResourceChildService bizFileResourceChildService;
    @Autowired
    private BizFileResourceService bizFileResourceService;

    //填充子资源
    public PageList<FileResourceVo> getResourceListByClassFullChild(ResourceQuery query) {
        Set<Long> resourceIds = getResourceIds(query);
        if (CollectionUtils.isEmpty(resourceIds)) return PageList.newInstance();

        Map<Long, FileResources> frMap = bizFileResourceService.getMapByIds(resourceIds);
        if (MapUtils.isEmpty(frMap)) return PageList.newInstance();

        List<FileResourceVo> voList = new ArrayList<>();
        for (long id : resourceIds) {
            FileResources fr = frMap.get(id);
            if (fr == null) continue;

            List<FileResourceChild> childList = bizFileResourceChildService.listByResourceId(id);
            FileResourceVo vo = new FileResourceVo(0L, id, fr.getResourceName(), fr.getResourceType().intValue(), childList);
            voList.add(vo);
        }

        return PageList.newInstance(count(query), voList, query.getPage());
    }

    public void delByTCIdAndRid(long teacherClassId, long resourceId) {
//        ResourceClass rc = new ResourceClass();
//        rc.setTeacherClassId(teacherClassId);
//        rc.setResourceId(resourceId);
//        rc.setDelFlag(true);
//        resourceClassMapper.updateByTCIdAndRid(rc);
        resourceClassMapper.deleteByTCIdAndRid(teacherClassId, resourceId);
    }

    public void delByPackageClassId(long packageClassId) {
        resourceClassMapper.deleteByPackageClassId(packageClassId);
    }

    public void delByResourceId(long resourceId) {
        resourceClassMapper.deleteByResourceId(resourceId);
    }

    public Set<Long> getResourceIds(ResourceQuery query) {
        List<ResourceClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Set<Long> ids = new HashSet<>();
        for (ResourceClass rc : list) {
            ids.add(rc.getResourceId());
        }
        return ids;
    }

    public List<ResourceClass> getList(ResourceQuery query) {
        //不按类型查
        if (query.getResourceType() == null || query.getResourceType() <= 0) {
            return resourceClassMapper.select(query);
        }
        return resourceClassMapper.selectByJoin(query);
    }

    public int count(ResourceQuery query) {
        if (query.getResourceType() == null || query.getResourceType() <= 0)
            return resourceClassMapper.count(query);
        return resourceClassMapper.countByJoin(query);
    }

    public ResourceClass getOneByTCidAndRId(long teacherClassId, long resourceId) {
        ResourceQuery query = new ResourceQuery();
        query.setClassId(teacherClassId);
        query.setResourceId(resourceId);
        query.limitOne();
        List<ResourceClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        return list.get(0);
    }

    public void saveOrUpdate(ResourceClass record) {
        ResourceClass one = getOneByTCidAndRId(record.getTeacherClassId(), record.getResourceId());
        if (one != null) return;

        save(record);
    }

    public void save(ResourceClass record) {
        resourceClassMapper.insertSelective(record);
    }

    public void updateByPK(ResourceClass record) {
        resourceClassMapper.updateByPrimaryKeySelective(record);
    }

}
