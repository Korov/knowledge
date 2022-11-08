`org.springframework.http.client.SimpleBufferingClientHttpRequest.executeInternal`

消息在这里执行

NacosServiceRegistry，NacosNamingService

`com.alibaba.nacos.client.naming.net.NamingProxy.registerService`

这里会把实例注册到nacos中

com.alibaba.nacos.client.naming.core.HostReactor.UpdateTask  这里定时任务看是否需要更新实例

`com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient.getInstances`

然后会调用`com.alibaba.nacos.client.naming.NacosNamingService.selectInstances(java.lang.String, java.lang.String, java.util.List<java.lang.String>, boolean, boolean)`，

`com.alibaba.nacos.client.naming.net.NamingProxy.getServiceList(int, int, java.lang.String, com.alibaba.nacos.api.selector.AbstractSelector)`

nacos用的spring开发的，nacos-console是启动类

docker中nacos的启动方式`/usr/lib/jvm/java-1.8.0-openjdk/bin/java -Xms1g -Xmx1g -Xmn512m -Dnacos.standalone=true -Dnacos.member.list= -Djava.ext.dirs=/usr/lib/jvm/java-1.8.0-openjdk/jre/lib/ext:/usr/lib/jvm/java-1.8.0-openjdk/lib/ext:/home/nacos/plugins/health:/home/nacos/plugins/cmdb:/home/nacos/plugins/mysql -Xloggc:/home/nacos/logs/nacos_gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M -Dnacos.home=/home/nacos -jar /home/nacos/target/nacos-server.jar --spring.config.additional-location=file:/home/nacos/conf/ --spring.config.name=application --logging.config=/home/nacos/conf/nacos-logback.xml --server.max-http-header-size=524288`