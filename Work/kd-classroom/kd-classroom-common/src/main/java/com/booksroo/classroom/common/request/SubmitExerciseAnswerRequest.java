package com.booksroo.classroom.common.request;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/13 19:27
 */
@Data
public class SubmitExerciseAnswerRequest extends BaseRequest {

    private long studentId;
    private long unitTestId;//随堂测试id
    private long exerciseId;// 习题id
    private String answer;// 学生的答案
    private String answerResult;//作答结果，right-答对，wrong-答错

}
