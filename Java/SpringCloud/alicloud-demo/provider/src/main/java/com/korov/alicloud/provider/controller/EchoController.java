package com.korov.alicloud.provider.controller;

import com.korov.alicloud.provider.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RefreshScope 可以自动刷新属性的值
 */
@RefreshScope
@RestController
public class EchoController {
    private static final Logger log = LoggerFactory.getLogger(EchoController.class);

    // 在启动时拿到值之后就不再更新
    private String configName;

    private EchoService echoService;
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    public void setEchoService(EchoService echoService) {
        this.echoService = echoService;
    }

    @Autowired
    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Value("${config.name:default}")
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        // 当动态配置刷新时，会更新到 Environment 中，因此需要手动获取更新之后的值
        // configName = applicationContext.getEnvironment().getProperty("config.name");
        log.info("provider get str:{}, configName:{}, nacosConfigName:{}", string, configName, echoService.getNacosConfigName());
        return String.format("Hello Nacos Discovery %s, configName:%s, nacosConfigName:%s", string, configName, echoService.getNacosConfigName());
    }
}
