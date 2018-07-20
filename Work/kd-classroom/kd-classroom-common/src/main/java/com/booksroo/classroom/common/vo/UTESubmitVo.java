package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.util.List;

/**
 * 随堂测试某个题某个学生的提交的信息封装
 *
 * @author liujianjian
 * @date 2018/7/12 19:28
 */
@Data
public class UTESubmitVo extends BaseDomain {
    private Long unitTestId;
    private Long exerciseId;
    private Long unitTestExerciseId;
    private Long studentId;
    private String studentName = ""; //学生姓名
    private Short answerResult = -1; //答题结果，1-答对，2-答错
    private List<String> studentAnswerList; //学生提交的答案，数组格式，如 ['A']，['right']，['填空1','填空2']，主观题的话就是['url']
}
