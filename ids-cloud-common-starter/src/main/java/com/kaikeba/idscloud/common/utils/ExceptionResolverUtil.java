package com.kaikeba.idscloud.common.utils;

import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.core.exception.IdsException;
import com.kaikeba.idscloud.common.core.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author: zhangminchao
 * @date: 2022/04/02
 * @description:
 */
@Slf4j
public class ExceptionResolverUtil {
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
        } else if (className.contains("AccountExpiredException") ||
                className.contains("Unauthorized")) {
            code = ErrorCodeEnum.CLIENT_ERROR_A0230;
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
