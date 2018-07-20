package com.booksroo.classroom.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentExerciseSubmitCriteria extends BaseCriteria{

    /**
     * student_exercise_submit表的操作属性:oredCriteria
     * 创建时间 : 2018-07-12 17:03:48
     */
    protected List<Criteria> oredCriteria;

    /**
     * student_exercise_submit数据表的操作方法: StudentExerciseSubmitCriteria  
     * 创建时间 : 2018-07-12 17:03:48
     */
    public StudentExerciseSubmitCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * student_exercise_submit数据表的操作方法: getOredCriteria  
     * 创建时间 : 2018-07-12 17:03:48
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * student_exercise_submit数据表的操作方法: or  
     * 创建时间 : 2018-07-12 17:03:48
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * student_exercise_submit数据表的操作方法: or  
     * 创建时间 : 2018-07-12 17:03:48
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * student_exercise_submit数据表的操作方法: createCriteria  
     * 创建时间 : 2018-07-12 17:03:48
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * student_exercise_submit数据表的操作方法: createCriteriaInternal  
     * 创建时间 : 2018-07-12 17:03:48
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * student_exercise_submit数据表的操作方法: clear  
     * 创建时间 : 2018-07-12 17:03:48
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * student_exercise_submit表的操作类
     * 创建时间 : 2018-07-12 17:03:48
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNull() {
            addCriterion("student_id is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("student_id is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Long value) {
            addCriterion("student_id =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Long value) {
            addCriterion("student_id <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Long value) {
            addCriterion("student_id >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("student_id >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Long value) {
            addCriterion("student_id <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Long value) {
            addCriterion("student_id <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Long> values) {
            addCriterion("student_id in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Long> values) {
            addCriterion("student_id not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Long value1, Long value2) {
            addCriterion("student_id between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Long value1, Long value2) {
            addCriterion("student_id not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdIsNull() {
            addCriterion("unit_test_exercise_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdIsNotNull() {
            addCriterion("unit_test_exercise_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdEqualTo(Long value) {
            addCriterion("unit_test_exercise_id =", value, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdNotEqualTo(Long value) {
            addCriterion("unit_test_exercise_id <>", value, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdGreaterThan(Long value) {
            addCriterion("unit_test_exercise_id >", value, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("unit_test_exercise_id >=", value, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdLessThan(Long value) {
            addCriterion("unit_test_exercise_id <", value, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdLessThanOrEqualTo(Long value) {
            addCriterion("unit_test_exercise_id <=", value, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdIn(List<Long> values) {
            addCriterion("unit_test_exercise_id in", values, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdNotIn(List<Long> values) {
            addCriterion("unit_test_exercise_id not in", values, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdBetween(Long value1, Long value2) {
            addCriterion("unit_test_exercise_id between", value1, value2, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andUnitTestExerciseIdNotBetween(Long value1, Long value2) {
            addCriterion("unit_test_exercise_id not between", value1, value2, "unitTestExerciseId");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerIsNull() {
            addCriterion("student_answer is null");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerIsNotNull() {
            addCriterion("student_answer is not null");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerEqualTo(String value) {
            addCriterion("student_answer =", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerNotEqualTo(String value) {
            addCriterion("student_answer <>", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerGreaterThan(String value) {
            addCriterion("student_answer >", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("student_answer >=", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerLessThan(String value) {
            addCriterion("student_answer <", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerLessThanOrEqualTo(String value) {
            addCriterion("student_answer <=", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerLike(String value) {
            addCriterion("student_answer like", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerNotLike(String value) {
            addCriterion("student_answer not like", value, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerIn(List<String> values) {
            addCriterion("student_answer in", values, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerNotIn(List<String> values) {
            addCriterion("student_answer not in", values, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerBetween(String value1, String value2) {
            addCriterion("student_answer between", value1, value2, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andStudentAnswerNotBetween(String value1, String value2) {
            addCriterion("student_answer not between", value1, value2, "studentAnswer");
            return (Criteria) this;
        }

        public Criteria andAnswerResultIsNull() {
            addCriterion("answer_result is null");
            return (Criteria) this;
        }

        public Criteria andAnswerResultIsNotNull() {
            addCriterion("answer_result is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerResultEqualTo(Short value) {
            addCriterion("answer_result =", value, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultNotEqualTo(Short value) {
            addCriterion("answer_result <>", value, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultGreaterThan(Short value) {
            addCriterion("answer_result >", value, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultGreaterThanOrEqualTo(Short value) {
            addCriterion("answer_result >=", value, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultLessThan(Short value) {
            addCriterion("answer_result <", value, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultLessThanOrEqualTo(Short value) {
            addCriterion("answer_result <=", value, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultIn(List<Short> values) {
            addCriterion("answer_result in", values, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultNotIn(List<Short> values) {
            addCriterion("answer_result not in", values, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultBetween(Short value1, Short value2) {
            addCriterion("answer_result between", value1, value2, "answerResult");
            return (Criteria) this;
        }

        public Criteria andAnswerResultNotBetween(Short value1, Short value2) {
            addCriterion("answer_result not between", value1, value2, "answerResult");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNull() {
            addCriterion("cost_time is null");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNotNull() {
            addCriterion("cost_time is not null");
            return (Criteria) this;
        }

        public Criteria andCostTimeEqualTo(Integer value) {
            addCriterion("cost_time =", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotEqualTo(Integer value) {
            addCriterion("cost_time <>", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThan(Integer value) {
            addCriterion("cost_time >", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("cost_time >=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThan(Integer value) {
            addCriterion("cost_time <", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThanOrEqualTo(Integer value) {
            addCriterion("cost_time <=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeIn(List<Integer> values) {
            addCriterion("cost_time in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotIn(List<Integer> values) {
            addCriterion("cost_time not in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeBetween(Integer value1, Integer value2) {
            addCriterion("cost_time between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("cost_time not between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     * student_exercise_submit表的操作类
     * 创建时间 : 2018-07-12 17:03:48
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * student_exercise_submit表的操作类
     * 创建时间 : 2018-07-12 17:03:48
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}