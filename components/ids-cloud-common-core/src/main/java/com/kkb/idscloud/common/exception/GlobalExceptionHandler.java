package com.kkb.idscloud.common.exception;

import com.kkb.idscloud.common.constants.ErrorCodeEnum;
import com.kkb.idscloud.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理器
 *
 * @author LYD
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
    @ExceptionHandler({IdsClientException.class})
    public static ResultBody openException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, request.getRequestURI());
        response.setStatus(resultBody.getHttpStatus());
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
    public static ResultBody exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, request.getRequestURI());
        response.setStatus(resultBody.getHttpStatus());
        return resultBody;
    }

    /**
     * 静态解析异常。可以直接调用
     *
     * @param ex
     * @return
     */
    public static ResultBody resolveException(Exception ex, String path) {
        ErrorCodeEnum code = ErrorCodeEnum.SERVER_ERROR_B0001;
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = ex.getMessage();
//        String superClassName = ex.getClass().getSuperclass().getName();
        String className = ex.getClass().getName();
        if (className.contains("UsernameNotFoundException")) {
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            code = ErrorCodeEnum.USER_ERROR_A0201;
        } else if (className.contains("BadCredentialsException")) {
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            code = ErrorCodeEnum.USER_ERROR_A0300;
        } else if (className.contains("AccountExpiredException")) {
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            code = ErrorCodeEnum.USER_ERROR_A0203;
        } else if (className.contains("LockedException")) {
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            code = ErrorCodeEnum.USER_ERROR_A0202;
        } else if (className.contains("DisabledException")) {
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            code = ErrorCodeEnum.USER_ERROR_A0203;
        } else if (className.contains("CredentialsExpiredException")) {
            httpStatus = HttpStatus.UNAUTHORIZED.value();
            code = ErrorCodeEnum.USER_ERROR_A0311;
        }   else if (className.contains("UserDeniedAuthorizationException")) {
            code = ErrorCodeEnum.USER_ERROR_A0303;
        } else if (className.contains("AccessDeniedException")) {
            code = ErrorCodeEnum.USER_ERROR_A0312;
            httpStatus = HttpStatus.FORBIDDEN.value();
        } else if (className.contains("HttpMessageNotReadableException")
                || className.contains("TypeMismatchException")
                || className.contains("MissingServletRequestParameterException")) {
            httpStatus = HttpStatus.BAD_REQUEST.value();
            code = ErrorCodeEnum.USER_ERROR_A0400;
        } else if (className.contains("NoHandlerFoundException")) {
            httpStatus = HttpStatus.NOT_FOUND.value();
            code = ErrorCodeEnum.USER_ERROR_A0404;
        } else if (className.contains("HttpRequestMethodNotSupportedException")) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
            code = ErrorCodeEnum.USER_ERROR_A0405;
        } else if (className.contains("HttpMediaTypeNotAcceptableException")) {
            httpStatus = HttpStatus.BAD_REQUEST.value();
            code = ErrorCodeEnum.USER_ERROR_A0400;
        } else if(message.equalsIgnoreCase(ErrorCodeEnum.USER_ERROR_A0501.name())){
            code = ErrorCodeEnum.USER_ERROR_A0501;
        }
        return buildBody(ex, code, path, httpStatus);
    }

    /**
     * 构建返回结果对象
     *
     * @param exception
     * @return
     */
    private static ResultBody buildBody(Exception exception, ErrorCodeEnum resultCode, String path, int httpStatus) {
        if (resultCode == null) {
            resultCode = ErrorCodeEnum.SERVER_ERROR_B0001;
        }
        ResultBody resultBody = ResultBody.failed().code(resultCode.getCode()).msg(exception.getMessage()).path(path).httpStatus(httpStatus);
        log.error("==> error:{} exception: {}",resultBody, exception.getMessage(), exception);
        return resultBody;
    }

}
