package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.List;

@Data
public class UnitTestVo extends BaseDomain {

    private Long unitTestId;
    private Long packageClassId;
    private String name;

    private List<ExerciseVo> exerciseList;
}