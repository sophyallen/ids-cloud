package com.kaikeba.idscloud.gateway;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.kaikeba.idscloud.gateway.configuration.ApiConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;

/**
 * @author: zhangminchao
 * @date: 2022/04/01
 * @description:
 */
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@ImportAutoConfiguration(value = {ApiConfiguration.class})
public class GateWayAutoConfiguration {

}
