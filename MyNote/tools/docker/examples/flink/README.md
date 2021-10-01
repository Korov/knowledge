```
mkdir job_log
mkdir job_rocksdb
mkdir job_savepoints
chmod -R 777 job_log
chmod -R 777 job_rocksdb
chmod -R 777 job_savepoints
rm -rf job_log
rm -rf job_rocksdb
rm -rf job_savepoints


mkdir task_log
mkdir task_rocksdb
mkdir task_savepoints
chmod -R 777 task_log
chmod -R 777 task_rocksdb
chmod -R 777 task_savepoints
rm -rf task_log
rm -rf task_rocksdb
rm -rf task_savepoints

docker-compose up -d
docker-compose stop
docker-compose rm --force
```

 

启动之后登录网址

```
http://localhost:8081
```

docker中的文件路径

1. 日志：/opt/flink/log
2. rocksdb：/opt/flink/rocksdb
3. savepoints：/opt/flink/savepoints
