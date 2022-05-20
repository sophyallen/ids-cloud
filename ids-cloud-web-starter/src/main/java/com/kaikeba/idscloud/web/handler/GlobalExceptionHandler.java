package com.kaikeba.idscloud.web.handler;

import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.core.exception.IdsException;
import com.kaikeba.idscloud.common.core.model.ResultBody;
import com.kaikeba.idscloud.common.utils.ExceptionResolverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理器
 *
 * @author zmc
 * @date 2017/7/3
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({IdsException.class})
    public ResultBody openException(IdsException ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = ExceptionResolverUtil.buildBody(ex, ex.getErrorCodeEnum(), request.getRequestURI());
        response.setStatus(ex.getErrorCodeEnum().getHttpStatus());
        return resultBody;
    }

    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResultBody exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ErrorCodeEnum code = ExceptionResolverUtil.resolveException(ex);
        response.setStatus(code.getHttpStatus());
        return ExceptionResolverUtil.buildBody(ex, code, request.getRequestURI());
    }

}
