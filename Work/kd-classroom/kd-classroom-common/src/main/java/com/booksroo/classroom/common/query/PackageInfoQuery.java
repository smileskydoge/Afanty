package com.booksroo.classroom.common.query;

import lombok.Data;

import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/8 11:09
 */
@Data
public class PackageInfoQuery extends BaseQuery {
    private Long teacherId;
    private Long teacherClassId;
    private String packageName;
    private Long classId;//teacher_class表id
    private Long packageId;
    private Long resourceId;

    private Set<Long> resourceIds;
    private Set<Long> packageIds;
    private Integer status;
}
