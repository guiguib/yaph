package com.gbourquet.yaph.serveur.metier.generated;

import java.util.ArrayList;
import java.util.List;

public class PasswordCardExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public PasswordCardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAdresseIsNull() {
            addCriterion("adresse is null");
            return (Criteria) this;
        }

        public Criteria andAdresseIsNotNull() {
            addCriterion("adresse is not null");
            return (Criteria) this;
        }

        public Criteria andAdresseEqualTo(String value) {
            addCriterion("adresse =", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseNotEqualTo(String value) {
            addCriterion("adresse <>", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseGreaterThan(String value) {
            addCriterion("adresse >", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseGreaterThanOrEqualTo(String value) {
            addCriterion("adresse >=", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseLessThan(String value) {
            addCriterion("adresse <", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseLessThanOrEqualTo(String value) {
            addCriterion("adresse <=", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseLike(String value) {
            addCriterion("adresse like", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseNotLike(String value) {
            addCriterion("adresse not like", value, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseIn(List<String> values) {
            addCriterion("adresse in", values, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseNotIn(List<String> values) {
            addCriterion("adresse not in", values, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseBetween(String value1, String value2) {
            addCriterion("adresse between", value1, value2, "adresse");
            return (Criteria) this;
        }

        public Criteria andAdresseNotBetween(String value1, String value2) {
            addCriterion("adresse not between", value1, value2, "adresse");
            return (Criteria) this;
        }

        public Criteria andUserIsNull() {
            addCriterion("user is null");
            return (Criteria) this;
        }

        public Criteria andUserIsNotNull() {
            addCriterion("user is not null");
            return (Criteria) this;
        }

        public Criteria andUserEqualTo(String value) {
            addCriterion("user =", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotEqualTo(String value) {
            addCriterion("user <>", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThan(String value) {
            addCriterion("user >", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThanOrEqualTo(String value) {
            addCriterion("user >=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThan(String value) {
            addCriterion("user <", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThanOrEqualTo(String value) {
            addCriterion("user <=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLike(String value) {
            addCriterion("user like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotLike(String value) {
            addCriterion("user not like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserIn(List<String> values) {
            addCriterion("user in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotIn(List<String> values) {
            addCriterion("user not in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserBetween(String value1, String value2) {
            addCriterion("user between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotBetween(String value1, String value2) {
            addCriterion("user not between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(Integer value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(Integer value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(Integer value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(Integer value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(Integer value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<Integer> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<Integer> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(Integer value1, Integer value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andTitreIsNull() {
            addCriterion("titre is null");
            return (Criteria) this;
        }

        public Criteria andTitreIsNotNull() {
            addCriterion("titre is not null");
            return (Criteria) this;
        }

        public Criteria andTitreEqualTo(String value) {
            addCriterion("titre =", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreNotEqualTo(String value) {
            addCriterion("titre <>", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreGreaterThan(String value) {
            addCriterion("titre >", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreGreaterThanOrEqualTo(String value) {
            addCriterion("titre >=", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreLessThan(String value) {
            addCriterion("titre <", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreLessThanOrEqualTo(String value) {
            addCriterion("titre <=", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreLike(String value) {
            addCriterion("titre like", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreNotLike(String value) {
            addCriterion("titre not like", value, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreIn(List<String> values) {
            addCriterion("titre in", values, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreNotIn(List<String> values) {
            addCriterion("titre not in", values, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreBetween(String value1, String value2) {
            addCriterion("titre between", value1, value2, "titre");
            return (Criteria) this;
        }

        public Criteria andTitreNotBetween(String value1, String value2) {
            addCriterion("titre not between", value1, value2, "titre");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table passwordCard
     *
     * @mbggenerated do_not_delete_during_merge Wed Jan 07 10:47:53 CET 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

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

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}