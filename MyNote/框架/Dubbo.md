Dubbo是一个高性能，轻量级，基于Java的RPC框架。Dubbo包含三个主要功能：基于接口的远程调用，容错和负载均衡，自动服务注册和发现。

![image-20191127182551527](picture\image-20191127182551527.png)

Dubbo的底层是依赖Spring的，所以配置信息和Spring类似，并且需要使用Spring的Context来控制bean。



① 上面的文件其实就是类似 spring 的配置文件，而且，dubbo 底层就是 spring。
② **节点：dubbo:application**
就是整个项目在分布式架构中的唯一名称，可以在 `name` 属性中配置，另外还可以配置 `owner` 字段，表示属于谁。
下面的参数是可以不配置的，这里配置是因为出现了端口的冲突，所以配置。
③ **节点：dubbo:monitor**
监控中心配置， 用于配置连接监控中心相关信息，可以不配置，不是必须的参数。
④ **节点：dubbo:registry**
配置注册中心的信息，比如，这里我们可以配置 zookeeper 作为我们的注册中心。`address` 是注册中心的地址，这里我们配置的是 `N/A` 表示由 dubbo 自动分配地址。或者说是一种直连的方式，不通过注册中心。
⑤ **节点：dubbo:protocol**
服务发布的时候 dubbo 依赖什么协议，可以配置 dubbo、webserovice、Thrift、Hessain、http等协议。
⑥ **节点：dubbo:service**
这个节点就是我们的重点了，当我们服务发布的时候，我们就是通过这个配置将我们的服务发布出去的。`interface` 是接口的包路径，`ref` 是第 ⑦ 点配置的接口的 bean。
⑦ 最后，我们需要像配置 spring 的接口一样，配置接口的 bean。 