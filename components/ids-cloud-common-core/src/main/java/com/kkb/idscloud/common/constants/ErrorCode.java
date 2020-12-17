package com.kkb.idscloud.common.constants;

/**
 * 自定义返回码
 *
 * @author admin
 */

public enum ErrorCode {
    /**
     * 成功
     */
    OK("00000", "success"),
    /**
     * 4xx
      */
    CLIENT_ERROR("A0001", "客户端请求错误"),
    // 5xx
    SERVER_ERROR("B0001", "系统执行出错"),
    // x
    THIRD_PARTY_ERROR("C0001", "其他服务异常"),

    /**
     * oauth2返回码
     */

    ACCESS_DENIED("03", "access_denied"),
    ACCESS_DENIED_BLACK_LIMITED("03", "access_denied_black_limited"),
    ACCESS_DENIED_WHITE_LIMITED("03", "access_denied_white_limited"),
    ACCESS_DENIED_AUTHORITY_EXPIRED("03", "access_denied_authority_expired"),
    ACCESS_DENIED_UPDATING("03", "access_denied_updating"),
    ACCESS_DENIED_DISABLED("03", "access_denied_disabled"),
    ACCESS_DENIED_NOT_OPEN("03", "access_denied_not_open"),
    /**
     * 账号错误
     */
    BAD_CREDENTIALS("00", "bad_credentials"),
    ACCOUNT_DISABLED("00", "account_disabled"),
    ACCOUNT_EXPIRED("00", "account_expired"),
    CREDENTIALS_EXPIRED("00", "credentials_expired"),
    ACCOUNT_LOCKED("00", "account_locked"),
    USERNAME_NOT_FOUND("00", "username_not_found"),

    /**
     * 请求错误
     */
    BAD_REQUEST("A0400", "bad_request"),
    NOT_FOUND("00", "not_found"),
    METHOD_NOT_ALLOWED("00", "method_not_allowed"),
    MEDIA_TYPE_NOT_ACCEPTABLE("00", "media_type_not_acceptable"),
    TOO_MANY_REQUESTS("02", "too_many_requests"),
    /**
     * 系统错误
     */
    ERROR("00", "error"),
    GATEWAY_TIMEOUT("00", "gateway_timeout"),
    SERVICE_UNAVAILABLE("00", "service_unavailable");


    private String code;
    private String message;

    ErrorCode() {
    }

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getResultEnum(String code) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return SERVER_ERROR;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
