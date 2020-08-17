package com.sxk.gateway.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.sxk.gateway.config.GatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class DynamicRouteServiceImplByNacos {

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;
    @Autowired
    private GatewayConfig gatewayConfig;

    @PostConstruct
    public void init() {
        dynamicRouteByNacosListener(gatewayConfig.getNacosRouteDataId(), gatewayConfig.getNacosRouteGroup());
    }

    /**
     * 监听Nacos Server下发的动态路由配置
     *
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", gatewayConfig.getNacosServerAddr());
            properties.setProperty("namespace", gatewayConfig.getNacosNamespace());
            ConfigService configService = NacosFactory.createConfigService(properties);
            String configInfo = configService.getConfig(dataId, group, 5000);
            log.info("获取网关当前路由配置 gatewayConfig={} configInfo={}", gatewayConfig, configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition definition : definitionList) {
                log.info("update route : {}", definition.toString());
                dynamicRouteService.add(definition);
            }
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("进行网关路由配置更新: {}", configInfo);
                    List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
                    for (RouteDefinition definition : definitionList) {
                        log.info("update route : {}", definition.toString());
                        dynamicRouteService.update(definition);
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("网关动态路由配置出错", e);
        }
    }

}
