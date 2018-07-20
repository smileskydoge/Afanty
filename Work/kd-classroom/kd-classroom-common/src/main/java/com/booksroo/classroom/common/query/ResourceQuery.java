package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/7 11:23
 */
@Data
public class ResourceQuery extends BaseQuery {

    private Long classId;//teacher_class表id
    private Long teacherId;
    private Long resourceId;
    private Long packageId;
    private boolean needReturnChildList;//是否返回子资源

    private Set<Long> classIds;//teacher_class表ids

    private Set<Long> resourceIds;

    private String resourceName;

    private Integer resourceType;

    private boolean needReturnPackageClassId;//是否返回 packageClassId 字段值
}
