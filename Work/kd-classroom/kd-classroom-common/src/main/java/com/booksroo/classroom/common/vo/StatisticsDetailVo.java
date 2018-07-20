package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.util.List;

/**
 * @author liujianjian
 * @date 2018/7/12 19:28
 */
@Data
public class StatisticsDetailVo extends BaseDomain {

    private Long unitTestExerciseId;

    private String unSubmitName = ""; //未提交的学生姓名，逗号拼接
    private String submitNoSelect = ""; //已提交未选择的学生姓名
    private String rightName = ""; //答对的学生姓名
    private String wrongName = ""; //答错的学生姓名
    private List<OptionVo> options;

    public StatisticsDetailVo() {
    }

    public StatisticsDetailVo(Long unitTestExerciseId) {
        this.unitTestExerciseId = unitTestExerciseId;
    }

    public static class OptionVo {
        private String optionName = ""; //选项名
        private String selectedName = "";//选择该选项的学生姓名

        public String getSelectedName() {
            return selectedName;
        }

        public void setSelectedName(String selectedName) {
            this.selectedName = selectedName;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

    }
}
