package com.sxk.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class GatewayConfig {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String nacosServerAddr;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String nacosNamespace;

    @Value("${nacos.gateway.route.config.data-id}")
    private String nacosRouteDataId;
    @Value("${nacos.gateway.route.config.group}")
    private String nacosRouteGroup;
}
