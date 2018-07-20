package com.booksroo.classroom.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.StatisticsInfoMapper;
import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.VoFactory;
import com.booksroo.classroom.common.vo.StatisticsDetailVo;
import com.booksroo.classroom.common.vo.StatisticsVo;
import com.booksroo.classroom.common.vo.UTESubmitVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

@Service("bizStatisticsService")
public class BizStatisticsService extends BaseService {

    @Autowired
    private StatisticsInfoMapper statisticsInfoMapper;
    @Autowired
    private BizUnitTestExerciseService bizUnitTestExerciseService;
    @Autowired
    private BizStudentExerciseSubmitService bizStudentExerciseSubmitService;
    @Autowired
    private BizPackageClassService bizPackageClassService;
    @Autowired
    private BizExerciseService bizExerciseService;
    @Autowired
    private BizStudentService bizStudentService;

    //获取随堂测试下某道题的学生提交情况
    public List<UTESubmitVo> getStuSubmitListByUTE(long unitTestId, long questionId) {

        UnitTestExercises ute = bizUnitTestExerciseService.getByUTIdAndEId(unitTestId, questionId);
        if (ute == null) return null;

        List<StudentExerciseSubmit> submitList = bizStudentExerciseSubmitService.getListByUTEId(ute.getId());
        if (CollectionUtils.isEmpty(submitList)) return null;

        Set<Long> stuIds = new HashSet<>(submitList.size());
        List<UTESubmitVo> voList = new ArrayList<>(submitList.size());
        submitList.forEach(o -> {
            if (StringUtils.isBlank(o.getStudentAnswer())) return;

            UTESubmitVo vo = new UTESubmitVo();
            vo.setExerciseId(questionId);
            vo.setUnitTestExerciseId(ute.getId());
            vo.setUnitTestId(unitTestId);
            vo.setStudentId(o.getStudentId());
            vo.setAnswerResult(o.getAnswerResult());
            vo.setStudentAnswerList(BizUtil.jsonStrToList(o.getStudentAnswer()));
            vo.setId(o.getId());
            vo.setCreateTime(o.getCreateTime());
            voList.add(vo);
            stuIds.add(o.getStudentId());
        });

        if (CollectionUtils.isEmpty(voList)) return null;
        Map<Long, Student> map = bizStudentService.getMapByIds(stuIds);
        if (MapUtils.isEmpty(map)) return voList;

        voList.forEach(vo -> {
            Student student = map.get(vo.getStudentId());
            if (student == null) return;
            vo.setStudentName(student.getStudentName());
        });

        return voList;
    }

