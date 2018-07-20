package com.booksroo.classroom.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.PackageResourceNoteMapper;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.domain.PackageResource;
import com.booksroo.classroom.common.domain.PackageResourceNote;
import com.booksroo.classroom.common.query.PackageResourceNoteQuery;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.vo.FileResourceVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/13 23:07
 */
@Service("bizPackageResourceNoteService")
public class BizPackageResourceNoteService extends BaseService {

    @Autowired
    private PackageResourceNoteMapper packageResourceNoteMapper;
    @Autowired
    private BizPackageResourceService bizPackageResourceService;
    @Autowired
    private BizFileResourceChildService bizFileResourceChildService;
    @Autowired
    private BizPackageClassService bizPackageClassService;

    public void handleTeacherAddResourceToPackage(long packageClassId, Set<Long> resourceIds) {
        //如果该资源包已上过课，教师添加资源时，学生也需要看到
        if (packageClassId <= 0 || CollectionUtils.isEmpty(resourceIds) || !bizPackageClassService.isPackageHistory(packageClassId))
            return;

        PackageResourceNote prn = new PackageResourceNote();
        prn.setPackageClassId(packageClassId);

        for (long rid : resourceIds) {
            List<FileResourceChild> childList = bizFileResourceChildService.listByResourceId(rid);
            if (CollectionUtils.isEmpty(childList)) continue;

            for (FileResourceChild c : childList) {
                try {
                    prn.setResourceId(rid);
                    prn.setChildResourceId(c.getId());
                    addNotExist(prn);
                } catch (Exception e) {
                    log.error("添加 PackageResourceNote 失败, param: " + JSON.toJSONString(prn));
                }
            }
        }
    }

    public void copyResourcesByPackage(long packageClassId) {
        List<PackageResource> list = bizPackageResourceService.getListByPCId(packageClassId);
        if (CollectionUtils.isEmpty(list)) return;

        for (PackageResource pr : list) {
            long resourceId = pr.getResourceId();
            List<FileResourceChild> childList = bizFileResourceChildService.listByResourceId(resourceId);
            if (CollectionUtils.isEmpty(childList)) continue;

            for (FileResourceChild frc : childList) {
                PackageResourceNote record = new PackageResourceNote();
                record.setPackageClassId(packageClassId);
                record.setResourceId(resourceId);
                record.setChildResourceId(frc.getId());
                addNotExist(record);
            }
        }
    }

    public Map<Long, PackageResourceNote> getMapByIds(Set<Long> noteIds) {
        if (CollectionUtils.isEmpty(noteIds)) return null;

        List<PackageResourceNote> list = getListByIds(noteIds);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, PackageResourceNote> map = new HashMap<>();
        for (PackageResourceNote o : list) {
            map.put(o.getId(), o);
        }
        return map;
    }

    public List<PackageResourceNote> getListByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.noLimit();
        query.setIds(ids);
        return getList(query);
    }

    public Map<String, String> bindRIdCRIdContentMapByList(List<PackageResourceNote> list) {
        if (CollectionUtils.isEmpty(list)) return null;

        Map<String, String> map = new HashMap<>();

        for (PackageResourceNote o : list) {
            if (StringUtils.isBlank(o.getNoteContent())) continue;
            map.put(o.getPackageClassId() + "_" + o.getResourceId() + "_" + o.getChildResourceId(), o.getNoteContent());
        }

        return map;
    }

    public void replaceToNoteContent(long packageClassId, Set<Long> resourceIds, List<FileResourceVo> voList) {
        if (CollectionUtils.isEmpty(voList)) return;

        Map<String, String> prnMap = getNoteContentMapByPCIdAndRIds(packageClassId, resourceIds);
        BizUtil.replaceResourceToNoteContent(voList, prnMap);
    }

    public Map<String, String> getConMapByPCIdAndRIdForTeacher(long packageClassId, long resourceId) {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.noLimit();
        query.setPackageClassId(packageClassId);
        query.setResourceId(resourceId);
        query.setTeacherShowFlag((short) 1);
        List<PackageResourceNote> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        return bindRIdCRIdContentMapByList(list);
    }

    public int updateTeacherShowFlag(long packageClassId, long resourceId, short flag) {
        return packageResourceNoteMapper.updateTeacherShowFlag(packageClassId, resourceId, flag);
    }

    public Map<String, String> getNoteContentMapByPCIdAndRIds(long packageClassId, Set<Long> resourceIds) {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.noLimit();
        query.setPackageClassId(packageClassId);
        if (CollectionUtils.isNotEmpty(resourceIds))
            query.setResourceIds(resourceIds);
        List<PackageResourceNote> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        return bindRIdCRIdContentMapByList(list);
    }

    public void saveOrUpdate(PackageResourceNote record) {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.setPackageClassId(record.getPackageClassId());
        query.setResourceId(record.getResourceId());
        query.setChildResourceId(record.getChildResourceId());
        query.limitOne();
        List<PackageResourceNote> list = getList(query);
        if (CollectionUtils.isEmpty(list)) {
            packageResourceNoteMapper.insertSelective(record);
            return;
        }

        record.setId(list.get(0).getId());
        record.setTeacherShowFlag((short) 1);
        updateByPK(record);
    }

    public void addNotExist(PackageResourceNote record) {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.setPackageClassId(record.getPackageClassId());
        query.setResourceId(record.getResourceId());
        query.setChildResourceId(record.getChildResourceId());
        if (count(query) > 0) return;

        packageResourceNoteMapper.insertSelective(record);
    }

    public List<PackageResourceNote> getByPCId(long packageClassId) {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.noLimit();
        query.setPackageClassId(packageClassId);
        return getList(query);
    }

    public List<PackageResourceNote> getByPCIdAndRId(long packageClassId, long resourceId) {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.noLimit();
        query.setPackageClassId(packageClassId);
        query.setResourceId(resourceId);
        return getList(query);
    }

    public List<PackageResourceNote> getList(PackageResourceNoteQuery query) {
        if (query.getResourceType() == null || query.getResourceType() <= 0)
            return packageResourceNoteMapper.select(query);
        return packageResourceNoteMapper.selectJoinResource(query);
    }

    public int count(PackageResourceNoteQuery query) {
        if (query.getResourceType() == null || query.getResourceType() <= 0)
            return packageResourceNoteMapper.count(query);

        return packageResourceNoteMapper.countJoinResource(query);
    }

    public int updateByPK(PackageResourceNote record) {
        return packageResourceNoteMapper.updateByPrimaryKeySelective(record);
    }
}
