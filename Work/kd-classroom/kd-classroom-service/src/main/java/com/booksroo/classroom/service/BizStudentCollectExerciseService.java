package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.StudentCollectExerciseMapper;
import com.booksroo.classroom.common.domain.StudentCollectExercise;
import com.booksroo.classroom.common.query.StudentCollectExercisesQuery;
import com.booksroo.classroom.common.vo.PageList;
import com.booksroo.classroom.common.vo.StudentCollectExerciseVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("bizStudentCollectExerciseService")
public class BizStudentCollectExerciseService extends BaseService {

    @Autowired
    private StudentCollectExerciseMapper studentCollectExerciseMapper;

    //学生是否收藏习题
    public Boolean isStuCollected(long studentId, Long packageClassId, long quesId) {
        StudentCollectExercisesQuery qry = new StudentCollectExercisesQuery();
        qry.setStudentId(studentId);
        qry.setPackageClassId(packageClassId);
        qry.setQuestionId(quesId);
        return count(qry) > 0;
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean collectExercise(Long studentId, Long packageClassId, Long exerciseId, Long subjectId, String studentAnswer, Short answerResult) {
        if (isStuCollected(studentId, packageClassId, exerciseId)) return true;
        StudentCollectExercise studentCollectExercise = new StudentCollectExercise();
        studentCollectExercise.setStudentId(studentId);
        studentCollectExercise.setExerciseId(exerciseId);
        studentCollectExercise.setSubjectId(subjectId);
        studentCollectExercise.setPackageClassId(packageClassId);
        studentCollectExercise.setStudentAnswer(studentAnswer);
        studentCollectExercise.setAnswerResult(answerResult);
        return studentCollectExerciseMapper.insertSelective(studentCollectExercise) > 0;
    }


    public boolean cancelCollectExercise(Long studentId, Long exerciseId) {
        StudentCollectExercise studentCollectExercise = new StudentCollectExercise();
        studentCollectExercise.setStudentId(studentId);
        studentCollectExercise.setExerciseId(exerciseId);
        return studentCollectExerciseMapper.delCollect(studentCollectExercise) > 0;
    }

    public List<StudentCollectExercise> selectList(StudentCollectExercisesQuery qry) {
        return studentCollectExerciseMapper.select(qry);
    }

    public int count(StudentCollectExercisesQuery qry) {
        return studentCollectExerciseMapper.count(qry);
    }
}
