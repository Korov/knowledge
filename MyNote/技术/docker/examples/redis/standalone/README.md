```bash
docker run -p 6379:6379 -v $PWD/data:/data  -d redis:5.0.5 redis-server --appendonly yes;
```

