`org.springframework.http.client.SimpleBufferingClientHttpRequest.executeInternal`

消息在这里执行

NacosServiceRegistry，NacosNamingService

`com.alibaba.nacos.client.naming.remote.gprc.NamingGrpcClientProxy.registerService`

这里会把实例注册到nacos中

com.alibaba.nacos.client.naming.core.HostReactor.UpdateTask  这里定时任务看是否需要更新实例

`com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient.getInstances`

然后会调用`com.alibaba.nacos.client.naming.NacosNamingService.selectInstances(java.lang.String, java.lang.String, java.util.List<java.lang.String>, boolean, boolean)`，

`com.alibaba.nacos.client.naming.net.NamingProxy.getServiceList(int, int, java.lang.String, com.alibaba.nacos.api.selector.AbstractSelector)`

nacos用的spring开发的，nacos-console是启动类，需要添加VM参数`-Dnacos.standalone=true`