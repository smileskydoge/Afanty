package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.PackageInfo;
import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/10 17:06
 */
@Data
public class PackageVo extends PackageInfo {

    private Long packageClassId;

    private Byte status;//1-已上课
}
