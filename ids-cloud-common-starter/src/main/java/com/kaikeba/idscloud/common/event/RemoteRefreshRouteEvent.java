package com.kaikeba.idscloud.common.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class RemoteRefreshRouteEvent extends RemoteApplicationEvent {

    private RemoteRefreshRouteEvent() {
    }

    public RemoteRefreshRouteEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
