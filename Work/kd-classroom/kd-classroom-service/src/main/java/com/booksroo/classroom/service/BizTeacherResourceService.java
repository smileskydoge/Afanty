package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.TeacherResourceMapper;
import com.booksroo.classroom.common.domain.TeacherResource;
import com.booksroo.classroom.common.query.ResourceQuery;
import com.booksroo.classroom.common.query.TeacherResourceQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/6 19:23
 */
@Service("bizTeacherResourceService")
public class BizTeacherResourceService extends BaseService {

    @Autowired
    private TeacherResourceMapper teacherResourceMapper;

    public Set<Long> getResourceIds(TeacherResourceQuery query) {
        List<TeacherResource> list = select(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Set<Long> ids = new HashSet<>();
        for (TeacherResource o : list) {
            ids.add(o.getResourceId());
        }
        return ids;
    }

    public int deleteByUK(long teacherId, long resourceId) {
        return teacherResourceMapper.delete(teacherId, resourceId);
    }

    public void addNotExist(TeacherResource record) {
        if (isExist(record.getTeacherId(), record.getResourceId())) return;
        add(record);
    }

    public void add(TeacherResource record) {
        teacherResourceMapper.insertSelective(record);
    }

    public boolean isExist(long teacherId, long resourceId) {
        TeacherResourceQuery query = new TeacherResourceQuery();
        query.setTeacherId(teacherId);
        query.setResourceId(resourceId);
        return count(query) > 0;
    }

    public int count(TeacherResourceQuery query) {
        if (query.getResourceType() != null && query.getResourceType() > 0) {
            return teacherResourceMapper.countJoinResource(query);
        }
        return teacherResourceMapper.count(query);
    }

    public List<TeacherResource> select(TeacherResourceQuery query) {
        if (query.getResourceType() != null && query.getResourceType() > 0) {
            return teacherResourceMapper.selectJoinResource(query);
        }
        return teacherResourceMapper.select(query);
    }
}
