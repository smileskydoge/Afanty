package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/8 11:09
 */
@Data
public class PackageResourceQuery extends BaseQuery {
    private Long packageClassId;
    private Long resourceId;

    private Set<Long> resourceIds;
    private Set<Long> packageClassIds;

    private Integer resourceType;
}
