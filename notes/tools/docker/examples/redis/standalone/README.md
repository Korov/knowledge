```bash
docker run --restart always --name redis -p 6379:6379 -v $PWD/data:/data  -d redis:6.2.5-alpine redis-server --appendonly yes --requirepass redis;

# 进入容器内部
docker exec -it redis redis-cli
# 带有密码redis
redis-cli -h 127.0.0.1 -p 6379 -a redis ping
```

