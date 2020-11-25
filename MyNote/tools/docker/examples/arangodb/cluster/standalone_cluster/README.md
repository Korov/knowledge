# 开启的服务

目前使用官方给的镜像部署，后续改成自己制作的镜像，服务部署，下一步性能测试。

## 单机

端口：8528

## 集群

集群中只有一个agent，一个coordinator，一个dbserver，端口8529

集群中有三个agent，三个coordinator，三个dbserver，端口分别为8530，8531，8532

# 账号密码

全部服务的账号密码相同为：root/root123

# 清理映射文件

```bash
sudo rm -r ./arango-cluster-server1/agency ./arango-cluster-server1/coordinator ./arango-cluster-server1/dbserver ./arango-cluster-server2/agency ./arango-cluster-server2/coordinator ./arango-cluster-server2/dbserver ./arango-cluster-server3/agency ./arango-cluster-server3/coordinator ./arango-cluster-server3/dbserver ./arango-cluster-single/agency ./arango-cluster-single/coordinator ./arango-cluster-single/dbserver ./arango-standalone/dbserver
```

#  性能测试

```
https://github.com/Korov/demo-arango
```

