package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import com.booksroo.classroom.common.domain.FileResourceChild;
import lombok.Data;

import java.util.List;

/**
 * @author liujianjian
 * @date 2018/6/7 9:57
 */
@Data
public class FileResourceVo extends BaseDomain {

    private Long teacherId;

    private Long resourceId;

    private String resourceName;

    private Integer resourceType;

    private List<FileResourceChild> childResourceList;

    private Long resourceClassId;

    private String packageId;

    private Long packageClassId;//收藏的资源的包id

    private Long packageResourceId;

    private Integer currentNo;

    private boolean collected;

    public FileResourceVo() {
    }

    public FileResourceVo(Long resourceId, String resourceName, Integer resourceType) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }

    public FileResourceVo(Long teacherId, Long resourceId, String resourceName, Integer resourceType, List<FileResourceChild> childResourceList) {
        this.teacherId = teacherId;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.childResourceList = childResourceList;
    }

    public Long getId() {
        return resourceId;
    }

}
