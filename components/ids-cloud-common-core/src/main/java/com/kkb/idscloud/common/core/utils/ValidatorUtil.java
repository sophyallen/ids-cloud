package com.kkb.idscloud.common.core.utils;


import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.kkb.idscloud.common.core.constants.ErrorCodeEnum;
import com.kkb.idscloud.common.core.exception.IdsClientException;
import com.kkb.idscloud.common.core.exception.IdsException;
import com.kkb.idscloud.common.core.model.ResultBody;
import sun.jvm.hotspot.utilities.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zmc
 * @description hibernate validation 自动校验工具
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

    public static <T> boolean assertValidate(T obj, Class<T>... clazzes) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, clazzes);
        if (set.isEmpty()) {
            return Boolean.TRUE;
        } else {
            String subMessage = tipsMsg(set);
            throw new IdsClientException(ErrorCodeEnum.CLIENT_ERROR_A0400, subMessage);
        }
    }

    private static <T> String tipsMsg(Set<ConstraintViolation<T>> set) {
        Map<String, StringBuffer> errorMap = new HashMap<String, StringBuffer>();
        String property = null;
        for (ConstraintViolation<T> cv : set) {
            //这里循环获取错误信息，可以自定义格式
            property = cv.getPropertyPath().toString();
            if (errorMap.get(property) != null) {
                errorMap.get(property).append(CharUtil.AMP + cv.getMessage());
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(cv.getMessage());
                errorMap.put(property, sb);
            }
        }
        String subMessage = errorMap.entrySet().parallelStream()
                .map(e -> e.getKey() + StrUtil.COLON + e.getValue())
                .collect(Collectors.joining(StrUtil.COMMA));
        return subMessage;
    }

}  