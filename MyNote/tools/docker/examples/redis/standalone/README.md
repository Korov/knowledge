```bash
docker run --restart always --name redis -p 6379:6379 -v $PWD/data:/data  -d redis:6.2.4-alpine redis-server --appendonly yes;

# 进入容器内部
docker exec -it redis redis-cli
```

