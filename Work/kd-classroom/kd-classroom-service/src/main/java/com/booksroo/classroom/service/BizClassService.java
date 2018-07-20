package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.ClassMapper;
import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.query.ClassDomainQuery;
import com.booksroo.classroom.common.query.ClassQuery;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.vo.ClassVo;
import com.booksroo.classroom.common.vo.PageList;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 实际的业务类
 *
 * @author liujianjian
 * @date 2018/6/1 15:30
 */
@Service("bizClassService")
public class BizClassService extends BaseService {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private BizSchoolService bizSchoolService;

    @Autowired
    private BizStudentService bizStudentService;

    public List<ClassDomain> getList(ClassDomainQuery query) {
        return classMapper.select(query);
    }

    public ClassDomain getById(long id) {
        return classMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void addClass(ClassDomain domain) {
        classMapper.insertSelective(domain);
    }

    public Map<Long, ClassDomain> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        ClassDomainQuery query = new ClassDomainQuery();
        query.setPage(1);
        query.setLimit(ids.size());
        query.setClassIds(ids);
        List<ClassDomain> list = classMapper.select(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, ClassDomain> map = new HashMap<>();
        for (ClassDomain c : list) {
            map.put(c.getId(), c);
        }

        return map;
    }

    public PageList<ClassVo> queryClassPageList(ClassQuery classQuery) throws Exception{
        long count = count(classQuery);
        if (count <= 0) return PageList.newInstance();

        List<ClassDomain> list = classMapper.queryClassList(classQuery);
        List<ClassVo> voList = new ArrayList<>();
        Set<Long> schoolIds = new HashSet<>();

        for(ClassDomain s:list){
            schoolIds.add(s.getSchoolId());
            ClassVo classVo = new ClassVo();
            BeanUtilsExt.copyProperties(classVo,s);
            voList.add(classVo);
        }

        Map<Long, School> schoolMap = bizSchoolService.getMapByIds(schoolIds);

        for(ClassVo cv : voList){
            School school = schoolMap.get(cv.getSchoolId());
            cv.setSchoolName(school!=null?school.getSchoolName():"");
//            cv.setStudentNum(bizStudentService.queryStudentNumByClassId(cv.getId()));
        }
        return new PageList<>(count,voList);
    }

    private long count(ClassQuery classQuery) {
        return classMapper.count(classQuery);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addClassService(ClassDomain classDomain) throws Exception{
        if (classMapper.insertSelective(classDomain)<=0)return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteClassService(Long id) throws Exception{
        if (classMapper.deleteByPrimaryKey(id)<=0)return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateClassService(ClassDomain classDomain) throws Exception {
        if(classMapper.updateByPrimaryKeySelective(classDomain)<=0)return false;
        return true;
    }

    public List<ClassDomain> getClassInfoBySchoolId(Long schoolId){
        if(schoolId==null) return new ArrayList<>();
        return classMapper.queryClassBySchool(schoolId);
    }

    public List<ClassDomain> getClassInfoByTeacherId(Long teacherId){
        return classMapper.queryTeacherClassInfoByTeacherId(teacherId);
    }

    public ClassDomain getClassInfoById(Long classId){
        return classMapper.selectByPrimaryKey(classId);
    }

    public Boolean addStudentNum(Long classId){
        if(classMapper.addStudentNum(classId)<=0) return false;
        return true;
    }

    public Boolean SubstractStudentNum(Long classId){
        if(classMapper.subtractStudentNum(classId)<=0) return false;
        return true;
    }

}
