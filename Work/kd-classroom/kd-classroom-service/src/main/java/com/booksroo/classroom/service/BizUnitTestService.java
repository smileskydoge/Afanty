package com.booksroo.classroom.service;

import com.booksroo.classroom.common.domain.UnitTestCriteria;
import com.booksroo.classroom.common.dao.UnitTestMapper;
import com.booksroo.classroom.common.domain.UnitTest;
import com.booksroo.classroom.common.query.UnitTestQuery;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.VoFactory;
import com.booksroo.classroom.common.vo.UnitTestVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("bizUnitTestService")
public class BizUnitTestService extends BaseService {

    @Autowired
    private UnitTestMapper unitTestMapper;
    @Autowired
    private BizUnitTestExerciseService bizUnitTestExerciseService;

    //判断习题是否已存在于包下的单元测试中
    public boolean isQuesExistIn(long pcId, long quesId) {
        List<UnitTest> utList = getListByPCId(pcId);
        if (CollectionUtils.isEmpty(utList)) return false;

        for (UnitTest o : utList) {
            long unitTestId = o.getId();
            int count = bizUnitTestExerciseService.countByUTIdAndEId(unitTestId, quesId);
            if (count > 0) return true;
        }
        return false;
    }

    public UnitTestVo createUnitTest(long packageClassId, String name) {

        if (StringUtils.isBlank(name)) name = "随堂测试1";
        int count = countByPCId(packageClassId);
        if (count > 0) name = "随堂测试" + (count + 1);

        UnitTest record = new UnitTest();
        record.setPackageClassId(packageClassId);
        record.setName(name);
        unitTestMapper.insertGetId(record);
        return VoFactory.newUnitTestVo(record);
    }

    public List<UnitTestVo> getVoListByPCId(long packageClassId) {
        List<UnitTest> list = getListByPCId(packageClassId);
        if (CollectionUtils.isEmpty(list)) return null;
        return bindVoList(list);
    }

    public List<UnitTest> getListByPCId(long packageClassId) {
        UnitTestCriteria crt = new UnitTestCriteria();
        crt.noLimit();
        crt.createCriteria().andPackageClassIdEqualTo(packageClassId);
        return selectList(crt);
    }

    public int countByPCId(long packageClassId) {
        UnitTestCriteria crt = new UnitTestCriteria();
        crt.noLimit();
        crt.createCriteria().andPackageClassIdEqualTo(packageClassId);
        return count(crt);
    }

    public List<UnitTest> getList(UnitTestQuery query) {
        UnitTestCriteria crt = new UnitTestCriteria();
        crt.bindPage(query);
        UnitTestCriteria.Criteria uc = crt.createCriteria();
        if (isValidId(query.getPackageClassId())) {
            uc.andPackageClassIdEqualTo(query.getPackageClassId());
        }
        return selectList(crt);
    }

    private List<UnitTest> selectList(UnitTestCriteria crt) {
        if (StringUtils.isBlank(crt.getOrderByClause())) {
            crt.setOrderByClause("id desc");
        }
        return unitTestMapper.selectByExample(crt);
    }

    public int count(UnitTestCriteria crt) {
        return unitTestMapper.countByExample(crt);
    }

    private List<UnitTestVo> bindVoList(List<UnitTest> list) {
        List<UnitTestVo> voList = new ArrayList<>(list.size());
        list.forEach(o -> {
            voList.add(VoFactory.newUnitTestVo(o));
        });
        return voList;
    }
}
