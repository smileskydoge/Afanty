package com.booksroo.classroom.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.booksroo.classroom.common.constant.PromptConstant;
import com.booksroo.classroom.common.dao.ExerciseDomainMapper;
import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.PackageExercise;
import com.booksroo.classroom.common.enums.QuestionEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.ExerciseQuery;
import com.booksroo.classroom.common.request.UploadQuestionRequest;
import com.booksroo.classroom.common.util.*;
import com.booksroo.classroom.common.vo.ExerciseVo;
import com.booksroo.classroom.common.vo.PageList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.booksroo.classroom.common.constant.PromptConstant.QUESTION_ANSWER_INVALID;
import static com.booksroo.classroom.common.constant.PromptConstant.QUESTION_ANSWER_NUM_INVALID;


@Service("bizExerciseService")
public class BizExerciseService extends BaseService {

    @Autowired
    private ExerciseDomainMapper exerciseDomainMapper;

    @Autowired
    private BizPackageExerciseService bizPackageExerciseService;

    @Autowired
    private BizPackageClassService bizPackageClassService;

    @Autowired
    private BizExerciseClassService bizExerciseClassService;

    @Autowired
    private BizFileStoreService bizFileStoreService;

    @Autowired
    private BizTeacherClassService bizTeacherClassService;

    public ExerciseDomain getByPK(long id) {
        return exerciseDomainMapper.selectByPrimaryKey(id);
    }

    public Map<Long, ExerciseDomain> getMapByIds(Set<Long> ids) {
        List<ExerciseDomain> list = getListByIds(ids);
        if (CollectionUtils.isEmpty(list)) return null;
        Map<Long, ExerciseDomain> map = new HashMap<>();
        list.forEach(o -> {
            map.put(o.getId(), o);
        });
        return map;
    }

    public Map<Long, ExerciseVo> getVoMapByIds(Set<Long> ids) {
        return getVoMapByIds(ids, false);
    }

    public Map<Long, ExerciseVo> getVoMapByIds(Set<Long> ids, Boolean del) {
        List<ExerciseVo> list = getVoListByIds(ids, del);
        if (CollectionUtils.isEmpty(list)) return null;
        Map<Long, ExerciseVo> map = new HashMap<>();
        list.forEach(o -> {
            map.put(o.getId(), o);
        });
        return map;
    }

    public List<ExerciseDomain> getListByIds(Set<Long> ids) {
        return getListByIds(ids, false);
    }

    public List<ExerciseDomain> getListByIds(Set<Long> ids, Boolean del) {
        if (CollectionUtils.isEmpty(ids)) return null;
        BaseQuery query = new BaseQuery();
        query.noLimit();
        query.setIds(ids);
        query.setDelFlag(del);
        return exerciseDomainMapper.getExerciseByids(query);
    }

    public List<ExerciseVo> getVoListByIds(Set<Long> ids, Boolean del) {
        List<ExerciseDomain> list = getListByIds(ids, del);
        if (CollectionUtils.isEmpty(list)) return null;

        List<ExerciseVo> voList = new ArrayList<>(list.size());
        list.forEach(o -> {
            voList.add(VoFactory.newExerciseVo(o));

        });
        return voList;
    }

    //根据teacherId 获取教师所有习题
    public PageList<ExerciseVo> getExerciseInfoByTeacherId(BaseQuery query) throws Exception {
        List<ExerciseDomain> exercises = exerciseDomainMapper.getExcerciseIdsByTeacherId(query);
        Long count = exerciseDomainMapper.countByTeacherId(query);
        if (exercises == null) {
            return PageList.newInstance();
        }
        List<ExerciseVo> datas = new ArrayList<>();

        for (ExerciseDomain e : exercises) {
            ExerciseVo exerciseVo = new ExerciseVo();
            BeanUtilsExt.propertyUtilsCopy(exerciseVo, e);
            datas.add(exerciseVo);
        }
        return new PageList<>(count, datas);
    }


    public PageList<ExerciseVo> getEInfoByTId(ExerciseQuery exerciseQuery) throws Exception {
        List<ExerciseDomain> exercises = exerciseDomainMapper.getEIdsByTId(exerciseQuery);
        if (exercises == null) {
            return PageList.newInstance();
        }
        List<ExerciseVo> datas = new ArrayList<>();
        int count = exercises.size();

        for (ExerciseDomain e : exercises) {
            ExerciseVo exerciseVo = new ExerciseVo();
            BeanUtilsExt.propertyUtilsCopy(exerciseVo, e);
            datas.add(exerciseVo);
        }
        return new PageList<>(count, datas);
    }


