package com.booksroo.classroom.common.query;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/6 17:04
 */
@Data
public class TeacherCatalogQuery extends BaseQuery {

    private Long teacherId;

    private String catalogName;
}
