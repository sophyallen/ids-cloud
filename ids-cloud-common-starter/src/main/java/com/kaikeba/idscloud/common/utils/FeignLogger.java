package com.kaikeba.idscloud.common.utils;

import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;

/**
 * Feign请求日志处理类<br/>
 * 所有通过Feign请求的外部接口都会被监控
 *
 * @version V1.0
 * @author: liusai
 * @date:
 */
@Slf4j
public class FeignLogger extends feign.Logger {
    static ThreadLocal<Map<String, String>> logContext = new ThreadLocal();
    static String PATH = "path";
    static String METHOD = "method";
    static String REQUEST_BODY = "body";

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        Map<String, String> logMap = new HashMap<>(3);
        logMap.put(PATH, request.url());
        logMap.put(METHOD, request.method());
        logMap.put(REQUEST_BODY, request.body() == null ? null :
                request.charset() == null ? null : new String(request.body(), request.charset()));
        logContext.set(logMap);
    }

    @Override
    protected Response logAndRebufferResponse(
            String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        Map<String, String> requetParam = logContext.get();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(requetParam.get(METHOD)).append(" ")
                .append(response.status()).append(" ")
                .append(requetParam.get(PATH)).append(" ")
                .append(elapsedTime);
        if (requetParam.get(REQUEST_BODY) != null) {
            stringBuilder.append(" REQUEST BODY:").append(requetParam.get(REQUEST_BODY));
        }
        logContext.remove();
        // 返回参数
        if (response.body() != null && !(response.status() == 204 || response.status() == 205)) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            if (bodyData.length > 0) {
                String responseBody = decodeOrDefault(bodyData, UTF_8, "Binary data");
                stringBuilder
                        .append(" RESPONSE BODY:")
                        .append(responseBody.replaceAll("\\s*|\t|\r|\n", ""));
            }
            log.info(stringBuilder.toString());
            return response.toBuilder().body(bodyData).build();
        }
        stringBuilder
                .append(" RESPONSE STATUS:")
                .append(response.status()).append(" RESPONSE REASON:").append(response.reason());
        log.info(stringBuilder.toString());
        return response;
    }

    @Override
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        Map<String, String> requetParam = logContext.get();
        StringBuilder stringBuilder = new StringBuilder();
        if (null != requetParam && requetParam.size() > 0) {
            stringBuilder
                    .append(requetParam.get(METHOD)).append(" ")
                    .append(ioe.getClass().getSimpleName()).append(" ")
                    .append(requetParam.get(PATH)).append(" ")
                    .append(elapsedTime);
            if (requetParam.get(REQUEST_BODY) != null) {
                stringBuilder.append(" REQUEST BODY:").append(requetParam.get(REQUEST_BODY));
            }
            logContext.remove();
        }
        stringBuilder.append(" IOEXCEPTION MESSAGE:").append(ioe.getMessage());
        log.error(stringBuilder.toString());
        log.error(ioe.getMessage(), ioe);
        return ioe;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (log.isInfoEnabled()) {
            log.info(String.format(methodTag(configKey) + format, args));
        }
    }
}