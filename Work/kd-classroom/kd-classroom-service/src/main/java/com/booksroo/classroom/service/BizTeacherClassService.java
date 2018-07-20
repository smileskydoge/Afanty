package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.TeacherClassMapper;
import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.domain.TeacherClass;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.ClassDomainQuery;
import com.booksroo.classroom.common.util.BizUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizTeacherClassService")
public class BizTeacherClassService extends BaseService {
    @Autowired
    private TeacherClassMapper teacherClassMapper;

    public TeacherClass getByPK(long id) {
        return teacherClassMapper.selectByPrimaryKey(id);
    }

    public Set<Long> getClassIdsByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        ClassDomainQuery query = new ClassDomainQuery();
        query.noLimit();
        query.setIds(ids);
        List<TeacherClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return new HashSet<>();

        Set<Long> set = new HashSet<>();
        for (TeacherClass o : list) {
            set.add(o.getClassId());
        }

        return set;
    }

    public Set<Long> getSubjectIdsByClass(long classId) {
        ClassDomainQuery query = new ClassDomainQuery();
        query.setClassId(classId);
        query.noLimit();
        List<TeacherClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        StringBuilder sb = new StringBuilder();
        for (TeacherClass o : list) {
            sb.append(o.getSubjectId()).append(",");
        }
        return BizUtil.strToLongs(sb.toString());
    }

    public Map<Long, TeacherClass> getMapByClass(long classId) {
        ClassDomainQuery query = new ClassDomainQuery();
        query.setClassId(classId);
        query.noLimit();
        List<TeacherClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, TeacherClass> map = new HashMap<>();
        for (TeacherClass o : list) {
            map.put(o.getId(), o);
        }
        return map;
    }

    public List<TeacherClass> getList(ClassDomainQuery query) {
        return teacherClassMapper.select(query);
    }

    public TeacherClass getTCByCidAndSid(long classId, String subjectId) {
        ClassDomainQuery query = new ClassDomainQuery();
        query.setClassId(classId);
        query.setSubjectId(subjectId);
        query.limitOne();
        List<TeacherClass> list = getList(query);

        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;

    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addTeacherClassInfo(List<TeacherClass> teacherClasses) throws Exception{
        for(TeacherClass tc: teacherClasses){
            if(teacherClassMapper.insertSelective(tc)<= 0)throw new BizException("教师班级信息插入失败");
        }
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateTeacherClassInfo(TeacherClass teacherClass) {
        if (teacherClassMapper.updateTeacherClassInfo(teacherClass) <= 0) return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteOriTeacherClassInfo(Long teacherId){
        if(teacherClassMapper.deleteOriMappingInfo(teacherId)<0)return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteOriByRetain(Long teacherId,Set<Long> RetainIds){
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setId(teacherId);
        baseQuery.setIds(RetainIds);
        if(teacherClassMapper.deleteOriRetainInfo(baseQuery)<0)return false;
        return true;
    }

    public Set<Long> queryTeacherIdsByClass(Set<Long> classIds){

        List<TeacherClass> teacherClasses = teacherClassMapper.queryTeacherIdsByClass(classIds);
        if(teacherClasses==null||teacherClasses.size()<=0){
            return null;
        }
        Set<Long> teacherIds = new HashSet<>();
        for(TeacherClass tc:teacherClasses){
            teacherIds.add(tc.getTeacherId());
        }
        return teacherIds;
    }

    //查询老师所带班级Id
    public Set<Long> queryClassIdsByTeacherId(Long teacherId){
        return teacherClassMapper.queryClassIdsByTeacherId(teacherId);
    }

    public Long queryIdByclassIdAndTeacherId(Long classId,Long teacherId){
        TeacherClass teacherClass = new TeacherClass();
        teacherClass.setTeacherId(teacherId);
        teacherClass.setClassId(classId);
        return teacherClassMapper.queryIdByCidAndTid(teacherClass);
    }
}
