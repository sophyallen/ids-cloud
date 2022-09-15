package com.ids.cloud.flux.exception;

import com.ids.cloud.common.core.exception.ThirdServerException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerSignatureDeniedHandler {
    Mono<Void> handle(ServerWebExchange var1, ThirdServerException var2);
}