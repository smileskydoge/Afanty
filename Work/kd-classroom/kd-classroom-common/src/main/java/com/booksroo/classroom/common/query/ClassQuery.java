package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

@Data
public class ClassQuery extends BaseQuery {

    private Integer classNo;
    private Integer grade;
    private Long schoolId;
    private Set<Long> classIds;
}
