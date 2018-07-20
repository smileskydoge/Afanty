package com.booksroo.classroom.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticsInfoCriteria extends BaseCriteria{

    /**
     * statistics_info表的操作属性:oredCriteria
     * 创建时间 : 2018-07-13 13:54:06
     */
    protected List<Criteria> oredCriteria;

    /**
     * statistics_info数据表的操作方法: StatisticsInfoCriteria  
     * 创建时间 : 2018-07-13 13:54:06
     */
    public StatisticsInfoCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * statistics_info数据表的操作方法: getOredCriteria  
     * 创建时间 : 2018-07-13 13:54:06
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * statistics_info数据表的操作方法: or  
     * 创建时间 : 2018-07-13 13:54:06
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * statistics_info数据表的操作方法: or  
     * 创建时间 : 2018-07-13 13:54:06
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * statistics_info数据表的操作方法: createCriteria  
     * 创建时间 : 2018-07-13 13:54:06
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * statistics_info数据表的操作方法: createCriteriaInternal  
     * 创建时间 : 2018-07-13 13:54:06
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * statistics_info数据表的操作方法: clear  
     * 创建时间 : 2018-07-13 13:54:06
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * statistics_info表的操作类
     * 创建时间 : 2018-07-13 13:54:06
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

        public Criteria andSubmitNumIsNull() {
            addCriterion("submit_num is null");
            return (Criteria) this;
        }

        public Criteria andSubmitNumIsNotNull() {
            addCriterion("submit_num is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitNumEqualTo(Integer value) {
            addCriterion("submit_num =", value, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumNotEqualTo(Integer value) {
            addCriterion("submit_num <>", value, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumGreaterThan(Integer value) {
            addCriterion("submit_num >", value, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("submit_num >=", value, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumLessThan(Integer value) {
            addCriterion("submit_num <", value, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumLessThanOrEqualTo(Integer value) {
            addCriterion("submit_num <=", value, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumIn(List<Integer> values) {
            addCriterion("submit_num in", values, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumNotIn(List<Integer> values) {
            addCriterion("submit_num not in", values, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumBetween(Integer value1, Integer value2) {
            addCriterion("submit_num between", value1, value2, "submitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNumNotBetween(Integer value1, Integer value2) {
            addCriterion("submit_num not between", value1, value2, "submitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumIsNull() {
            addCriterion("un_submit_num is null");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumIsNotNull() {
            addCriterion("un_submit_num is not null");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumEqualTo(Integer value) {
            addCriterion("un_submit_num =", value, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumNotEqualTo(Integer value) {
            addCriterion("un_submit_num <>", value, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumGreaterThan(Integer value) {
            addCriterion("un_submit_num >", value, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("un_submit_num >=", value, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumLessThan(Integer value) {
            addCriterion("un_submit_num <", value, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumLessThanOrEqualTo(Integer value) {
            addCriterion("un_submit_num <=", value, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumIn(List<Integer> values) {
            addCriterion("un_submit_num in", values, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumNotIn(List<Integer> values) {
            addCriterion("un_submit_num not in", values, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumBetween(Integer value1, Integer value2) {
            addCriterion("un_submit_num between", value1, value2, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andUnSubmitNumNotBetween(Integer value1, Integer value2) {
            addCriterion("un_submit_num not between", value1, value2, "unSubmitNum");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectIsNull() {
            addCriterion("submit_no_select is null");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectIsNotNull() {
            addCriterion("submit_no_select is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectEqualTo(Integer value) {
            addCriterion("submit_no_select =", value, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectNotEqualTo(Integer value) {
            addCriterion("submit_no_select <>", value, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectGreaterThan(Integer value) {
            addCriterion("submit_no_select >", value, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectGreaterThanOrEqualTo(Integer value) {
            addCriterion("submit_no_select >=", value, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectLessThan(Integer value) {
            addCriterion("submit_no_select <", value, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectLessThanOrEqualTo(Integer value) {
            addCriterion("submit_no_select <=", value, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectIn(List<Integer> values) {
            addCriterion("submit_no_select in", values, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectNotIn(List<Integer> values) {
            addCriterion("submit_no_select not in", values, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectBetween(Integer value1, Integer value2) {
            addCriterion("submit_no_select between", value1, value2, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andSubmitNoSelectNotBetween(Integer value1, Integer value2) {
            addCriterion("submit_no_select not between", value1, value2, "submitNoSelect");
            return (Criteria) this;
        }

        public Criteria andRightNumIsNull() {
            addCriterion("right_num is null");
            return (Criteria) this;
        }

        public Criteria andRightNumIsNotNull() {
            addCriterion("right_num is not null");
            return (Criteria) this;
        }

        public Criteria andRightNumEqualTo(Integer value) {
            addCriterion("right_num =", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumNotEqualTo(Integer value) {
            addCriterion("right_num <>", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumGreaterThan(Integer value) {
            addCriterion("right_num >", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("right_num >=", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumLessThan(Integer value) {
            addCriterion("right_num <", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumLessThanOrEqualTo(Integer value) {
            addCriterion("right_num <=", value, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumIn(List<Integer> values) {
            addCriterion("right_num in", values, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumNotIn(List<Integer> values) {
            addCriterion("right_num not in", values, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumBetween(Integer value1, Integer value2) {
            addCriterion("right_num between", value1, value2, "rightNum");
            return (Criteria) this;
        }

        public Criteria andRightNumNotBetween(Integer value1, Integer value2) {
            addCriterion("right_num not between", value1, value2, "rightNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumIsNull() {
            addCriterion("wrong_num is null");
            return (Criteria) this;
        }

        public Criteria andWrongNumIsNotNull() {
            addCriterion("wrong_num is not null");
            return (Criteria) this;
        }

        public Criteria andWrongNumEqualTo(Integer value) {
            addCriterion("wrong_num =", value, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumNotEqualTo(Integer value) {
            addCriterion("wrong_num <>", value, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumGreaterThan(Integer value) {
            addCriterion("wrong_num >", value, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("wrong_num >=", value, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumLessThan(Integer value) {
            addCriterion("wrong_num <", value, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumLessThanOrEqualTo(Integer value) {
            addCriterion("wrong_num <=", value, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumIn(List<Integer> values) {
            addCriterion("wrong_num in", values, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumNotIn(List<Integer> values) {
            addCriterion("wrong_num not in", values, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumBetween(Integer value1, Integer value2) {
            addCriterion("wrong_num between", value1, value2, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andWrongNumNotBetween(Integer value1, Integer value2) {
            addCriterion("wrong_num not between", value1, value2, "wrongNum");
            return (Criteria) this;
        }

        public Criteria andOptionsIsNull() {
            addCriterion("options is null");
            return (Criteria) this;
        }

        public Criteria andOptionsIsNotNull() {
            addCriterion("options is not null");
            return (Criteria) this;
        }

        public Criteria andOptionsEqualTo(String value) {
            addCriterion("options =", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotEqualTo(String value) {
            addCriterion("options <>", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsGreaterThan(String value) {
            addCriterion("options >", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsGreaterThanOrEqualTo(String value) {
            addCriterion("options >=", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsLessThan(String value) {
            addCriterion("options <", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsLessThanOrEqualTo(String value) {
            addCriterion("options <=", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsLike(String value) {
            addCriterion("options like", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotLike(String value) {
            addCriterion("options not like", value, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsIn(List<String> values) {
            addCriterion("options in", values, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotIn(List<String> values) {
            addCriterion("options not in", values, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsBetween(String value1, String value2) {
            addCriterion("options between", value1, value2, "options");
            return (Criteria) this;
        }

        public Criteria andOptionsNotBetween(String value1, String value2) {
            addCriterion("options not between", value1, value2, "options");
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
     * statistics_info表的操作类
     * 创建时间 : 2018-07-13 13:54:06
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * statistics_info表的操作类
     * 创建时间 : 2018-07-13 13:54:06
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