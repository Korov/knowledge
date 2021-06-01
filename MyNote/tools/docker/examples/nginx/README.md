#  拉取镜像

```bash
docker pull nginx:1.21.0-alpine

docker run --name nginx -d -p 80:80 nginx:1.21.0-alpine
```

配置文件位置：`/etc/nginx/nginx.conf`