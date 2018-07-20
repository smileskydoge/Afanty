package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

@Data
public class StudentCollectExerciseVo extends BaseDomain {

    private Long subjectId;
    private Long studentId;
    private Long packageClassId;
    private Long exerciseId;
    private String studentAnswer;
    private Short answerResult;

}
