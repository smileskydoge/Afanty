package com.booksroo.classroom.common.query;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/7 14:48
 */
@Data
public class TeacherResourceQuery extends BaseQuery {

    private Long teacherId;

    private Long resourceId;

    private Integer resourceType;
}
