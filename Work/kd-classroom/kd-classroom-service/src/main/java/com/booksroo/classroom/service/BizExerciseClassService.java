package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.ExerciseClassMapper;
import com.booksroo.classroom.common.domain.ExerciseClass;
import com.booksroo.classroom.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("bizExerciseClassService")
public class BizExerciseClassService extends BaseService {

    @Autowired
    private ExerciseClassMapper exerciseClassMapper;

    //根据tcid 查询习题ids
    public Set<Long> getExerciseIdsByTCId(Long teacherClassId){
        List<ExerciseClass> exerciseClasses = exerciseClassMapper.selectByTCId(teacherClassId);
        Set<Long> eids = new HashSet<>();
        for(ExerciseClass ec : exerciseClasses){
            eids.add(ec.getExerciseId());
        }
        return eids;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addExerciseClassMapper(ExerciseClass exerciseClass) throws BizException {
        if(exerciseClassMapper.insertSelective(exerciseClass)<=0) throw new BizException("insert exerciseClass error");
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteExerciseClassMapper(Long exerciseId,Long teacherClassId) throws BizException {
        ExerciseClass exerciseClass = new ExerciseClass();
        exerciseClass.setExerciseId(exerciseId);
        exerciseClass.setTeacherClassId(teacherClassId);
        if(exerciseClassMapper.delECMapper(exerciseClass)<=0)throw new BizException("delete exerciseClass error");
        return true;
    }
}