    //删除全部资源下习题
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delExerciseOnAll(Long exerciseId) {
        if (exerciseDomainMapper.delExerciseOnAll(exerciseId) <= 0) return false;
        return true;
    }

    //删除班级资源包下习题
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delExerciseOnCP(Long packageClassId, Long exerciseId) throws BizException {
        Long tcId = bizPackageClassService.getTCIdByPCId(packageClassId);
        if (bizExerciseClassService.deleteExerciseClassMapper(exerciseId, tcId)
                && bizPackageExerciseService.deletePEMapper(exerciseId, packageClassId))
            return true;
        return false;
    }

    //删除班级下习题
    @Transactional(rollbackFor = Throwable.class)
    public Boolean delExerciseOnTC(Long teacherClassId, Long exerciseId) throws BizException {
        Set<Long> pcIds = bizPackageClassService.getPackageClassIdsByCid(teacherClassId);
        for (Long pcId : pcIds) {
            if (!bizPackageExerciseService.deletePEMapper(exerciseId, pcId)
                    && !bizExerciseClassService.deleteExerciseClassMapper(exerciseId, teacherClassId))
                throw new BizException("delete Exercise By tcId error");
        }
        return true;
    }

    public PageList<ExerciseVo> getExerciseInfoByPackageId(Long packageId, Long teacherClassId) throws Exception {
        if (packageId == null || teacherClassId == null) throw new BizException("packageId or teacherClassId is null");

        Set<Long> exerciseIds = bizPackageExerciseService.getExerciseIdsByPackageId(packageId, teacherClassId);

        BaseQuery query = new BaseQuery();
        query.setId(null);
        query.setIds(exerciseIds);
        return getExerciseInfo(query);
    }

    // by exerciseIds
    public PageList<ExerciseVo> getExerciseInfo(BaseQuery query) throws Exception {
        List<ExerciseDomain> exercises = getListByIds(query.getIds());
//        Long count = exerciseDomainMapper.count(query);
        if (exercises == null) {
//            log.info("query exercise is null");
            return PageList.newInstance();
        }
        List<ExerciseVo> voList = new ArrayList<>();
        for (ExerciseDomain e : exercises) {
            voList.add(VoFactory.newExerciseVo(e));
        }
        return new PageList<>(exercises.size(), voList);
    }

    public ExerciseDomain getExerciseInfoById(Long exerciseId) {
        return exerciseDomainMapper.selectByPrimaryKey(exerciseId);
    }

    public PageList<ExerciseVo> getExerciseInfoByPCId(BaseQuery query) throws Exception {
        Set<Long> exerciseIds = bizPackageExerciseService.getExerciseIdsByPCId(query.getId());
        query.setIds(exerciseIds);
        return getExerciseInfo(query);
    }

    public PageList<ExerciseVo> getEInfoByPCId(ExerciseQuery exerciseQuery) throws Exception {
        Set<Long> exerciseIds = bizPackageExerciseService.getExerciseIdsByPCId(exerciseQuery.getId());
        if (CollectionUtils.isEmpty(exerciseIds)) return PageList.newInstance();
        exerciseQuery.setIds(exerciseIds);

        List<ExerciseDomain> exercises = exerciseDomainMapper.getEInfoByids(exerciseQuery);
        if (exercises == null) {
            log.info("query exercise is null");
            return PageList.newInstance();
        }
        List<ExerciseVo> datas = new ArrayList<>();

        for (ExerciseDomain e : exercises) {
            ExerciseVo exerciseVo = new ExerciseVo();
            BeanUtilsExt.propertyUtilsCopy(exerciseVo, e);
            datas.add(exerciseVo);
        }
        return new PageList<>(exercises.size(), datas);
    }

    //根据tcId 获取习题列表
    public PageList<ExerciseVo> getExerciseInfoByTCId(Long teacherClassId, Integer page, Integer limit) throws Exception {
        Set<Long> eIds = bizExerciseClassService.getExerciseIdsByTCId(teacherClassId);
        BaseQuery query = new BaseQuery();
        query.setIds(eIds);
        query.setPage(page);
        query.setLimit(limit);
        return getExerciseInfo(query);
    }

