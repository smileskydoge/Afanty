package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectQuery extends BaseQuery {

    private String subjectName;

    private Set<Long> subjectIds;
}
