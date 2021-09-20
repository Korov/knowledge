#  拉取镜像

```bash
docker pull nginx:1.21.0-alpine

docker run --name nginx -d -p 80:80 nginx:1.21.3-alpine

# 通过host方式启动docker
docker run --network=host --name nginx -d nginx:1.21.3-alpine

# 挂载外部配置文件
docker run --network=host --name nginx -d -v ./nginx.conf:/etc/nginx/nginx.conf:ro nginx:1.21.3-alpine
```

配置文件位置：`/etc/nginx/nginx.conf`

