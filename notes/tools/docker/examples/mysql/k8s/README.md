```shell
kubectl apply -f mysql-pv.yaml
kubectl apply -f mysql-deployment.yaml

# 展示相关信息
kubectl describe deployment mysql
# 列出pods
kubectl get pods -l app=mysql
# 查看 PersistentVolumeClaim
kubectl describe pvc mysql-pv-claim
```

```shell
kubectl delete deployment,svc mysql
kubectl delete pvc mysql-pv-claim
kubectl delete pv mysql-pv-volume
```

需要访问外部的30306端口才能链接到数据库



```bash
kubectl apply -f mysql-service.yaml
```

这个是一个只有临时存储文件的MySQL服务