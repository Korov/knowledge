```bash
# 启动容器
docker run -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME="admin" -e MONGO_INITDB_ROOT_PASSWORD="admin" --name mongo -d mongo:4.4.5;
# 进入容器
docker exec -it mongo /bin/sh;
# 测试容器是否可以使用
mongo --username admin --password --authenticationDatabase admin
use admin;
db.createUser({user:"root",pwd:"root123",roles:[{role:"userAdminAnyDatabase",db:"admin"}]});
exit;
```

