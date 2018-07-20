package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.util.List;

/**
 * @author liujianjian
 * @date 2018/7/12 19:28
 */
@Data
public class StatisticsVo extends BaseDomain {
    private Long packageClassId;
    private Long unitTestId;
    private Long exerciseId;
    private Long unitTestExerciseId;
    private Integer submitNum;
    private Integer unSubmitNum;
    private Integer submitNoSelect;
    private Integer rightNum;
    private Integer wrongNum;
    private Integer classStudentNum;
    private Integer questionType; //题目类型，1-选择题 2-判断题 3-填空题 4-主观题（跟2.0.3返回的一样）

    private String options;
    private List<OptionVo> optionList;

    private String unSubmitName = ""; //未提交的学生姓名，逗号拼接
    private String submitNoSelectName = ""; //已提交未选择的学生姓名
    private String rightName = ""; //答对的学生姓名
    private String wrongName = ""; //答错的学生姓名

    private boolean detailed;//是否已经带有详细统计

    public static class OptionVo {
        private String optionName;//选项名，如 A, B, C ...如果是判断题，返回right,wrong
        private Integer selectNum; //选择的人数
        private boolean rightFlag; //true-表示该选项是正确答案，false-不是
        private String selectedName = "";//选择该选项的学生姓名

        public boolean isRightFlag() {
            return rightFlag;
        }

        public void setRightFlag(boolean rightFlag) {
            this.rightFlag = rightFlag;
        }

        public String getOptionName() {

            return optionName != null ? optionName.replaceAll("\\[", "").replaceAll("]", "")
                    .replaceAll("'", "").replaceAll("\"", "") : null;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public Integer getSelectNum() {
            return selectNum;
        }

        public void setSelectNum(Integer selectNum) {
            this.selectNum = selectNum;
        }

        public String getSelectedName() {
            return selectedName;
        }

        public void setSelectedName(String selectedName) {
            this.selectedName = selectedName;
        }
    }
}
