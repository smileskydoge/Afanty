package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.SubjectMapper;
import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.domain.TeacherClass;
import com.booksroo.classroom.common.enums.SubjectEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.SubjectQuery;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.vo.PageList;
import com.booksroo.classroom.common.vo.SubjectVo;
import com.booksroo.classroom.service.interf.CacheService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizSubjectService")
public class BizSubjectService extends BaseService {

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private BizTeacherClassService bizTeacherClassService;
    @Autowired
    private CacheService cacheService;

    public Set<Long> getOnClassSubjects(Set<String> subjectIds) {
        if (CollectionUtils.isEmpty(subjectIds)) return null;

        Set<String> cacheSet = cacheService.getTeacherInClass();
        if (CollectionUtils.isEmpty(cacheSet)) return null;

        subjectIds.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return !cacheSet.contains(s);
            }
        });

        if (CollectionUtils.isEmpty(subjectIds)) return null;

        Set<Long> ids = new HashSet<>();
        subjectIds.forEach(str -> {
            try {
                String[] arr = str.split("_");
                ids.add(Long.parseLong(arr[1]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

        return ids;
    }

    public List<SubjectVo> getSubjectList(long classId) {
        Map<Long, TeacherClass> tcMap = bizTeacherClassService.getMapByClass(classId);
        if (MapUtils.isEmpty(tcMap)) return null;

        Set<Long> subjectIds = new HashSet<>();
        Set<String> tcSubjects = new HashSet<>();
        for (TeacherClass o : tcMap.values()) {
            try {
                String subIdStr = o.getSubjectId();
                if (StringUtils.isBlank(subIdStr)) continue;

                if (!subIdStr.contains(",")) {
                    long id = Long.parseLong(subIdStr);
                    subjectIds.add(id);
                    tcSubjects.add(o.getId() + "_" + subIdStr);
                    continue;
                }

                Set<Long> subIds = BizUtil.strToLongs(subIdStr);
                if (CollectionUtils.isEmpty(subIds)) continue;

                for (long subId : subIds) {
                    tcSubjects.add(o.getId() + "_" + subId);
                }
                subjectIds.addAll(subIds);
            } catch (Exception e) {
                log.error("获取学生学科列表出错: " + e.getMessage(), e);
            }
        }

        Set<Long> onClassSubjects = getOnClassSubjects(tcSubjects);
        boolean hasOnCS = CollectionUtils.isNotEmpty(onClassSubjects);

        List<Subject> list = getSubjectListByIds(subjectIds);
        if (CollectionUtils.isEmpty(list)) return null;

        List<SubjectVo> voList = new ArrayList<>();
        for (Subject o : list) {
            SubjectVo vo = new SubjectVo(o.getId(), o.getSubjectName());
            vo.setCoverImg(o.getCoverImg());
            for (TeacherClass tc : tcMap.values()) {
                if (!tc.getSubjectId().contains("" + o.getId())) continue;
                vo.setTeacherClassId(tc.getId());
                vo.setTeacherId(tc.getTeacherId());
            }

            SubjectEnum se = SubjectEnum.getByName(vo.getSubjectName());
            if (se != null) {
                vo.setSubjectNamePinyin(se.getSubjectPinyin());
                vo.setOrderNo(se.getOrderNo());
            }
            voList.add(vo);

            if (!hasOnCS) continue;
            vo.setOnClass(onClassSubjects.contains(vo.getSubjectId()));
        }
        return voList;
    }

    public List<Subject> getSubjectListByClass(long classId) {
        Set<Long> sids = bizTeacherClassService.getSubjectIdsByClass(classId);
        return getSubjectListByIds(sids);
    }

    public List<Subject> getSubjectListByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        SubjectQuery query = new SubjectQuery();
        query.setSubjectIds(ids);
        query.noLimit();
        return getList(query);
    }

    public Map<Long, Subject> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        SubjectQuery query = new SubjectQuery();
        query.setSubjectIds(ids);
        query.noLimit();
        List<Subject> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, Subject> map = new HashMap<>();
        for (Subject o : list) {
            map.put(o.getId(), o);
        }
        return map;
    }

    public PageList<SubjectVo> getSubjectInfo(SubjectQuery subjectQuery) throws Exception {

        long count = count(subjectQuery);
        if (count <= 0) return PageList.newInstance();

        List<Subject> list = getList(subjectQuery);
        List<SubjectVo> voList = new ArrayList<>();
        for (Subject s : list) {
            SubjectVo subjectVo = new SubjectVo();
            BeanUtilsExt.copyProperties(subjectVo, s);
            voList.add(subjectVo);
        }
        return new PageList<>(count, voList);
    }

    public long count(SubjectQuery subjectQuery) {
        return subjectMapper.count(subjectQuery);
    }

    public List<Subject> getList(SubjectQuery query) {
        return subjectMapper.querySubjectListInfo(query);
    }

    //获取所有科目信息to Map
    public Map<Long, String> getAllSubjectInfo() {
        List<Subject> subjects = subjectMapper.queryAllSubjectInfo();
        if (subjects.size() <= 0) return new HashMap<>();
        Map<Long, String> subjectMap = new HashMap<>();
        for (Subject s : subjects) {
            subjectMap.put(s.getId(), s.getSubjectName());
        }
        return subjectMap;
    }

    //获取所有科目信息to List
    public List<Subject> getAllSubjectList() {
        return subjectMapper.queryAllSubjectInfo();
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addSubjectInfo(Subject subject) throws Exception {
        if (subject == null) throw new BizException("增加失败");
        if (subject.getSubjectName() == null || "".equals(subject.getSubjectName())) {
            throw new BizException("科目名输入有误");
        }
        subjectMapper.insertSelective(subject);
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteSubjectInfo(Long id) throws Exception {
        if (subjectMapper.deleteByPrimaryKey(id) <= 0) return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateSubjectInfo(Subject subject) throws Exception {
        if (subjectMapper.updateByPrimaryKeySelective(subject) <= 0) return false;
        return true;
    }

    public Subject getById(Long id) throws Exception {
        return subjectMapper.selectByPrimaryKey(id);
    }
}
