# 介绍

`gRPC`基于`HTTP/2`标准设计，带来诸如双向流、流控、头部压缩、单TCP连接上的多复用请求等待。这些特性使得其在移动设备上表现更好，更省电和节省空间占用。

在 `gRPC `里*客户端*应用可以像调用本地对象一样直接调用另一台不同的机器上*服务端*应用的方法，使得您能够更容易地创建分布式应用和服务。与许多 RPC 系统类似，gRPC 也是基于以下理念：定义一个*服务*，指定其能够被远程调用的方法（包含参数和返回类型）。在服务端实现这个接口，并运行一个 gRPC 服务器来处理客户端调用。在客户端拥有一个*存根*能够像服务端一样的方法。

![https://www.grpc.io/img/grpc_concept_diagram_00.png](picture/grpc_concept_diagram_00.png)

gRPC 默认使用 *protocol buffers*，这是 Google 开源的一套成熟的结构数据序列化机制（当然也可以使用其他数据格式如 JSON）。正如你将在下方例子里所看到的，你用 *proto files* 创建 gRPC 服务，用 protocol buffers 消息类型来定义方法参数和返回类型。
