package com.kkb.idscloud.base.service.impl;

import com.kkb.idscloud.base.service.BaseAuthorityService;
import com.kkb.idscloud.common.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class BaseAuthorityServiceImplTest extends BaseTest {
    @Autowired
    private BaseAuthorityService baseAuthorityService;

    @Test
    public void clearInvalidApi() {
        String[] serviceIds = {
                "serviceId",
                "serviceId2"
        };
        for (String serviceId : serviceIds) {
            // 清空无效服务 权限 和 api接口数据
            baseAuthorityService.clearInvalidApi(serviceId, Collections.emptyList());
        }

    }
}
