使用docker compose实现redis的哨兵模式部署

# 修改IP地址

需要讲docker-compose.yaml中的IP地址修改为自己的宿主机IP

修改sentinel_config中sentinel.conf中的IP地址和端口号

# 复制配置文件

讲sentinel.conf复制三份sentinel1.conf，sentinel2.conf，sentinel3.conf。

# 启动

启动就可以了

```bash
docker-compose -f docker-compose.yaml up -d
```

要使用的话需要连接哨兵的端口
