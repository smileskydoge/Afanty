package com.booksroo.classroom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.third.service.JedisService;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.vo.TeacherClassVo;
import com.booksroo.classroom.common.vo.TeacherVo;
import com.booksroo.classroom.service.interf.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.booksroo.classroom.common.constant.BizConstant.TOKEN_EXPIRED_TIME_SECONDS;
import static com.booksroo.classroom.common.constant.CacheKeyConstant.STUDENT_TOKEN_CACHE_KEY_PRE;
import static com.booksroo.classroom.common.constant.CacheKeyConstant.TEACHER_START_CLASS_DATA_CACHE_KEY;
import static com.booksroo.classroom.common.constant.CacheKeyConstant.TEACHER_TOKEN_CACHE_KEY_PRE;

/**
 * @author liujianjian
 * @date 2018/6/1 15:47
 */

@Service("redisCacheService")
public class RedisCacheService implements CacheService {

    @Autowired
    private JedisService jedisService;

    @Override
    public Set<String> getTeacherInClass() {
        String val = jedisService.get(TEACHER_START_CLASS_DATA_CACHE_KEY);
        return BizUtil.jsonStrToSet(val);
    }

    @Override
    public void setTeacherToken(long userId, String token, String action) {
        setUserToken(TEACHER_TOKEN_CACHE_KEY_PRE + userId, token, action);//token有效期14天
    }

    @Override
    public void setStudentToken(long userId, String token, String action) {
        setUserToken(STUDENT_TOKEN_CACHE_KEY_PRE + userId, token, action);//token有效期14天
    }

    private void setUserToken(String key, String token, String action) {
        setCache(key, token, TOKEN_EXPIRED_TIME_SECONDS);//
        setCache(token, action, TOKEN_EXPIRED_TIME_SECONDS);//
    }

    @Override
    public String getTeacherToken(long userId) {
        return BizUtil.removeQuotes(getCache(TEACHER_TOKEN_CACHE_KEY_PRE + userId));
    }

    @Override
    public String getStudentToken(long userId) {
        return BizUtil.removeQuotes(getCache(STUDENT_TOKEN_CACHE_KEY_PRE + userId));
    }

    @Override
    public String getTokenAction(String token) {
        return BizUtil.removeQuotes(getCache(BizUtil.removeQuotes(token)));
    }

    @Override
    public String get(String key) {
        return getCache(key);
    }

    @Override
    public void set(String key, String val) {
        setCache(key, val);
    }

    @Override
    public TeacherVo getSessionTeacherVo() {
        return null;
    }

    @Override
    public void setSessionTeacherVo(TeacherVo vo) {
//        setCache(WEB_SESSION_USER_KEY + "_teacher_" + vo.getId(), vo);
    }

    @Override
    public List<TeacherClassVo> getTeacherClassList(long teacherId) {
        return null;
    }

    @Override
    public void setTeacherClassList(long teacherId, List<TeacherClassVo> list) {
    }

    @Override
    public Long getTeacherDefCatalogId(long teacherId) {
        return null;
    }

    @Override
    public void setTeacherDefCatalogId(long teacherId, long catalogId) {

    }

    private void setCache(String key, Object value) {
        setCache(key, value, 60L * 30);
    }

    private void setCache(String key, Object value, long time) {
        jedisService.set(key, JSON.toJSONString(value), time);
    }

    private <T> T getCache(String key, Class<T> clazz) {
        String val = getCache(key);
        if (StringUtils.isBlank(val)) return null;

        return JSONObject.parseObject(val, clazz);
    }

    private String getCache(String key) {
        return jedisService.get(key);
    }
}
