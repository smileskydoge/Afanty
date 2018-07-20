package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.util.Set;

@Data
public class TeacherAddVo extends BaseDomain {

    private Long schoolId;

    private Long classId;

    private String classIds;

    private String subjectId;

    private String teacherName;

    private String mobileNo;

    private String password;

    private Byte age;

    private String headImg;

    private Byte jobTitle;

    private Boolean delFlag;

}
