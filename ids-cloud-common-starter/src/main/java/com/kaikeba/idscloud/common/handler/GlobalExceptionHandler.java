package com.kaikeba.idscloud.common.handler;

import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.core.exception.IdsException;
import com.kaikeba.idscloud.common.core.model.ResultBody;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

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
        ResultBody resultBody = buildBody(ex, ex.getErrorCodeEnum(), request.getRequestURI());
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
        ErrorCodeEnum code = resolveException(ex);
        response.setStatus(code.getHttpStatus());
        return buildBody(ex, code, request.getRequestURI());
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultBody handleBindException(BindException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("参数绑定校验异常", e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBody handleValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        log.error("参数绑定校验异常", e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private ResultBody wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return ResultBody.failed(ErrorCodeEnum.CLIENT_ERROR_A0400, msg.substring(2))
                .traceId(TraceContext.traceId());
    }


    /**
     * 静态解析异常。可以直接调用
     *
     * @param ex
     * @return
     */
    public static ErrorCodeEnum resolveException(Throwable ex) {
        if (ex instanceof IdsException) {
            return ((IdsException) ex).getErrorCodeEnum();
        }
        ErrorCodeEnum code = ErrorCodeEnum.SERVER_ERROR_B0001;
        String className = ex.getClass().getName();
        if (ex instanceof ResponseStatusException) {
            ResponseStatusException e = (ResponseStatusException) ex;
            if (e.getStatus().value() == HttpStatus.NOT_FOUND.value()) {
                return ErrorCodeEnum.CLIENT_ERROR_A0404;
            }
            if (e.getStatus().value() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                return ErrorCodeEnum.THIRD_PARTY_ERROR_C0111;
            }
            if (e.getStatus().is4xxClientError()) {
                return ErrorCodeEnum.CLIENT_ERROR_A0400;
            }
        } else if (ex instanceof HttpClientErrorException) {
            HttpClientErrorException e = (HttpClientErrorException)ex;
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ErrorCodeEnum.CLIENT_ERROR_A0404;
            }
            if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                return ErrorCodeEnum.THIRD_PARTY_ERROR_C0111;
            }
            if (e.getStatusCode().is4xxClientError()) {
                return ErrorCodeEnum.CLIENT_ERROR_A0400;
            }
        } else if (className.contains("UsernameNotFoundException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0201;
        } else if (className.contains("BadCredentialsException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0300;
        } else if (className.contains("AccountExpiredException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0203;
        } else if (className.contains("LockedException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0202;
        } else if (className.contains("DisabledException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0203;
        } else if (className.contains("CredentialsExpiredException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0311;
        } else if (className.contains("UserDeniedAuthorizationException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0303;
        } else if (className.contains("AccessDeniedException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0312;
        } else if (className.contains("HttpMessageNotReadableException")
                || className.contains("TypeMismatchException")
                || className.contains("MissingServletRequestParameterException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0400;
        } else if (className.contains("NoHandlerFoundException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0404;
        } else if (className.contains("HttpRequestMethodNotSupportedException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0405;
        } else if (className.contains("HttpMediaTypeNotAcceptableException")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0400;
        } else if (className.contains("ConnectTimeoutException")) {
            code = ErrorCodeEnum.THIRD_PARTY_ERROR_C0211;
        }
        return code;
    }

    /**
     * 构建返回结果对象
     *
     * @param throwable
     * @return
     */
    public static ResultBody<?> buildBody(Throwable throwable, ErrorCodeEnum resultCode, String path) {
        ResultBody<?> resultBody = ResultBody.failed().code(resultCode.getCode())
                .message(throwable instanceof IdsException ? throwable.getMessage() : resultCode.getMessage())
                .path(path)
                .traceId(TraceContext.traceId());
        log.error("GlobalExceptionHandler handle error: {}", resultBody, throwable.getMessage(), throwable);
        return resultBody;
    }

}
