package com.ids.cloud.common.core.utils;


import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.ids.cloud.common.core.constants.ErrorCodeEnum;
import com.ids.cloud.common.core.model.ResultBody;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zmc
 * @description hibernate validation 自动校验工具
 *
 */
public class ValidatorUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();


    public static <T> ResultBody<?> validate(T obj) {
        return validate(obj, Default.class);
    }

    public static <T> ResultBody<?> validate(T obj, Class<?>... clazzes) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, clazzes);
        if (set.isEmpty()) {
            return ResultBody.ok();
        }

        String subMessage = tipsMsg(set);
        return ResultBody.failed(ErrorCodeEnum.CLIENT_ERROR_A0400, subMessage);
    }

    public static <T> void assertValidate(T obj) {
        assertValidate(obj, Default.class);
    }

    public static <T> void assertValidate(T obj, Class<?>... clazzes) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, clazzes);
        ErrorCodeEnum.CLIENT_ERROR_A0400.assertEmpty(set, tipsMsg(set));
    }

    private static <T> String tipsMsg(Set<ConstraintViolation<T>> set) {
        Map<String, StringBuffer> errorMap = new HashMap<String, StringBuffer>();
        return set.parallelStream().map(c -> c.getPropertyPath().toString()
                + CharUtil.AMP + CharUtil.SPACE + c.getMessage())
                .collect(Collectors.joining(StrUtil.COMMA));

    }

}  