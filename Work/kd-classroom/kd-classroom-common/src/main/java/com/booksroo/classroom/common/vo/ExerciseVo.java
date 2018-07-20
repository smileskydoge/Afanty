package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import com.booksroo.classroom.common.util.BizUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class ExerciseVo extends BaseDomain {
    private Long exerciseId;
    private Long teacherId;
    private Long unitTestId = -1L;
    private Byte type = -1;
    private String content = "";
    private String contentUrl = "";
    private String analyze = "";
    private String analyzeUrl = "";
    private String answer = "";
    private Byte answerNum = -1;

    private Boolean collected = false;

    private String studentAnswer; //查询指定学生的答案
    private Integer answerResult = -1; //答题结果，1-答对，2-答错
    private Integer resourceType = 3;//资源类型，3-习题

    public Long getExerciseId() {
        return exerciseId == null || exerciseId <= 0 ? super.getId() : exerciseId;
    }

    public Long getResourceId() {
        return getExerciseId();
    }

    public Long getExercisesId() {
        return getExerciseId();
    }

    public Long getQuestionId() {
        return getExerciseId();
    }

    public List<String> getAnswerList() {
        if (StringUtils.isBlank(answer) || !answer.startsWith("[")) return null;
        return BizUtil.jsonStrToList(answer);
    }

    public List<String> getStudentAnswerList() {
        if (StringUtils.isBlank(studentAnswer) || !studentAnswer.startsWith("[")) return null;
        return BizUtil.jsonStrToList(studentAnswer);
    }
}
