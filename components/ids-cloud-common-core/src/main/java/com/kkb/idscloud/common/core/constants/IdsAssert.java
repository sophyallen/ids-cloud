package com.kkb.idscloud.common.core.constants;

import com.kkb.idscloud.common.core.exception.IdsException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public interface IdsAssert {
    /**
     * 创建异常
     *
     * @param msg
     * @return
     */
    IdsException newException(String msg);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        assertNotNull(obj, null);
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param obj 待判断对象
     * @param msg message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, String msg) {
        assertTrue(t -> t != null, obj, msg);
    }

    default void assertIsNull(Object obj) {
        assertIsNull(obj, null);
    }

    default void assertIsNull(Object obj, String msg) {
        assertTrue(t -> t == null, obj, msg);
    }

    default void assertEmpty(Object obj) {
        assertTrue(getAssertEmptyPredicate(), obj);
    }

    /**
     * null "" 空数组、集合都为true
     * @param obj
     * @param msg
     */
    default void assertEmpty(Object obj, String msg) {
        assertTrue(getAssertEmptyPredicate(), obj, msg);
    }

    default void assertNotEmpty(Object obj, String msg) {
        assertTrue(getAssertEmptyPredicate().negate(), obj, msg);
    }

    default void assertNotEmpty(Object obj) {
        assertTrue(getAssertEmptyPredicate().negate(), obj);
    }

    default <T> void assertTrue(Predicate<T> predicate, T obj) {
        assertTrue(predicate, obj, null);
    }

    default <T> void assertTrue(Predicate<T> predicate, T obj, String msg) {
        if (predicate.negate().test(obj)) {
            throw newException(msg);
        }
    }

    default <T> void assertFalse(Predicate<T> predicate, T obj) {
        assertFalse(predicate, obj, null);
    }

    default <T> void assertFalse(Predicate<T> predicate, T obj, String msg) {
        if (predicate.test(obj)) {
            throw newException(msg);
        }
    }

    default <T> Predicate<T> getAssertEmptyPredicate() {
        return o -> {
            if (o == null) {
                return true;
            } else if (o.getClass().isArray()) {
                return Array.getLength(o) == 0;
            } else if (o instanceof CharSequence) {
                return ((CharSequence) o).length() == 0;
            } else if (o instanceof Collection) {
                return ((Collection) o).isEmpty();
            } else {
                return o instanceof Map ? ((Map) o).isEmpty() : false;
            }
        };
    }

}

