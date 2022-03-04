此docker是为工作用的。 

配备了如下容器：

- arangodb:3.1.0
- flink:1.9.1-scala_2.11
- mariadb:5.5.62
- zookeeper:3.4.9
- wurstmeister/kafka:2.11-0.11.0.3

```bash
# 启动
docker-compose -f docker-compse.yaml up -d
# 查看各个容器状态
docker-compose -f docker-compse.yaml ps
# 停止
docker-compose -f docker-compse.yaml stop
# 删除
docker-compose -f docker-compse.yaml rm
```

