package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author liujianjian
 * @date 2018/6/13 13:13
 */
@Data
public class TeacherClassVo extends BaseDomain {

    private Long classId;

    private Byte grade;

    private Byte classNo;

    private Short classStudentNum;

    private Date termStartTime;

    private Date termEndTime;

    private String subjectId;

    private List<SubjectVo> subjectList;
}
