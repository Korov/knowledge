```
docker run -d --name minio -p 9000:9000 -p 9001:9001 -e "MINIO_ROOT_USER=minioadmin" -e "MINIO_ROOT_PASSWORD=minioadmin" minio/minio:RELEASE.2021-09-24T00-24-24Z server /data --console-address ":9001"
```

打开：`localhost:9001`账号密码：`minioadmin:minioadmin`

```
docker run -d --name minio \
    --publish 9000:9000 \
    --publish 9001:9001 \
    --env MINIO_ACCESS_KEY="minio-access-key" \
    --env MINIO_SECRET_KEY="minio-secret-key" \
    --env MINIO_DEFAULT_BUCKETS='my-first-bucket:policy,my-second-bucket' \
    --env MINIO_HTTP_TRACE=/opt/bitnami/minio/log/minio.log \
    --volume /path/to/minio-persistence:/data \
    bitnami/minio:2022.2.26
```

