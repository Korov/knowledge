```bash
# 启动容器
docker run -p 27017:27017 --name mymongodb -v $PWD/db:/data/db -d mongo:4.1.11;
# 进入容器
docker exec -it mymongodb /bin/sh;
# 测试容器是否可以使用
mongo;
use admin;
db.createUser({user:"root",pwd:"root123",roles:[{role:"userAdminAnyDatabase",db:"admin"}]});
exit;
```

