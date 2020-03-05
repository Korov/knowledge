# 项目介绍

此版本是v2版本，基于v1版本，主要增加的是注册中心和网关。

此处网关或拦截用户请求，并将用户的请求解析成明文放到http的header中转发给其他微服务，其他微服务获取到token之后就可以直接用，不用再次解析了。

# 测试

## 获取token

`http://localhost:53010/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=password&username=zhangsan&password=123`