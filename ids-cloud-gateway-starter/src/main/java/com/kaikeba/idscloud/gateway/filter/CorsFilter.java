package com.kaikeba.idscloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 网关统一跨域响应，如果前面网关已配置跨域（如：Nginx）则取消该配置，被代理服务一律不允许配置跨域
 *
 * @author: zmc
 * @date: 2021-12-14 17:13
 * @description:
 */
@Slf4j
@Component
public class CorsFilter implements WebFilter, Ordered {
    /**
     * x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client,Origin,No-Cache,X-Requested-With,If-Modified-Since,Pragma,Last-Modified,Cache-Control,Expires,Access-Control-Allow-Credentials,request-account-domain
     */
    private static final String ALLOWED_HEADERS = "*";
    private static final String ALLOWED_METHODS = "GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_EXPOSE = "*";
    private static final String MAX_AGE = "3600";
    private static final int ORDER_NO = 0;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            //  配置为*
            headers.add("Access-Control-Allow-Origin", request.getHeaders().getFirst(HttpHeaders.ORIGIN));
            headers.add("Access-Control-Allow-Credentials", "true");
            //   配置为*
            headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
            headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
            headers.add("Access-Control-Expose-Headers", ALLOWED_EXPOSE);
            /**Access-Control-Max-Age  限制下次的时间
             *第一次是浏览器使用OPTIONS方法发起一个预检请求
             *浏览器会限制从脚本发起的跨域HTTP请求（比如异步请求GET, POST, PUT, DELETE, OPTIONS等等），所以浏览器会向所请求的服务器发起两次请求，
             *第一次是浏览器使用OPTIONS方法发起一个预检请求，第二次才是真正的异步请求，第一次的预检请求获知服务器是否允许该跨域请求
             *如果允许，才发起第二次真实的请求；如果不允许，则拦截第二次请求。
             * */
            headers.add("Access-Control-Max-Age", MAX_AGE);
            /**header头 (206, "Partial Content")*/
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.PARTIAL_CONTENT);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return ORDER_NO;
    }
}
