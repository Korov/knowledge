```bash
docker-compose up -d

# 为某个Collection设置分片
docker-compose exec mongo-router mongo
use admin
# 查看分片状态
sh.status()
# 对test Collection数据库分片
db.runCommand({ enablesharding: 'test'})
```

