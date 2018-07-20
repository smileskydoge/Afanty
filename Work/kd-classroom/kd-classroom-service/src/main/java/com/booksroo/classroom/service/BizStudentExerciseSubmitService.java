package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.StudentExerciseSubmitMapper;
import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.enums.QuestionEnum;
import com.booksroo.classroom.common.enums.StudentExercisesEnum;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.vo.StatisticsDetailVo;
import com.booksroo.classroom.common.vo.StatisticsVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Service("bizStudentExerciseSubmitService")
public class BizStudentExerciseSubmitService extends BaseService {

    @Autowired
    private StudentExerciseSubmitMapper studentExerciseSubmitMapper;
    @Autowired
    private BizStatisticsService bizStatisticsService;
    @Autowired
    private BizUnitTestExerciseService bizUnitTestExerciseService;
    @Autowired
    private BizPackageClassService bizPackageClassService;
    @Autowired
    private BizStudentService bizStudentService;

    public Map<String, StudentExerciseSubmit> getMapByStuByStuUnitQuesIds(long studentId, long unitTestId, Set<Long> quesIds) {
        if (CollectionUtils.isEmpty(quesIds)) return null;
        Map<String, Long> map = bizUnitTestExerciseService.getMapIdByUTIdAndEIds(unitTestId, quesIds);
        if (MapUtils.isEmpty(map)) return null;

        Map<String, StudentExerciseSubmit> result = new HashMap<>();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            StudentExerciseSubmit submit = getByStuAndUTEId(studentId, entry.getValue());
            if (submit == null) continue;
            result.put(studentId + "_" + entry.getKey(), submit);
        }
        return result;
    }

    public StudentExerciseSubmit getByStuAndUnitTestAndQues(long studentId, long unitTestId, long quesId) {
        UnitTestExercises ute = bizUnitTestExerciseService.getByUTIdAndEId(unitTestId, quesId);
        if (ute == null) return null;
        return getByStuAndUTEId(studentId, ute.getId());
    }

    //统计某个随堂测试包下的多个习题提交数据
    public List<StatisticsVo> countStatisticsByUTEIds(int classStudentNum, Set<Long> uteIds) {
        if (CollectionUtils.isEmpty(uteIds)) return null;
        List<StatisticsVo> voList = new ArrayList<>();
        uteIds.forEach(id -> {
            StatisticsVo vo = countStatisticsByUTEId(classStudentNum, id);
            if (vo == null) return;
            voList.add(vo);
        });
        return voList;
    }

    //统计某个随堂测试包下的某道题的提交数据
    public StatisticsVo countStatisticsByUTEId(int classStudentNum, long uteId) {
        int submitNum = 0;//已提交的人数
        int submitNoSelect = 0;//提交未选择人数
        int rightNum = 0;//答对人数
        int wrongNum = 0;//答错人数
        List<StudentExerciseSubmit> submitList = getListByUTEId(uteId);
        Map<String, Integer> opCountMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(submitList)) {
            submitNum = submitList.size();
            for (StudentExerciseSubmit submit : submitList) {
                if (submit.getAnswerResult() == null || submit.getAnswerResult() <= 0) {
                    submitNoSelect++;
                }
                if (submit.getAnswerResult() != null) {
                    if (StudentExercisesEnum.isRight(submit.getAnswerResult())) {
                        rightNum++;
                    }
                    if (StudentExercisesEnum.isWrong(submit.getAnswerResult())) {
                        wrongNum++;
                    }
                }
                //获取习题类型
                ExerciseDomain question = bizUnitTestExerciseService.getQuestionByUTEId(submit.getUnitTestExerciseId());
                if (question == null || !QuestionEnum.hasOptions(question.getType())) continue;

                Integer num = opCountMap.get(submit.getStudentAnswer());
                num = num == null ? 1 : num + 1;
                opCountMap.put(submit.getStudentAnswer(), num);
            }
        }
        int unSubmitNum = classStudentNum - submitNum;//未提交的人数
        StatisticsVo vo = new StatisticsVo();
        vo.setUnitTestExerciseId(uteId);
        vo.setSubmitNum(submitNum);
        vo.setSubmitNoSelect(submitNoSelect);
        vo.setRightNum(rightNum);
        vo.setWrongNum(wrongNum);
        vo.setUnSubmitNum(unSubmitNum > 0 ? unSubmitNum : 0);
        vo.setClassStudentNum(classStudentNum);
        if (MapUtils.isNotEmpty(opCountMap)) {
            List<StatisticsVo.OptionVo> opList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : opCountMap.entrySet()) {
                StatisticsVo.OptionVo op = new StatisticsVo.OptionVo();
                op.setOptionName(entry.getKey());
                op.setSelectNum(entry.getValue());
                opList.add(op);
            }
            vo.setOptionList(opList);
        }
        return vo;
    }

    //获取统计详情，带学生姓名
    public StatisticsDetailVo countSubmitDetailByUTEId(long packageClassId, long uteId) {

        List<String> stuNameList = bizPackageClassService.getClassStuNameByPCId(packageClassId);//班级全部的学生姓名
        if (CollectionUtils.isEmpty(stuNameList)) return new StatisticsDetailVo(uteId);

        Set<Long> submitIds = new HashSet<>();
        Set<Long> submitNoSelectIds = new HashSet<>();
        Set<Long> rightIds = new HashSet<>();
        Set<Long> wrongIds = new HashSet<>();

        List<StudentExerciseSubmit> submitList = getListByUTEId(uteId);
        Map<String, Set<Long>> opMap = new HashMap<>(); //选项对应的学生id

        if (CollectionUtils.isNotEmpty(submitList)) {
            for (StudentExerciseSubmit submit : submitList) {
                submitIds.add(submit.getStudentId());

                if (submit.getAnswerResult() == null || submit.getAnswerResult() <= 0) {
                    submitNoSelectIds.add(submit.getStudentId());
                }
                if (submit.getAnswerResult() != null) {
                    if (StudentExercisesEnum.isRight(submit.getAnswerResult())) {
                        rightIds.add(submit.getStudentId());
                    }
                    if (StudentExercisesEnum.isWrong(submit.getAnswerResult())) {
                        wrongIds.add(submit.getStudentId());
                    }
                }
                //获取习题类型
                ExerciseDomain question = bizUnitTestExerciseService.getQuestionByUTEId(submit.getUnitTestExerciseId());
                if (question == null || !QuestionEnum.hasOptions(question.getType())) continue;

                //提交的答案和学生对应关系
                Set<Long> stuIds = opMap.get(submit.getStudentAnswer());
                if (stuIds == null) stuIds = new HashSet<>();
                stuIds.add(submit.getStudentId());
                opMap.put(submit.getStudentAnswer(), stuIds);
            }
        }

        Map<Long, Student> stuMap = bizStudentService.getMapByIds(submitIds);
        if (MapUtils.isEmpty(stuMap)) return new StatisticsDetailVo(uteId);

        List<String> submitNames = getStuNameByIds(submitIds, stuMap);//已提交的人姓名
        List<String> submitNoSelectNames = getStuNameByIds(submitNoSelectIds, stuMap);//提交未选择人姓名
        List<String> rightNames = getStuNameByIds(rightIds, stuMap);//答对人
        List<String> wrongNames = getStuNameByIds(wrongIds, stuMap);//答错人

        if (CollectionUtils.isNotEmpty(submitNames))
            stuNameList.removeIf(new Predicate<String>() {
                @Override
                public boolean test(String s) {
                    return submitNames.contains(s);
                }
            });

        StatisticsDetailVo vo = new StatisticsDetailVo();
        vo.setUnitTestExerciseId(uteId);
        vo.setUnSubmitName(BizUtil.arrToStr(stuNameList));
        vo.setRightName(BizUtil.arrToStr(rightNames));
        vo.setWrongName(BizUtil.arrToStr(wrongNames));
        vo.setSubmitNoSelect(BizUtil.arrToStr(submitNoSelectNames));

        List<StatisticsDetailVo.OptionVo> opList = new ArrayList<>();
        for (Map.Entry<String, Set<Long>> entry : opMap.entrySet()) {
            StatisticsDetailVo.OptionVo op = new StatisticsDetailVo.OptionVo();
            op.setOptionName(entry.getKey());
            op.setSelectedName(BizUtil.arrToStr(getStuNameByIds(entry.getValue(), stuMap)));
            opList.add(op);
        }
        vo.setOptions(opList);
        return vo;
    }

    private List<String> getStuNameByIds(Set<Long> ids, Map<Long, Student> stuMap) {
        if (CollectionUtils.isEmpty(ids)) return null;
        List<String> list = new ArrayList<>();
        ids.forEach(stuId -> {
            Student stu = stuMap.get(stuId);
            if (stu == null) return;
            list.add(stu.getStudentName());
        });
        return list;
    }

    public List<StudentExerciseSubmit> getListByUTEIds(Set<Long> uteIds) {
        StudentExerciseSubmitCriteria crt = new StudentExerciseSubmitCriteria();
        crt.createCriteria().andUnitTestExerciseIdIn(new ArrayList<>(uteIds));
        crt.noLimit();
        return selectList(crt);
    }

    public List<StudentExerciseSubmit> getListByUTEId(long uteId) {
        StudentExerciseSubmitCriteria crt = new StudentExerciseSubmitCriteria();
        crt.createCriteria().andUnitTestExerciseIdEqualTo(uteId);
        crt.noLimit();
        return selectList(crt);
    }

    public List<StudentExerciseSubmit> getListByStuAndUTEIds(long studentId, Set<Long> uteIds) {
        StudentExerciseSubmitCriteria crt = new StudentExerciseSubmitCriteria();
        crt.createCriteria().andStudentIdEqualTo(studentId).andUnitTestExerciseIdIn(new ArrayList<>(uteIds));
        crt.limitOne();
        return selectList(crt);
    }

    public StudentExerciseSubmit getByStuAndUTEId(long studentId, long uteId) {
        StudentExerciseSubmitCriteria crt = new StudentExerciseSubmitCriteria();
        crt.createCriteria().andStudentIdEqualTo(studentId).andUnitTestExerciseIdEqualTo(uteId);
        crt.limitOne();
        List<StudentExerciseSubmit> list = selectList(crt);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public boolean isExist(long studentId, long uteId) {
        StudentExerciseSubmitCriteria crt = new StudentExerciseSubmitCriteria();
        crt.createCriteria().andStudentIdEqualTo(studentId).andUnitTestExerciseIdEqualTo(uteId);
        return count(crt) > 0;
    }

    public List<StudentExerciseSubmit> selectList(StudentExerciseSubmitCriteria crt) {
        return studentExerciseSubmitMapper.selectByExample(crt);
    }

    public int count(StudentExerciseSubmitCriteria crt) {
        return studentExerciseSubmitMapper.countByExample(crt);
    }


    public int insertBatchNoExist(List<StudentExerciseSubmit> list) {
        if (CollectionUtils.isEmpty(list)) return 0;

        list.removeIf(new Predicate<StudentExerciseSubmit>() {
            @Override
            public boolean test(StudentExerciseSubmit o) {
                return isExist(o.getStudentId(), o.getUnitTestExerciseId());
            }
        });
        return insertBatch(list);
    }

    @Transactional(rollbackFor = Throwable.class)
    public int insertBatch(List<StudentExerciseSubmit> list) {
        if (CollectionUtils.isEmpty(list)) return 0;
        int n = studentExerciseSubmitMapper.insertBatch(list);

        list.forEach(o -> {
            bizStatisticsService.delByUTEId(o.getUnitTestExerciseId());
        });

        return n;
    }
}
