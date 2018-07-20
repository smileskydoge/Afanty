package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/5 19:02
 */
@Data
public class ClassDomainQuery extends BaseQuery {

    private Long teacherId;
    private Long classId;

    private Integer classNo;
    private Integer grade;
    private Long schoolId;

    private Set<Long> classIds;

    private String subjectId;

}