    //查询随堂测试的统计概览
    public List<StatisticsVo> getVoListByUnitTestId(Long packageClassId, long unitTestId, Boolean detail) throws Exception {
        if (detail == null) detail = false;
        List<UnitTestExercises> uteList = bizUnitTestExerciseService.getListByUTId(unitTestId);
        if (CollectionUtils.isEmpty(uteList)) return null;

        Set<Long> ids = new HashSet<>(uteList.size());
        Set<Long> quesIds = new HashSet<>(uteList.size());
        Map<Long, UnitTestExercises> map = new HashMap<>();
        uteList.forEach(o -> {
            ids.add(o.getId());
            quesIds.add(o.getExercisesId());
            map.put(o.getId(), o);
        });

        int classStudentNum = 0;//班级人数
        if (isValidId(packageClassId)) {
            ClassDomain cd = bizPackageClassService.getClassByPCId(packageClassId);
            if (cd == null) throw new BizException("班级信息不存在");
            classStudentNum = cd.getStudentNum();
        }
        final int csn = classStudentNum;

        Map<Long, ExerciseDomain> quesMap = bizExerciseService.getMapByIds(quesIds);
        List<StatisticsVo> voList = getVoListByUTEIds(ids);
        if (CollectionUtils.isEmpty(voList)) {
            //如果统计表里没有习题的统计信息，则从submit表里实时统计一遍
            voList = bizStudentExerciseSubmitService.countStatisticsByUTEIds(csn, ids);
            bindPCIdAndUTIdAndEId(packageClassId, unitTestId, voList, map, null);
            if (detail) bindStatisticsDetail(packageClassId, voList);
            bindQuesAndOptionStr(voList, quesMap);
            //把统计的数据同步到统计表里
            insertBatchVo(voList);
            return voList;
        }

        Set<Long> dbIds = new HashSet<>(voList.size());
        voList.forEach(vo -> {
            dbIds.add(vo.getUnitTestExerciseId());
        });
        //如果ids的大小比voList多，则对多出来的id，查询下student_exercises_submit表获取该题最新的答题情况，并统计保存
        ids.removeIf(new Predicate<Long>() {
            @Override
            public boolean test(Long id) {
                return dbIds.contains(id);
            }
        });

        if (CollectionUtils.isNotEmpty(ids)) {
            List<StatisticsVo> submitVoList = bizStudentExerciseSubmitService.countStatisticsByUTEIds(csn, ids);
            if (CollectionUtils.isNotEmpty(submitVoList)) {
                voList.addAll(submitVoList);
            }
        }

        bindPCIdAndUTIdAndEId(packageClassId, unitTestId, voList, map, dbIds);
        if (detail) bindStatisticsDetail(packageClassId, voList);
        bindQuesAndOptionStr(voList, quesMap);

        //设置未提交人数数据
        voList.forEach(vo -> {
            if (vo.getUnSubmitNum() != null && vo.getUnSubmitNum() > 0) return;
            vo.setClassStudentNum(csn);
            vo.setUnSubmitNum((csn - vo.getSubmitNum()) > 0 ? (csn - vo.getSubmitNum()) : 0);
        });

        insertBatchVo(voList);
        return voList;
    }

    public List<StatisticsVo> getVoListByUTEIds(Set<Long> ids) {
        List<StatisticsInfo> list = getListByUTEIds(ids);
        if (CollectionUtils.isEmpty(list)) return null;
        List<StatisticsVo> voList = new ArrayList<>(list.size());
        list.forEach(o -> {
            voList.add(VoFactory.newStatisticsVo(o));
        });
        return voList;
    }

