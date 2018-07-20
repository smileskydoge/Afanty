package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/7 11:23
 */
@Data
public class StudentCollectExercisesQuery extends BaseQuery {

    private Long studentId;
    private Long packageClassId;
    private Long questionId;
    private Long subjectId;

    private Set<Long> questionIds;
}
