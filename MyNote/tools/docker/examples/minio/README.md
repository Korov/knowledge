```
docker run -d --name minio -p 9000:9000 -p 9001:9001 -e "MINIO_ROOT_USER=minioadmin" -e "MINIO_ROOT_PASSWORD=minioadmin" minio/minio:RELEASE.2021-09-24T00-24-24Z server /data --console-address ":9001"
```

打开：`localhost:9001`账号密码：`minioadmin:minioadmin`

