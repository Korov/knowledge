# 安装

```bash
# 创建默认配置文件
docker run --rm influxdb:2.1.1 influxd print-config > config.yml;

docker run -d --name influxdb -p 8086:8086 \
  --volume $PWD:/var/lib/influxdb2 \
  -v $PWD/config.yml:/etc/influxdb2/config.yml \
  -e DOCKER_INFLUXDB_INIT_MODE=setup \
  -e DOCKER_INFLUXDB_INIT_USERNAME=root \
  -e DOCKER_INFLUXDB_INIT_PASSWORD=root1234 \
  -e DOCKER_INFLUXDB_INIT_ORG=my-org \
  -e DOCKER_INFLUXDB_INIT_BUCKET=my-bucket \
  influxdb:2.1.1
  
docker run -d --name influxdb -e DOCKER_INFLUXDB_INIT_USERNAME=root -e DOCKER_INFLUXDB_INIT_PASSWORD=root1234 -p 8086:8086 influxdb:2.1.1
```

