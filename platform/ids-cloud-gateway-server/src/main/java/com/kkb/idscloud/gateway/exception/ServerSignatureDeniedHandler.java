package com.kkb.idscloud.gateway.exception;

import com.kkb.idscloud.common.core.exception.ThirdServerException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerSignatureDeniedHandler {
    Mono<Void> handle(ServerWebExchange var1, ThirdServerException var2);
}