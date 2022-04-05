package com.kaikeba.idscloud.gateway.filter;

import com.kaikeba.idscloud.gateway.service.AccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 日志过滤器
 *
 * @author zmc
 */
@Component
@Slf4j
public class AccessLogFilter implements WebFilter, Ordered {

    private AccessLogService accessLogService;

    public AccessLogFilter(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getAttributes().put("requestTime", new Date());
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        return bufferFactory.wrap(content);
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        decoratedResponse.beforeCommit(() -> Mono.fromRunnable(() ->
                accessLogService.sendLog(exchange, null)));
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}