    public List<StatisticsInfo> getListByUTEIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        StatisticsInfoCriteria crt = new StatisticsInfoCriteria();
        crt.noLimit();
        crt.createCriteria().andUnitTestExerciseIdIn(new ArrayList<>(ids));
        crt.setOrderByClause("unit_test_exercise_id asc");
        return selectList(crt);
    }

    public int updateByUTEId(StatisticsInfo record) {
        StatisticsInfoCriteria crt = new StatisticsInfoCriteria();
        crt.createCriteria().andUnitTestExerciseIdEqualTo(record.getUnitTestExerciseId());
        return statisticsInfoMapper.updateByExampleSelective(record, crt);
    }

    public List<StatisticsInfo> selectList(StatisticsInfoCriteria crt) {
        return statisticsInfoMapper.selectByExample(crt);
    }

    public int count(StatisticsInfoCriteria crt) {
        return statisticsInfoMapper.countByExample(crt);
    }

    public boolean isExist(long uteId) {
        StatisticsInfoCriteria crt = new StatisticsInfoCriteria();
        crt.createCriteria().andUnitTestExerciseIdEqualTo(uteId);
        return count(crt) > 0;
    }

    public int insertBatchVo(List<StatisticsVo> voList) {
        if (CollectionUtils.isEmpty(voList)) return 0;

        List<StatisticsInfo> list = new ArrayList<>(voList.size());
        voList.forEach(vo -> {
            if (vo.getSubmitNum() == null || vo.getSubmitNum() <= 0) return;
            StatisticsInfo o = new StatisticsInfo();
            try {
                BeanUtilsExt.copyProperties(o, vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(o);
        });
        return insertBatchNoExist(list);
    }

    @Transactional(rollbackFor = Throwable.class)
    public int insertBatchNoExist(List<StatisticsInfo> list) {
        if (CollectionUtils.isEmpty(list)) return 0;
        list.forEach(o -> {
            delByUTEId(o.getUnitTestExerciseId());
        });
        return insertBatch(list);
    }

    private int insertBatch(List<StatisticsInfo> list) {
        if (CollectionUtils.isEmpty(list)) return 0;
        return statisticsInfoMapper.insertBatch(list);
    }

    private void bindPCIdAndUTIdAndEId(Long packageClassId, long unitTestId, List<StatisticsVo> voList, Map<Long, UnitTestExercises> map, Set<Long> ids) {
        if (CollectionUtils.isEmpty(voList)) return;

        boolean emptyMap = MapUtils.isEmpty(map);
        voList.forEach(vo -> {
            vo.setPackageClassId(packageClassId);
            if (ids != null && isValidId(vo.getUnitTestExerciseId())) {
                ids.add(vo.getUnitTestExerciseId());
            }
            vo.setUnitTestId(unitTestId);
            if (isValidId(vo.getExerciseId())) return;

            if (emptyMap) return;
            UnitTestExercises ute = map.get(vo.getUnitTestExerciseId());
            if (ute == null) return;
            vo.setExerciseId(ute.getExercisesId());
        });
    }

    private void bindQuesAndOptionStr(List<StatisticsVo> voList, Map<Long, ExerciseDomain> map) {
        if (CollectionUtils.isEmpty(voList) || MapUtils.isEmpty(map)) return;

        voList.forEach(vo -> {
            ExerciseDomain ques = map.get(vo.getExerciseId());
            if (ques == null) return;
            vo.setQuestionType(ques.getType().intValue());
            if (StringUtils.isBlank(ques.getAnswer())) return;
            if (CollectionUtils.isEmpty(vo.getOptionList()) && StringUtils.isNotBlank(vo.getOptions())) {
                vo.setOptionList(BizUtil.jsonStrToList(vo.getOptions(), StatisticsVo.OptionVo.class));
            }
            if (CollectionUtils.isEmpty(vo.getOptionList())) return;
            vo.getOptionList().forEach(op -> {
                op.setRightFlag(BizUtil.isOptionRight(ques.getAnswer(), op.getOptionName()));
            });
            if (StringUtils.isBlank(vo.getOptions())) {
                //设置options属性值为json串，不带学生姓名
                List<StatisticsVo.OptionVo> tempList = new ArrayList<>(vo.getOptionList().size());
                vo.getOptionList().forEach(o1 -> {
                    StatisticsVo.OptionVo o2 = new StatisticsVo.OptionVo();
                    o2.setOptionName(o1.getOptionName());
                    o2.setRightFlag(o1.isRightFlag());
                    o2.setSelectNum(o1.getSelectNum());
                    o2.setSelectedName(null);
                    tempList.add(o2);
                });
                vo.setOptions(JSON.toJSONString(tempList));
            }
        });
    }

    public int delByUTEId(long uteId) {
        StatisticsInfoCriteria crt = new StatisticsInfoCriteria();
        crt.createCriteria().andUnitTestExerciseIdEqualTo(uteId);
        crt.noLimit();
        return statisticsInfoMapper.deleteByExample(crt);
    }

    //设置统计的详细数据
    private void bindStatisticsDetail(long packageClassId, List<StatisticsVo> list) {

        if (CollectionUtils.isEmpty(list)) return;

        list.forEach(o -> {

            if (o.isDetailed()) return;

            o.setDetailed(true);
            StatisticsDetailVo detail = bizStudentExerciseSubmitService.countSubmitDetailByUTEId(packageClassId, o.getUnitTestExerciseId());
            if (detail == null) return;

            o.setRightName(detail.getRightName());
            o.setWrongName(detail.getWrongName());
            o.setUnSubmitName(detail.getUnSubmitName());
            o.setSubmitNoSelectName(detail.getSubmitNoSelect());

            if (!(CollectionUtils.isNotEmpty(o.getOptionList()) && CollectionUtils.isNotEmpty(detail.getOptions())))
                return;

            Map<String, String> map = new HashMap<>();
            detail.getOptions().forEach(d -> {
                map.put(d.getOptionName(), d.getSelectedName());
            });
            o.getOptionList().forEach(op -> {
                op.setSelectedName(map.get(op.getOptionName()));
            });
        });
    }
}
