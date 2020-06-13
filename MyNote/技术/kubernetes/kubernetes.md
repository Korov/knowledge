 k8s需要在阿里云镜像网站设置镜像。

kubelet,kubeadm,kubectl

systemctl enable kubelet

systemctl start kubelet

复制出三个虚拟机同时运行，一个master，node1,node2.

设置主机名

```
vim /etc/hostname
```

设置host

```
vim /etc/hosts
```

生成配置文件：

```
kubeadm config print init-defaults ClusterConfiguration > kubeadm.conf
```

需要配置kubeadm.conf中的内容：

设置镜像地址：registry.aliyuncs.com/google_containers

设置advertiseAddress为master的ip地址。

添加子网络，在networking节点下添加：podSubnet: 10.244.0.0/16



还需要下载其他工具：kubeadm config images list --config kubeadm.conf

kubeadm config images pull --config ./kubeadm.conf

kubeadm init --config ./kubeadm.conf

> vim /etc/fstab  注释掉swap永久关闭swap



需要在所有k8s的主机上执行一下命令

```
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

添加node：

```
kubeadm join 172.16.193.132:6443 --token abcdef.0123456789abcdef \
    --discovery-token-ca-cert-hash sha256:7fdb6996e3618f450082e220692babda7518dab594093ed86686ef048623f602 
```



```
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/k8s-manifests/kube-flannel.yml
```

需要确保net-conf.json中的network与上面设置的子网络相同。



查看node：kubectl get nodes