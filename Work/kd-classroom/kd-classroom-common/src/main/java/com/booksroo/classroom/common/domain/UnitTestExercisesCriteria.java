package com.booksroo.classroom.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UnitTestExercisesCriteria extends BaseCriteria{

    /**
     * unit_test_exercises表的操作属性:oredCriteria
     * 创建时间 : 2018-07-12 15:22:12
     */
    protected List<Criteria> oredCriteria;

    /**
     * unit_test_exercises数据表的操作方法: UnitTestExercisesCriteria  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public UnitTestExercisesCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * unit_test_exercises数据表的操作方法: setOrderByClause  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * unit_test_exercises数据表的操作方法: getOrderByClause  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * unit_test_exercises数据表的操作方法: getOredCriteria  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * unit_test_exercises数据表的操作方法: or  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * unit_test_exercises数据表的操作方法: or  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * unit_test_exercises数据表的操作方法: createCriteria  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * unit_test_exercises数据表的操作方法: createCriteriaInternal  
     * 创建时间 : 2018-07-12 15:22:12
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * unit_test_exercises数据表的操作方法: clear  
     * 创建时间 : 2018-07-12 15:22:12
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * unit_test_exercises表的操作类
     * 创建时间 : 2018-07-12 15:22:12
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

        public Criteria andUnitTestIdIsNull() {
            addCriterion("unit_test_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdIsNotNull() {
            addCriterion("unit_test_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdEqualTo(Long value) {
            addCriterion("unit_test_id =", value, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdNotEqualTo(Long value) {
            addCriterion("unit_test_id <>", value, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdGreaterThan(Long value) {
            addCriterion("unit_test_id >", value, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdGreaterThanOrEqualTo(Long value) {
            addCriterion("unit_test_id >=", value, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdLessThan(Long value) {
            addCriterion("unit_test_id <", value, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdLessThanOrEqualTo(Long value) {
            addCriterion("unit_test_id <=", value, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdIn(List<Long> values) {
            addCriterion("unit_test_id in", values, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdNotIn(List<Long> values) {
            addCriterion("unit_test_id not in", values, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdBetween(Long value1, Long value2) {
            addCriterion("unit_test_id between", value1, value2, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andUnitTestIdNotBetween(Long value1, Long value2) {
            addCriterion("unit_test_id not between", value1, value2, "unitTestId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdIsNull() {
            addCriterion("exercises_id is null");
            return (Criteria) this;
        }

        public Criteria andExercisesIdIsNotNull() {
            addCriterion("exercises_id is not null");
            return (Criteria) this;
        }

        public Criteria andExercisesIdEqualTo(Long value) {
            addCriterion("exercises_id =", value, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdNotEqualTo(Long value) {
            addCriterion("exercises_id <>", value, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdGreaterThan(Long value) {
            addCriterion("exercises_id >", value, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdGreaterThanOrEqualTo(Long value) {
            addCriterion("exercises_id >=", value, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdLessThan(Long value) {
            addCriterion("exercises_id <", value, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdLessThanOrEqualTo(Long value) {
            addCriterion("exercises_id <=", value, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdIn(List<Long> values) {
            addCriterion("exercises_id in", values, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdNotIn(List<Long> values) {
            addCriterion("exercises_id not in", values, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdBetween(Long value1, Long value2) {
            addCriterion("exercises_id between", value1, value2, "exercisesId");
            return (Criteria) this;
        }

        public Criteria andExercisesIdNotBetween(Long value1, Long value2) {
            addCriterion("exercises_id not between", value1, value2, "exercisesId");
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
     * unit_test_exercises表的操作类
     * 创建时间 : 2018-07-12 15:22:12
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * unit_test_exercises表的操作类
     * 创建时间 : 2018-07-12 15:22:12
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