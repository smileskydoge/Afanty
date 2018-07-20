package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.FileResourceChildMapper;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.query.ResourceQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizFileResourceChildService")
public class BizFileResourceChildService extends BaseService {

    @Autowired
    private FileResourceChildMapper fileResourceChildMapper;

    public List<FileResourceChild> listByResourceId(long resourceId) {
        ResourceQuery query = new ResourceQuery();
        query.setResourceId(resourceId);
        query.noLimit();
        query.setOrderByStr("order_no asc");
        return getList(query);
    }

    public Set<Long> getIdsByRId(long resourceId) {
        List<FileResourceChild> list = listByResourceId(resourceId);
        if (CollectionUtils.isEmpty(list)) return null;

        Set<Long> set = new HashSet<>(list.size());
        for (FileResourceChild o : list) {
            set.add(o.getId());
        }

        return set;
    }

    public List<FileResourceChild> getList(ResourceQuery query) {
        return fileResourceChildMapper.select(query);
    }

    public int count(ResourceQuery query) {
        return fileResourceChildMapper.count(query);
    }

    public void addBatch(List<FileResourceChild> list) {
        if (CollectionUtils.isEmpty(list)) return;
        fileResourceChildMapper.insertBatch(list);
    }

    public void add(FileResourceChild record) {
        fileResourceChildMapper.insertSelective(record);
    }
}
