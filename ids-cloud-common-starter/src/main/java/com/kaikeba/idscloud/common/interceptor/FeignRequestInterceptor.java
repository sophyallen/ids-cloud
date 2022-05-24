package com.kaikeba.idscloud.common.interceptor;

import com.google.common.collect.ImmutableSet;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 微服务之间feign调用请求头丢失的问题
 * 加入微服务之间传递的唯一标识,便于追踪
 *
 * @author zmc
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    /**
     * 微服务之间传递的唯一标识
     */
    private final Set<String> headersNameSet = ImmutableSet.of(HttpHeaders.AUTHORIZATION,
            "userId", "authSource");

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null) {
            Map<String, String> headers = getHeaders(httpServletRequest);
            headers.entrySet().parallelStream()
                    // 过滤出来可以透传的 header
                    .filter(e -> headersNameSet.parallelStream().anyMatch(h -> h.equalsIgnoreCase(e.getKey())))
                    .forEach(e -> template.header(e.getKey(), e.getValue()));
            if (log.isDebugEnabled()) {
                log.debug("FeignRequestInterceptor:{}", template.toString());
            }
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

}