    //上传习题信息
    public Boolean uploadExerciseInfo(long teacherId, UploadQuestionRequest req) throws Exception {

        ParamCheckUtil.checkNotNull(req.getType());
        ParamCheckUtil.checkNotEmptyStr(req.getContentImage());
        boolean editFlag = isValidId(req.getId());

        if (!QuestionEnum.isSubjective(req.getType())) {
            //除了主观题，其它题目的答案必填
            checkQuesAnswer(req.getAnswer());
            if (QuestionEnum.isCompletion(req.getType())) {
                checkFillBlock(req.getAnswerNum(), req.getAnswer());
            }
            if (QuestionEnum.isChoice(req.getType())) {
                checkOpSizeByAnswer(req.getAnswerNum(), req.getAnswer());
            }
        }

        ExerciseDomain exerciseDomain = new ExerciseDomain();
        if (editFlag) exerciseDomain.setId(req.getId());
        exerciseDomain.setTeacherId(teacherId);
        exerciseDomain.setContent(req.getContent());
        exerciseDomain.setAnalyze(req.getAnalyze());
        exerciseDomain.setAnswer(req.getAnswer());
        exerciseDomain.setAnswerNum(req.getAnswerNum());
        exerciseDomain.setType(req.getType());

        if (req.getContentImage().contains("data:image/png;base64")) {
            req.setContentImage(BizUtil.removeBase64Tag(req.getContentImage()));
        }
        byte[] contentImg = FileUtil.genByteFromBase64Str(req.getContentImage());

        String contentUrl = bizFileStoreService.handleFileStore(contentImg, ".png", teacherId + "_" + System.currentTimeMillis() + "_C");
        if (StringUtils.isBlank(contentUrl))
            throw new BizException(PromptConstant.UPLOAD_EXERCISE_IMAGE_ERROR, "上传习题图片失败");
        exerciseDomain.setContentUrl(contentUrl);

        if (StringUtils.isNotBlank(req.getAnalyzeImage())) {
            if (req.getAnalyzeImage().contains("data:image/png;base64")) {
                req.setAnalyzeImage(BizUtil.removeBase64Tag(req.getAnalyzeImage()));
            }
            byte[] analyzeImg = FileUtil.genByteFromBase64Str(req.getAnalyzeImage());
            String analyzeUrl = bizFileStoreService.handleFileStore(analyzeImg, ".png", teacherId + "_" + System.currentTimeMillis() + "_A");
            if (StringUtils.isBlank(analyzeUrl))
                throw new BizException(PromptConstant.UPLOAD_EXERCISE_IMAGE_ERROR, "上传习题解析图片失败");
            exerciseDomain.setAnalyzeUrl(analyzeUrl);
        }

        if (editFlag) {
            return exerciseDomainMapper.updateByPrimaryKeySelective(exerciseDomain) > 0;
        }
        return exerciseDomainMapper.insertSelective(exerciseDomain) > 0;
    }

    //校验习题答案
    private void checkQuesAnswer(String answer) throws Exception {
        if (StringUtils.isBlank(answer))
            throw new BizException(QUESTION_ANSWER_INVALID, "习题答案不能为空");
        List<String> list = BizUtil.jsonStrToList(answer);
        if (list == null || list.size() <= 0) throw new BizException(QUESTION_ANSWER_INVALID, "习题答案不能为空");
    }

    //校验选项个数跟答案
    private void checkOpSizeByAnswer(Byte num, String answer) throws Exception {
        if (num == null || num <= 0) return;
        //目前只判断单选题
        List<String> list = BizUtil.jsonStrToList(answer);
        if (CollectionUtils.isNotEmpty(list)) {
            String a = list.get(0);
            if (a.length() == 1) {
                char c = a.toCharArray()[0];
                int ascii = (int) c;
                int max = 65 + (num - 1);

                if (ascii > max || ascii < 65) throw new BizException(QUESTION_ANSWER_NUM_INVALID, "习题答案和选项个数不匹配");
            }
        }
    }

    //填空题参数判断
    private void checkFillBlock(Byte num, String answer) throws BizException {
        if (num == null || num <= 0) return;

        if (StringUtils.isEmpty(answer))
            throw new BizException(PromptConstant.UPLOAD_EXERCISE_FillBlock_PARAM_ERROR, "填空题答案未填写");
        Set<String> answers = BizUtil.jsonStrToSet(answer);
        for (String an : answers) {
            if ("".equals(an)) {
                throw new BizException(PromptConstant.UPLOAD_EXERCISE_FillBlock_PARAM_ERROR, "填空题答案未填写");
            }
        }
        if (num.intValue() != answers.size()) throw new BizException(QUESTION_ANSWER_NUM_INVALID, "答案和空的个数不匹配");
    }
}
