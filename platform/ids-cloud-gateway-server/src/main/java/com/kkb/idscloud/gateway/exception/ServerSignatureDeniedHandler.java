package com.kkb.idscloud.gateway.exception;

import com.kkb.idscloud.common.exception.OpenSignatureException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerSignatureDeniedHandler {
    Mono<Void> handle(ServerWebExchange var1, OpenSignatureException var2);
}