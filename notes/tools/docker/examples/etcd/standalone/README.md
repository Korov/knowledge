```bash
# 无密码模式
docker run -d --name etcd --publish 2379:2379 --publish 2380:2380 --env ALLOW_NONE_AUTHENTICATION=yes bitnami/etcd:3.5.1

# 有密码
docker run -d --name etcd --publish 2379:2379 --publish 2380:2380 --env ETCD_ROOT_PASSWORD=etcd bitnami/etcd:3.5.1
```

