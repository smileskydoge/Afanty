package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/13 23:23
 */
@Data
public class PackageResourceNoteQuery extends BaseQuery {

    private Long packageClassId;
    private Integer resourceType;
    private Long resourceId;
    private Set<Long> resourceIds;
    private Set<Long> childResourceIds;
    private Long studentId;
    private Long childResourceId;
    private Short teacherShowFlag;

}
