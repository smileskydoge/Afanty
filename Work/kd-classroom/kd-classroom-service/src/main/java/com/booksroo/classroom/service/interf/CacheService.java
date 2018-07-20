package com.booksroo.classroom.service.interf;

import com.booksroo.classroom.common.vo.TeacherClassVo;
import com.booksroo.classroom.common.vo.TeacherVo;

import java.util.List;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/1 15:30
 */
public interface CacheService {

    Set<String> getTeacherInClass();

    void setTeacherToken(long userId, String token, String action);
    void setStudentToken(long userId, String token, String action);

    String getTeacherToken(long userId);
    String getStudentToken(long userId);

    String getTokenAction(String token);

    TeacherVo getSessionTeacherVo();

    void setSessionTeacherVo(TeacherVo vo);

    List<TeacherClassVo> getTeacherClassList(long teacherId);

    void setTeacherClassList(long teacherId, List<TeacherClassVo> list);

    Long getTeacherDefCatalogId(long teacherId);

    void setTeacherDefCatalogId(long teacherId, long catalogId);

    String get(String key);

    void set(String key, String val);
}
