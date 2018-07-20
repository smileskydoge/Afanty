package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;


/**
 * @author liujianjian
 * @date 2018/6/2 23:53
 */
@Data
public class StudentQuery extends BaseQuery {

    private String studentNo;

    private String studentName;

    private Long schoolId;

    private Long classId;

    private String parentPhone;

    private String classIdstr;

    private Set<Long> classIds;
}
