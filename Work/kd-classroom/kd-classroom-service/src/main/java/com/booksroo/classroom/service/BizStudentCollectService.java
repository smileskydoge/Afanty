package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.StudentCollectNotesMapper;
import com.booksroo.classroom.common.domain.StudentCollectNotes;
import com.booksroo.classroom.common.query.StudentCollectQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/14 10:17
 */
@Service("bizStudentCollectService")
public class BizStudentCollectService extends BaseService {

    @Autowired
    private StudentCollectNotesMapper studentCollectNotesMapper;
    @Autowired
    private BizStudentCollectExerciseService bizStudentCollectExerciseService;

    public boolean cancelCollectExercise(long studentId, long quesId){
        return bizStudentCollectExerciseService.cancelCollectExercise(studentId, quesId);
    }

    public boolean collectExercise(long studentId, long quesId, long subjectId, String studentAnswer, Integer answerResult) {
        return bizStudentCollectExerciseService.collectExercise(studentId, null, quesId, subjectId, studentAnswer, answerResult != null ? answerResult.shortValue() : null);
    }

    public boolean isCollectedQues(long studentId, long questionId) {
        return bizStudentCollectExerciseService.isStuCollected(studentId, null, questionId);
    }

    public Map<String, Boolean> getCollectedMap(long studentId, Set<Long> quesIds) {
        return null;
    }

    public List<StudentCollectNotes> getList(StudentCollectQuery query) {
        return studentCollectNotesMapper.select(query);
    }

    public int count(StudentCollectQuery query) {
        return studentCollectNotesMapper.count(query);
    }

    public void add(StudentCollectNotes record) {
        if (isResourceHasCollected(record.getStudentId(), record.getPackageClassId(), record.getResourceId())) return;

        studentCollectNotesMapper.insertSelective(record);
    }

    public boolean isResourceHasCollected(long studentId, long packageClassId, long resourceId) {
        StudentCollectQuery query = new StudentCollectQuery();
        query.setStudentId(studentId);
        query.setPackageClassId(packageClassId);
        query.setResourceId(resourceId);
        return count(query) > 0;
    }

    //查询资源是否被收藏了
    public Map<Long, Boolean> getByStuIdAndRids(long studentId, long packageClassId, Set<Long> rids) {
        if (CollectionUtils.isEmpty(rids)) return null;

        StudentCollectQuery query = new StudentCollectQuery();
        query.setStudentId(studentId);
        query.setPackageClassId(packageClassId);
        query.setResourceIds(rids);
        query.noLimit();
        List<StudentCollectNotes> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, Boolean> map = new HashMap<>();
        for (StudentCollectNotes o : list) {
            if (map.get(o.getResourceId()) != null) continue;
            map.put(o.getResourceId(), true);
        }
        return map;
    }

    public void delCollected(long studentId, long packageClassId, long resourceId, long subjectId) {
        StudentCollectNotes record = new StudentCollectNotes();
        record.setStudentId(studentId);
        record.setPackageClassId(packageClassId);
        record.setResourceId(resourceId);
        record.setSubjectId(subjectId);
        record.setDelFlag(true);
        studentCollectNotesMapper.delCollected(record);
    }
}
