package com.kkb.idscloud.gateway.exception;

import com.alibaba.fastjson.JSONObject;
import com.kkb.idscloud.common.exception.GlobalExceptionHandler;
import com.kkb.idscloud.common.exception.ThirdServerException;
import com.kkb.idscloud.common.model.ResultBody;
import com.kkb.idscloud.gateway.service.AccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 网关权限异常处理,记录日志
 *
 * @author zmc
 */
@Slf4j
public class JsonSignatureDeniedHandler implements ServerSignatureDeniedHandler {
    private AccessLogService accessLogService;

    public JsonSignatureDeniedHandler(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, ThirdServerException e) {
        ResultBody resultBody = GlobalExceptionHandler.resolveException(e, exchange.getRequest().getURI().getPath());
        return Mono.defer(() -> {
            return Mono.just(exchange.getResponse());
        }).flatMap((response) -> {
            response.setStatusCode(HttpStatus.valueOf(resultBody.getHttpStatus()));
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(resultBody).getBytes(Charset.defaultCharset()));
            // 保存日志
            accessLogService.sendLog(exchange, e);
            return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                DataBufferUtils.release(buffer);
            });
        });
    }
}
