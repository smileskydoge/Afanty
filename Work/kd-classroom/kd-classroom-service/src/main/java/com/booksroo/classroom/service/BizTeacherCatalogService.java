package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.TeacherCatalogMapper;
import com.booksroo.classroom.common.domain.TeacherCatalog;
import com.booksroo.classroom.common.query.TeacherCatalogQuery;
import com.booksroo.classroom.service.interf.CacheService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizTeacherCatalogService")
public class BizTeacherCatalogService extends BaseService {

    @Autowired
    private TeacherCatalogMapper teacherCatalogMapper;
    @Autowired
    private CacheService redisCacheService;

    public boolean hasDefaultCatalog(long teacherId) {
        TeacherCatalogQuery query = new TeacherCatalogQuery();
        query.setTeacherId(teacherId);
        query.setCatalogName("default");
        return count(query) > 0;
    }

    public Long addDefaultCatalog(long teacherId) {
        //添加默认的类目
        TeacherCatalog tc = new TeacherCatalog();
        tc.setTeacherId(teacherId);
        tc.setCatalogName("default");
        add(tc);
        Long id = tc.getId();
        if (id != null) return id;

        return getDefCatalogId(teacherId, false);

    }

    public Long getDefCatalogId(long teacherId, boolean cacheFirst) {
        Long id = null;
        if (cacheFirst) {
            id = redisCacheService.getTeacherDefCatalogId(teacherId);
            if (id != null && id > 0) return id;
        }

        TeacherCatalogQuery query = new TeacherCatalogQuery();
        query.setTeacherId(teacherId);
        query.setCatalogName("default");
        query.setPage(1);
        query.setLimit(1);
        List<TeacherCatalog> list = getList(query);
        if (CollectionUtils.isNotEmpty(list)) id = list.get(0).getId();

        if (id != null && id > 0) {
            redisCacheService.setTeacherDefCatalogId(teacherId, id);
        }
        return id;
    }

    public List<TeacherCatalog> getList(TeacherCatalogQuery query) {
        return teacherCatalogMapper.select(query);
    }

    public int count(TeacherCatalogQuery query) {
        return teacherCatalogMapper.count(query);
    }

    public void add(TeacherCatalog tc) {
        teacherCatalogMapper.insertSelective(tc);
    }
}
