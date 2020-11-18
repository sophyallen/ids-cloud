package com.kkb.idscloud.gateway.actuator;

import com.kkb.idscloud.common.event.RemoteRefreshRouteEvent;
import com.kkb.idscloud.common.model.ResultBody;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 自定义网关监控端点
 * @author zmc
 */
@RestControllerEndpoint(
        id = "ids"
)
public class ApiEndpoint extends AbstractBusEndpoint {

    public ApiEndpoint(ApplicationEventPublisher context, String id) {
        super(context, id);
    }

    /**
     * 支持灰度发布
     * /actuator/ids/refresh?destination = customers：**
     *
     * @param destination
     */
    @PostMapping("/refresh")
    public ResultBody busRefreshWithDestination(@RequestParam(required = false)  String destination) {
        this.publish(new RemoteRefreshRouteEvent(this, this.getInstanceId(), destination));
        return ResultBody.ok();
    }

    @GetMapping("/test")
    public ResultBody test() {
        return ResultBody.ok();
    }
}
