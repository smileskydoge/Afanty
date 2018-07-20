package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/7 11:23
 */
@Data
public class StudentCollectQuery extends BaseQuery {

    private Long studentId;
    private Long packageClassId;
    private Long resourceId;
    private Long subjectId;

    private Set<Long> resourceIds;
}
