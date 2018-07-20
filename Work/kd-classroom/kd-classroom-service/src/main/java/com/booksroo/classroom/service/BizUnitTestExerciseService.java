package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.UnitTestExercisesMapper;
import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.UnitTestExercises;
import com.booksroo.classroom.common.domain.UnitTestExercisesCriteria;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.VoFactory;
import com.booksroo.classroom.common.vo.ExerciseVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("bizUnitTestExerciseService")
public class BizUnitTestExerciseService extends BaseService {

    @Autowired
    private UnitTestExercisesMapper unitTestExercisesMapper;
    @Autowired
    private BizExerciseService bizExerciseService;

    public List<UnitTestExercises> getListByQuesId(long quesId) {
        UnitTestExercisesCriteria crt = new UnitTestExercisesCriteria();
        crt.createCriteria().andExercisesIdEqualTo(quesId);
        crt.noLimit();
        return selectList(crt);
    }

    public int insertBatch(long unitTestId, String exerciseIds) {
        List<Long> ids = BizUtil.strToLongList(exerciseIds);
        if (CollectionUtils.isEmpty(ids)) return 0;
        return insertBatch(unitTestId, ids);
    }

    public int insertBatch(long unitTestId, List<Long> quesIds) {
        if (CollectionUtils.isEmpty(quesIds)) return 0;
        List<UnitTestExercises> list = new ArrayList<>();
        quesIds.forEach(id -> {
            if (isUTIdAndEIdExist(unitTestId, id)) return;

            UnitTestExercises ute = new UnitTestExercises();
            ute.setUnitTestId(unitTestId);
            ute.setExercisesId(id);
            list.add(ute);
        });
        return unitTestExercisesMapper.insertBatch(list);
    }

    public int countByUTIdAndEId(long unitTestId, long quesId) {
        UnitTestExercisesCriteria crt = new UnitTestExercisesCriteria();
        crt.createCriteria().andUnitTestIdEqualTo(unitTestId).andExercisesIdEqualTo(quesId);
        return count(crt);
    }

    public UnitTestExercises getByUTIdAndEId(long unitTestId, long quesId) {
        UnitTestExercisesCriteria crt = new UnitTestExercisesCriteria();
        crt.createCriteria().andUnitTestIdEqualTo(unitTestId).andExercisesIdEqualTo(quesId);
        crt.limitOne();
        List<UnitTestExercises> list = selectList(crt);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public Map<String, Long> getMapIdByUTIdAndEIds(long unitTestId, Set<Long> quesIds) {
        UnitTestExercisesCriteria crt = new UnitTestExercisesCriteria();
        crt.createCriteria().andUnitTestIdEqualTo(unitTestId).andExercisesIdIn(new ArrayList<>(quesIds));
        List<UnitTestExercises> list = selectList(crt);
        if (CollectionUtils.isEmpty(list)) return null;
        Map<String, Long> map = new HashMap<>();
        list.forEach(o -> {
            map.put(unitTestId + "_" + o.getExercisesId(), o.getId());
        });
        return map;
    }

    //获取单元测试下的习题
    public List<ExerciseVo> getExerciseVoListByUTId(long unitTestId, Boolean del) {
        List<UnitTestExercises> uteList = getListByUTId(unitTestId);
        if (CollectionUtils.isEmpty(uteList)) return null;

        Set<Long> quesIds = new HashSet<>();
        uteList.forEach(o -> {
            quesIds.add(o.getExercisesId());
        });

        List<ExerciseDomain> list = bizExerciseService.getListByIds(quesIds, del);
        if (CollectionUtils.isEmpty(list)) return null;

        List<ExerciseVo> voList = new ArrayList<>(list.size());
        list.forEach(o -> {
            ExerciseVo vo = VoFactory.newExerciseVo(o);
            vo.setUnitTestId(unitTestId);
            voList.add(vo);
        });
        return voList;
    }

    public List<UnitTestExercises> getListByUTId(long unitTestId) {
        UnitTestExercisesCriteria crt = new UnitTestExercisesCriteria();
        crt.createCriteria().andUnitTestIdEqualTo(unitTestId);
        crt.noLimit();
        crt.setOrderByClause("id asc");
        return selectList(crt);
    }

    public boolean isUTIdAndEIdExist(long unitTestId, long exerciseId) {
        UnitTestExercisesCriteria crt = new UnitTestExercisesCriteria();
        crt.createCriteria().andUnitTestIdEqualTo(unitTestId).andExercisesIdEqualTo(exerciseId);
        return count(crt) > 0;
    }

    public List<UnitTestExercises> selectList(UnitTestExercisesCriteria crt) {
        return unitTestExercisesMapper.selectByExample(crt);
    }

    public int count(UnitTestExercisesCriteria crt) {
        return unitTestExercisesMapper.countByExample(crt);
    }

    //根据id获取习题
    public ExerciseDomain getQuestionByUTEId(long id) {
        UnitTestExercises ute = getByPk(id);
        if (ute == null) return null;
        return bizExerciseService.getByPK(ute.getExercisesId());
    }

    public UnitTestExercises getByPk(long id) {
        return unitTestExercisesMapper.selectByPrimaryKey(id);
    }

}
