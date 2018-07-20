package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/14 9:34
 */
@Data
public class FileNotesQuery extends BaseQuery {

    private Set<Long> ids;

    private Long childResourceId;
}
