package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.TeacherExerciseMapper;
import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.TeacherExercise;
import com.booksroo.classroom.common.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("bizTeacherExerciseService")
public class BizTeacherExerciseService extends BaseService {

    @Autowired
    private TeacherExerciseMapper teacherExerciseMapper;


    public Set<Long> getExcerciseIdsByTeacherId(BaseQuery query){
        List<TeacherExercise> tes =teacherExerciseMapper.getExcerciseInfoByTeacherId(query);
        Set<Long> ExerciseIds = new HashSet<>();
        for(TeacherExercise te:tes){
            ExerciseIds.add(te.getExerciseId());
        }
        return ExerciseIds;
    }


    public boolean delTeacherExerciseByEId(ExerciseDomain exerciseDomain){
        Long eid = exerciseDomain.getId();
        Long tid = exerciseDomain.getTeacherId();
        TeacherExercise teacherExercise = new TeacherExercise();
        teacherExercise.setTeacherId(tid);
        teacherExercise.setExerciseId(eid);

        if(teacherExerciseMapper.delEByEIdAndTid(teacherExercise)<=0) return false;
        return true;
    }

}
