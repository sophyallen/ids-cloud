package com.ids.cloud.flux.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ids.cloud.flux.filter.context.GatewayContext;
import com.ids.cloud.flux.util.ReactiveWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;

/**
 * @author zmc
 * @date: 2019/5/8 11:27
 * @description:
 */
@Slf4j
public class AccessLogService {

    @Value("${spring.application.name}")
    private String defaultServiceId;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @JsonIgnore
    private Set<String> ignores = new HashSet<>(Arrays.asList(
            "/manage/**",
            "/webjars/**"
    ));

    /**
     * 不记录日志
     *
     * @param requestPath
     * @return
     */
    public boolean ignore(String requestPath) {
        return ignores.parallelStream()
                .anyMatch(p -> antPathMatcher.match(p, requestPath));
    }

    public void sendLog(ServerWebExchange exchange, Exception ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            int httpStatus = response.getStatusCode().value();
            String requestPath = request.getURI().getPath();
            if (ignore(requestPath)) {
                return;
            }
            String method = request.getMethodValue();
            Map<String, String> headers = request.getHeaders().toSingleValueMap();
            Map data = Maps.newHashMap();
            GatewayContext gatewayContext = exchange.getAttribute(GatewayContext.CACHE_GATEWAY_CONTEXT);
            if (gatewayContext != null) {
                data = gatewayContext.getAllRequestData().toSingleValueMap();
            }
            String serviceId = null;
            String ip = ReactiveWebUtils.getRemoteAddress(exchange);
            String userAgent = headers.get(HttpHeaders.USER_AGENT);
            Object requestTime = exchange.getAttribute("requestTime");
            String error = null;
            if (ex != null) {
                error = ex.getMessage();
            }
            Date responseTime = new Date();
            Long duringMS = responseTime.getTime() - ((Date) requestTime).getTime();
            Map<String, Object> map = Maps.newHashMap();
            map.put("requestTime", requestTime);
            map.put("serviceId", serviceId == null ? defaultServiceId : serviceId);
            map.put("httpStatus", httpStatus);
            map.put("headers", JSONObject.toJSON(headers));
            map.put("path", requestPath);
            map.put("params", JSONObject.toJSON(data));
            map.put("ip", ip);
            map.put("method", method);
            map.put("userAgent", userAgent);
            map.put("responseTime", new Date());
            map.put("exceptionMsg", error);
            map.put("duringMS", duringMS);
            log.info("gateway log: {}", map);
        } catch (Exception e) {
            log.error("access logs save error:{}", e);
        }

    }
}
