package com.kaikeba.idscloud.gateway.controller;


import com.kaikeba.idscloud.common.core.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zmc
 * @date: 2018/11/5 16:33
 * @description:
 */
@RestController
public class IndexController {
    @GetMapping("/")
    public Mono<ResultBody> index() {
        return Mono.just(ResultBody.ok());
    }

    @GetMapping("/index")
    public Mono<ResultBody> index2() {
        return Mono.just(ResultBody.ok());
    }
}
