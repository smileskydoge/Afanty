package com.booksroo.classroom.service;

import com.booksroo.classroom.common.constant.PromptConstant;
import com.booksroo.classroom.common.dao.FileResourcesMapper;
import com.booksroo.classroom.common.domain.FileResources;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.ResourceQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizFileResourceService")
public class BizFileResourceService extends BaseService {

    @Autowired
    private FileResourcesMapper fileResourcesMapper;
    @Autowired
    private BizTeacherResourceService bizTeacherResourceService;

    public Map<Long, FileResources> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        ResourceQuery query = new ResourceQuery();
        query.setResourceIds(ids);
        List<FileResources> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, FileResources> map = new HashMap<>();
        for (FileResources f : list) {
            map.put(f.getId(), f);
        }

        return map;
    }

    public List<FileResources> getListByIds(Set<Long> ids) {
        return getListByIds(ids, null);
    }

    public List<FileResources> getListByIds(Set<Long> ids, String orderBy) {
        if (CollectionUtils.isEmpty(ids)) return null;

        ResourceQuery query = new ResourceQuery();
        query.setResourceIds(ids);
        query.noLimit();
        if (StringUtils.isNotBlank(orderBy)) query.setOrderByStr(orderBy);
        return getList(query);
    }

    public Map<Long, Integer> getResourceTypeByIds(Set<Long> ids) {
        List<FileResources> list = getListByIds(ids);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, Integer> map = new HashMap<>();
        for (FileResources o : list) {
            map.put(o.getId(), o.getResourceType().intValue());
        }

        return map;
    }

    public List<FileResources> getList(ResourceQuery query) {
        return fileResourcesMapper.select(query);
    }

    public List<FileResources> getListByTIdAndName(long teacherId, String resourceName) {
        ResourceQuery query = new ResourceQuery();
        query.setTeacherId(teacherId);
        query.setResourceName(resourceName);
        return getList(query);
    }

    public int count(ResourceQuery query) {
        return fileResourcesMapper.count(query);
    }

    public boolean isResourceNameExist(long teacherId, String name) {
        if (StringUtils.isBlank(name)) return false;

        ResourceQuery query = new ResourceQuery();
        query.setResourceName(name);
        query.setTeacherId(teacherId);
        return count(query) > 0;
    }

    public void add(FileResources fileResources) {
        fileResourcesMapper.insertSelective(fileResources);
    }

    public void updateResourceName(long teacherId, long resourceId, String resourceName) throws Exception {
        if (isResourceNameExist(teacherId, resourceName))
            throw new BizException(PromptConstant.FILE_NAME_ALREADY_EXIST, "文件名已存在，请重新命名");

        FileResources record = new FileResources();
        record.setId(resourceId);
        record.setResourceName(resourceName);
        updateByPK(record);
    }

    public void updateByPK(FileResources record) {
        fileResourcesMapper.updateByPrimaryKeySelective(record);
    }

    public void delete(long id) {
        FileResources record = new FileResources();
        record.setId(id);
        record.setDelFlag(true);
        fileResourcesMapper.updateByPrimaryKeySelective(record);
    }
}
