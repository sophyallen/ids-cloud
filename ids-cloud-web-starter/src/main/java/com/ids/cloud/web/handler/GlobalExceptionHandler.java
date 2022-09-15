package com.ids.cloud.web.handler;

import com.ids.cloud.common.core.constants.ErrorCodeEnum;
import com.ids.cloud.common.core.exception.IdsException;
import com.ids.cloud.common.core.model.ResultBody;
import com.ids.cloud.common.utils.ExceptionResolverUtil;
import lombok.extern.slf4j.Slf4j;
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
