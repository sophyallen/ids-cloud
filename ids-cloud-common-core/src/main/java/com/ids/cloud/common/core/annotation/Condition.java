package com.ids.cloud.common.core.annotation;

import java.lang.annotation.*;

/**
 * @author: zmc
 * @date: 2021-02-01 12:36
 * @description:
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

    String fieldName() default "";

    /**
     * 多条件链接方式
     * @return
     */
    CompareEnum compare() default CompareEnum.EQ;

    SortEnum orderBy() default SortEnum.NONE;

    enum SortEnum {
        NONE,
        ASC,
        DESC
    }

    enum CompareEnum {
        /**
         * equal
         */
        EQ,
        /**
         * greater than
         */
        GT,
        /**
         * less than
         */
        LT,
        /**
         * like %colums%
         */
        LIKE,
        /**
         * like %colums
         */
        LEFT_LIKE,
        /**
         * like colums%
         */
        RIGHT_LIKE,
        /**
         * greater than or equal
         */
        GTE,
        /**
         * less than or equal
         */
        LTE,
        /**
         * in
         */
        IN,
        /**
         * <> or !=
         */
        NEQ,
        /**
         * between
          */
        BETWEEN,
        /**
         * not null
         */
        NOT_NULL,
        /**
         * is null
         */
        IS_NULL;
    }
}
