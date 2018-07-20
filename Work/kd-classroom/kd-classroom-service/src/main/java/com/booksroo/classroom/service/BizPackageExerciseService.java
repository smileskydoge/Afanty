package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.PackageExerciseMapper;
import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.PackageClass;
import com.booksroo.classroom.common.domain.PackageExercise;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.vo.ExerciseVo;
import com.booksroo.classroom.common.vo.StudentCollectExerciseVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("bizPackageExerciseService")
public class BizPackageExerciseService extends BaseService {

    @Autowired
    private PackageExerciseMapper packageExerciseMapper;
    @Autowired
    private BizPackageClassService bizPackageClassService;
    @Autowired
    private BizExerciseService bizExerciseService;

    //建立包与习题关联关系
    @Transactional(rollbackFor = Throwable.class)
    public int insertPackageExercise(Long packageClassId, Long exerciseId) {
        PackageExercise packageExercise = new PackageExercise();
        packageExercise.setPackageClassId(packageClassId);
        packageExercise.setExerciseId(exerciseId);
        return packageExerciseMapper.insertSelective(packageExercise);
    }

    public Set<Long> getExerciseIdsByPackageId(Long packageId, Long teacherClassId) throws BizException {

        PackageClass packageClass = new PackageClass();
        packageClass.setPackageId(packageId);
        packageClass.setTeacherClassId(teacherClassId);
        Long PCId = bizPackageClassService.checkPCMapper(packageClass);
        if (PCId == null) throw new BizException("PCId is null");

        return getExerciseIdsByPCId(PCId);
    }

    //获取包下的习题id
    public Set<Long> getExerciseIdsByPCId(long pcId) {
        List<PackageExercise> list = packageExerciseMapper.getExercisesByPCId(pcId);
        if (CollectionUtils.isEmpty(list)) return null;
        Set<Long> ids = new HashSet<>();
        list.forEach(e -> {
            ids.add(e.getExerciseId());
        });
        return ids;
    }

    public Boolean deletePEMapper(Long exerciseId, Long pcId) {
        PackageExercise packageExercise = new PackageExercise();
        packageExercise.setPackageClassId(pcId);
        packageExercise.setExerciseId(exerciseId);
        if (packageExerciseMapper.delPEMapper(packageExercise) <= 0) return false;
        return true;
    }

    //查询习题在同一个资源包里是否重复
    public Boolean checkExerciseRepe(long exerciseId,long pcId){
        PackageExercise packageExercise = new PackageExercise();
        packageExercise.setExerciseId(exerciseId);
        packageExercise.setPackageClassId(pcId);
        if(packageExerciseMapper.countPENumber(packageExercise)>=1)return true;
        return false;
    }
}
