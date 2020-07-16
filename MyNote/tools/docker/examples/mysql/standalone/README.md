```bash
#执行以下脚本。
docker run --name mysql --restart=always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root123 \
-v `pwd`/data:/var/lib/mysql:rw \
-v `pwd`/mysql-files:/var/lib/mysql-files:rw \
-v `pwd`/log:/var/log/mysql:rw \
-v `pwd`/config/my.cnf:/etc/mysql/my.cnf:rw \
-d mysql:latest;

# 添加远程登录用户
CREATE USER 'liaozesong'@'%' IDENTIFIED WITH mysql_native_password BY 'Lzslov123!';
GRANT ALL PRIVILEGES ON *.* TO 'liaozesong'@'%';
```

将文件挂载到`/docker-entrypoint-initdb.d`中，文件会按照字母顺序执行，支持的文件有`.sh`, `.sql` 和 `.sql.gz`。

