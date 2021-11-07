```bash
docker pull nacos/nacos-server

docker run --env MODE=standalone --name nacos -d -p 8848:8848 -p 9848:9848 nacos/nacos-server:2.0.3-slim
```

安装完成后访问localhost:8848/nacos/index.html

默认账号密码是nacos/nacos

