package com.kaikeba.idscloud.flux.fallback;

import com.kaikeba.idscloud.common.core.constants.ErrorCodeEnum;
import com.kaikeba.idscloud.common.core.model.ResultBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 响应超时熔断处理器
 *
 * @author zmc
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<ResultBody> fallback() {
        return Mono.just(ResultBody.failed().code(ErrorCodeEnum.SERVER_ERROR_B0100.getCode()).message("访问超时，请稍后再试!"));
    }
}
