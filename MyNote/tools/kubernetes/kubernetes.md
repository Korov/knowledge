# 本质

维护一种状态，当你定义了yaml配置文件，你就定义了一些状态，当你把这个配置文件放到任何机器上的时候这个机器的状态应该和之前的那个机器完全一样，这就是k8s维护的状态。

# 安装

```
172.16.150.137 master
172.16.150.138 node1
172.16.150.139 node2
```



##  禁用开机启动防火墙

```
systemctl disable firewalld
```

### **永久禁用SELinux**

```
sed -i 's/SELINUX=.*/SELINUX=disabled/' /etc/sysconfig/selinux
```

## 关闭系统swap

Kubernetes 1.8开始要求关闭系统的Swap，如果不关闭，默认配置下kubelet将无法启动。

```
sed -i 's/.*swap.*/#&/' /etc/fstab
```

重启一下系统

## 安装docker

```
yum install wget container-selinux -y

wget https://download.docker.com/linux/centos/7/x86_64/stable/Packages/containerd.io-1.2.6-3.3.el7.x86_64.rpm

yum erase runc -y

rpm -ivh containerd.io-1.2.6-3.3.el7.x86_64.rpm

update-alternatives --set iptables /usr/sbin/iptables-legacy

yum install -y yum-utils device-mapper-persistent-data lvm2 && yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo && yum makecache && yum -y install docker-ce -y && systemctl enable docker.service && systemctl start docker

vim /etc/docker/daemon.json

{
    "registry-mirrors": ["https://ntb32z44.mirror.aliyuncs.com"],
     "exec-opts":["native.cgroupdriver=systemd"]
}

```

## 安装kubeadm和kubelet

```
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
```

**安装最新版kubeadm**

```
yum makecache
yum install -y kubelet kubeadm kubectl ipvsadm
```

**配置内核参数**

```
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
vm.swappiness=0
EOF

# sysctl --system
# modprobe br_netfilter
# sysctl -p /etc/sysctl.d/k8s.conf

加载ipvs相关内核模块
如果重新开机，需要重新加载（可以写在 /etc/rc.local 中开机自动加载）
# modprobe ip_vs
# modprobe ip_vs_rr
# modprobe ip_vs_wrr
# modprobe ip_vs_sh

# modprobe nf_conntrack # kernel大4.19时使用这个
# modprobe nf_conntrack_ipv4

查看是否加载成功
# lsmod | grep ip_vs
```

## 获取镜像

- 三个节点都要下载
- 注意下载时把版本号修改到官方最新版，即使下载了最新版也可能版本不对应，需要按报错提示下载
- 每次部署都会有版本更新，具体版本要求，运行初始化过程失败会有版本提示
- kubeadm的版本和镜像的版本必须是对应的

**用命令查看版本当前kubeadm对应的k8s镜像版本**

```
[root@localhost ~]# kubeadm config images list  
k8s.gcr.io/kube-apiserver:v1.20.4
k8s.gcr.io/kube-controller-manager:v1.20.4
k8s.gcr.io/kube-scheduler:v1.20.4
k8s.gcr.io/kube-proxy:v1.20.4
k8s.gcr.io/pause:3.2
k8s.gcr.io/etcd:3.4.13-0
k8s.gcr.io/coredns:1.7.0
```

**使用下面的方法在aliyun拉取相应的镜像并重新打标**

```
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-apiserver:v1.20.4
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-apiserver:v1.20.4 k8s.gcr.io/kube-apiserver:v1.20.4
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-controller-manager:v1.20.4
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-controller-manager:v1.20.4 k8s.gcr.io/kube-controller-manager:v1.20.4
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-scheduler:v1.20.4
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-scheduler:v1.20.4 k8s.gcr.io/kube-scheduler:v1.20.4
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy:v1.20.4
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy:v1.20.4 k8s.gcr.io/kube-proxy:v1.20.4
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2 k8s.gcr.io/pause:3.2
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/etcd:3.4.13-0
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/etcd:3.4.13-0 k8s.gcr.io/etcd:3.4.13-0
docker pull coredns/coredns:1.7.0
docker tag coredns/coredns:1.7.0 k8s.gcr.io/coredns:1.7.0
```

## 所有节点启动kubelet

**配置kubelet使用国内pause镜像**

```
DOCKER_CGROUPS=$(docker info | grep 'Cgroup' | cut -d' ' -f4)
DOCKER_CGROUPS=systemd
# echo $DOCKER_CGROUPS
cgroupfs
```

配置kubelet的cgroups

```
cat >/etc/sysconfig/kubelet<<EOF
KUBELET_EXTRA_ARGS="--cgroup-driver=$DOCKER_CGROUPS --pod-infra-container-image=k8s.gcr.io/pause:3.2"
EOF
```

启动

```
# systemctl daemon-reload
# systemctl enable kubelet && systemctl start kubelet
```

## 初始化集群

### 在master节点进行初始化操作

```
kubeadm init --kubernetes-version=v1.20.4 --pod-network-cidr=10.244.0.0/16 --apiserver-advertise-address=172.16.150.137 --ignore-preflight-errors=Swap --ignore-preflight-errors=all
```

### 在master节点配置使用kubectl

```
# rm -rf $HOME/.kube
# mkdir -p $HOME/.kube
# cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
# chown $(id -u):$(id -g) $HOME/.kube/config
```

### 查看node节点

```
kubectl get nodes
```

### 配置网络插件

### **master节点下载yaml配置文件**

```
# cd ~ && mkdir flannel && cd flannel
# curl -O https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```

### **修改配置文件kube-flannel.yml**

**指定启动网卡**

flanneld启动参数加上--iface=

```
containers:
  - name: kube-flannel
    image: registry.cn-shanghai.aliyuncs.com/gcr-k8s/flannel:v0.10.0-amd64
    command:
    - /opt/bin/flanneld

    args:
    - --ip-masq
    - --kube-subnet-mgr
    - --iface=ens33
    - --iface=eth0
```

--iface=ens33 的值，是你当前的网卡,或者可以指定多网卡

启动

```
# kubectl apply -f ~/flannel/kube-flannel.yml
```

查看

```
# kubectl get pods --namespace kube-system

# kubectl get service

# kubectl get svc --namespace kube-system
```

### 配置所有node节点加入集群

在所有node节点操作，此命令为初始化master成功后返回的结果

```
# kubeadm join 192.168.1.200:6443 --token ccxrk8.myui0xu4syp99gxu --discovery-token-ca-cert-hash sha256:e3c90ace969aa4d62143e7da6202f548662866dfe33c140095b020031bff2986
```



k8s需要在阿里云镜像网站设置镜像。并安装kubelet,kubeadm,kubectl

```bash
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
setenforce 0
yum install -y kubelet kubeadm kubectl
systemctl enable kubelet && systemctl start kubelet
```

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