package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.PackageClassMapper;
import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.domain.PackageClass;
import com.booksroo.classroom.common.domain.TeacherClass;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.PackageInfoQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/10 14:29
 */
@Service("bizPackageClassService")
public class BizPackageClassService extends BaseService {

    @Autowired
    private PackageClassMapper packageClassMapper;
    @Autowired
    private BizTeacherClassService bizTeacherClassService;
    @Autowired
    private BizClassService bizClassService;
    @Autowired
    private BizStudentService bizStudentService;

    public Map<Long, Boolean> add(long classId, long packageId, boolean needCheckUnique) {

        Map<Long, Boolean> map = new HashMap<>();
        if (needCheckUnique) {
            PackageClass pcDB = getByCidAndPid(classId, packageId);
            if (pcDB != null) {
                map.put(pcDB.getId(), true);
                return map;
            }
        }

        PackageClass record = new PackageClass();
        record.setTeacherClassId(classId);
        record.setPackageId(packageId);
        packageClassMapper.insertSelective(record);
        map.put(record.getId(), false);
        return map;
    }

    public void del(long classId, long packageId) {
//        PackageClass record = new PackageClass();
//        record.setTeacherClassId(classId);
//        record.setPackageId(packageId);
//        record.setDelFlag(true);
//        packageClassMapper.updateByTCIdAndPid(record);

        packageClassMapper.deleteByTCIdAndPid(classId, packageId);
    }

    public boolean isPackageInClass(long packageId, long classId) {
        PackageInfoQuery query = new PackageInfoQuery();
        query.setClassId(classId);
        query.setPackageId(packageId);
        return count(query) > 0;
    }

    public PackageClass getByCidAndPid(long classId, long packageId) {
        PackageInfoQuery query = new PackageInfoQuery();
        query.setClassId(classId);
        query.setPackageId(packageId);
        query.limitOne();
        List<PackageClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        return list.get(0);
    }

    public Set<Long> getPackageClassIdsByCid(long teacherClassId) {
        PackageInfoQuery query = new PackageInfoQuery();
        query.setClassId(teacherClassId);
        query.noLimit();
        List<PackageClass> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Set<Long> set = new HashSet<>();
        for (PackageClass o : list) {
            set.add(o.getId());
        }

        return set;
    }

    public List<PackageClass> getList(PackageInfoQuery query) {
        return packageClassMapper.select(query);
    }

    public void updatePackageIdByTCIdAndPid(long teacherClassId, long oldPackageId, long newPackageId) {
        packageClassMapper.updatePackageIdByTCIdAndPid(teacherClassId, oldPackageId, newPackageId);
    }

    public int count(PackageInfoQuery query) {
        return packageClassMapper.count(query);
    }

    public List<PackageClass> getHistoryList(long classId, String subjectId, int start, int size) {
        return packageClassMapper.getHistoryList(classId, subjectId, start, size);
    }

    public int countHistory(long classId, String subjectId) {
        return packageClassMapper.countHistory(classId, subjectId);
    }

    public int updateByPK(PackageClass record) {
        return packageClassMapper.updateByPrimaryKeySelective(record);
    }

    public PackageClass getById(long id) {
        return packageClassMapper.selectByPrimaryKey(id);
    }

    //包是否已上过课
    public boolean isPackageHistory(long packageClassId) {
        PackageClass pc = getById(packageClassId);
        return pc != null && pc.getStatus() == 1;
    }


    //检查package class 对应关系是否存在
    public Long checkPCMapper(PackageClass packageClass) {
        return packageClassMapper.selectPCIdByPIdAndCId(packageClass);
    }

    //add package_class
    public Integer addPackageClassMapper(PackageClass packageClass) {
        return packageClassMapper.addPackageClassMapper(packageClass);
    }

    // query pcId by teacherId + classId + packageId
    public Long queryPCIdByTCPId(Long teacherId, Long classId, Long packageId) throws BizException {
        Long tcId = bizTeacherClassService.queryIdByclassIdAndTeacherId(classId, teacherId);
        if (tcId == null) throw new BizException("tcId is null");
        PackageClass packageClass = new PackageClass();
        packageClass.setPackageId(packageId);
        packageClass.setTeacherClassId(tcId);
        Long pcId = packageClassMapper.selectPCIdByPIdAndCId(packageClass);
        if (pcId == null) throw new BizException("pcId is null");
        return pcId;
    }

    //根据packageClassId获取班级信息
    public ClassDomain getClassByPCId(long packageClassId) {
        PackageClass pc = getById(packageClassId);
        if (pc == null) return null;
        TeacherClass tc = bizTeacherClassService.getByPK(pc.getTeacherClassId());
        if (tc == null) return null;
        return bizClassService.getById(tc.getClassId());
    }

    //根据packageClassId获取班级下的全部学生姓名
    public List<String> getClassStuNameByPCId(long packageClassId) {
        PackageClass pc = getById(packageClassId);
        if (pc == null) return null;
        TeacherClass tc = bizTeacherClassService.getByPK(pc.getTeacherClassId());
        if (tc == null || tc.getClassId() == null) return null;
        return bizStudentService.getStuNameByClass(tc.getClassId());
    }

    public Long getTCIdByPCId(Long pcId) throws BizException {
        PackageClass packageClass = packageClassMapper.selectByPrimaryKey(pcId);
        if(packageClass.getTeacherClassId()==null)throw new BizException("getTCIdByPCId is error");
        return packageClass.getTeacherClassId();
    }

}
