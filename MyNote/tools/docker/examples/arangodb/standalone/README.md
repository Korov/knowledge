 

```
docker run -e ARANGO_ROOT_PASSWORD=root123 -p 18529:8529 --name arango -d arangodb/arangodb:3.7.2

docker run -e ARANGO_ROOT_PASSWORD=root123 -p 8529:8529 --name arango -d arangodb/arangodb:3.6.6
```

在浏览器中登录`http://localhost:8529`

用户名/密码：root/root123