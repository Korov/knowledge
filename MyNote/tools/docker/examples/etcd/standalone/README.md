```
docker run -d --name etcd --publish 2379:2379 --publish 2380:2380 --env ALLOW_NONE_AUTHENTICATION=yes bitnami/etcd:3.5.1

docker run -d --name etcd --publish 2379:2379 --publish 2380:2380 --env ETCD_ROOT_PASSWORD=etcd bitnami/etcd:3.5.1
```

