package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.SchoolMapper;
import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.query.SchoolQuery;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.vo.PageList;
import com.booksroo.classroom.common.vo.SchoolVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/3 10:57
 */
@Service("bizSchoolService")
public class BizSchoolService extends BaseService{

    @Autowired
    private SchoolMapper schoolMapper;

    public School getById(long id) {
        return schoolMapper.selectByPrimaryKey(id);
    }

    // id in() 查询
    public Map<Long, School> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return new HashMap<>();
        Map<Long,School> schoolMap = new HashMap<>();
        for (Long l:ids){
            schoolMap.put(l,schoolMapper.selectByPrimaryKey(l));
        }
        return schoolMap;
    }

    //获取学校信息列表
    public PageList<SchoolVo> getSchoolInfo(SchoolQuery schoolQuery) throws Exception{

        long count = count(schoolQuery);
        if (count <= 0) return PageList.newInstance();

        List<School> list = schoolMapper.querySchoolList(schoolQuery);
        List<SchoolVo> voList = new ArrayList<>();
        for(School s:list){
            SchoolVo schoolVo = new SchoolVo();
            BeanUtilsExt.copyProperties(schoolVo,s);
            voList.add(schoolVo);
        }
        return new PageList<>(count,voList);
    }

    private long count(SchoolQuery schoolQuery) {
        return schoolMapper.count(schoolQuery);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addSchoolService(School school) throws Exception{
        if (schoolMapper.insertSelective(school)<=0)return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteSchoolService(Long id) throws Exception{
        if (schoolMapper.deleteByPrimaryKey(id)<=0)return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateSchoolService(School school) throws Exception {
        if(schoolMapper.updateByPrimaryKeySelective(school)<=0)return false;
        return true;
    }

    public List<School> getAllSchoolInfo(){
        return schoolMapper.queryAllSchoolInfo();
    }

}
