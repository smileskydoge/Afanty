package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

@Data
public class TeacherQuery extends BaseQuery {

    private String teacherName;

    private String subjectName;

    private String mobileNo;

    private Long subjectId;

    private Byte jobTitle;

    private Long schoolId;

    private String classIdstr;

    private Set<Long> classIds;

    private Set<Long> teacherIds;

}
