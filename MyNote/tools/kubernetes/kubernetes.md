# 本质

维护一种状态，当你定义了yaml配置文件，你就定义了一些状态，当你把这个配置文件放到任何机器上的时候这个机器的状态应该和之前的那个机器完全一样，这就是k8s维护的状态。

# [安装](https://kuboard.cn/install/install-k8s.html#%E6%96%87%E6%A1%A3%E7%89%B9%E7%82%B9)

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

sysctl --system
modprobe br_netfilter
sysctl -p /etc/sysctl.d/k8s.conf

加载ipvs相关内核模块
如果重新开机，需要重新加载（可以写在 /etc/rc.local 中开机自动加载）
modprobe ip_vs
modprobe ip_vs_rr
modprobe ip_vs_wrr
modprobe ip_vs_sh

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

## 固定ip地址

```
vim /etc/sysconfig/network-scripts/ifcfg-ens33


TYPE=Ethernet
BOOTPROTO=static ##静态ip
PROXY_METHOD=none
BROWSER_ONLY=no
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=no
NAME=ens33
#UUID=57972749-df25-4f04-8fbf-a3744a0b23e8
DEVICE=ens33
ONBOOT=yes #系统启动时开启
IPADDR=172.16.150.130 # 静态ip地址
NETMASK=255.255.255.0 # 子网掩码
GATEWAY=172.16.150.2 # 第3步中查看的网关ip
PREFIX=24
UUID=c96bc909-188e-ec64-3a96-6a90982b08ad
HWADDR=00:0C:29:95:3E:AB


# 重启
nmcli c reload ens33
nmcli c up ens33
```



## ip地址

```
172.16.150.130 master
172.16.150.131 node1
172.16.150.132 node2
```

## 初始化集群

### 在master节点进行初始化操作

内存要够大，cpu要够多，不然超时

```
kubeadm init --kubernetes-version=v1.20.4 --pod-network-cidr=10.244.0.0/16 --apiserver-advertise-address=172.16.150.137 --service-cidr=10.96.0.0/16 --ignore-preflight-errors=Swap --v=5
```

### 在master节点配置使用kubectl

```
rm -rf $HOME/.kube
mkdir -p $HOME/.kube
cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
chown $(id -u):$(id -g) $HOME/.kube/config
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

---
apiVersion: policy/v1beta1
kind: PodSecurityPolicy
metadata:
  name: psp.flannel.unprivileged
  annotations:
    seccomp.security.alpha.kubernetes.io/allowedProfileNames: docker/default
    seccomp.security.alpha.kubernetes.io/defaultProfileName: docker/default
    apparmor.security.beta.kubernetes.io/allowedProfileNames: runtime/default
    apparmor.security.beta.kubernetes.io/defaultProfileName: runtime/default
spec:
  privileged: false
  volumes:
  - configMap
  - secret
  - emptyDir
  - hostPath
  allowedHostPaths:
  - pathPrefix: "/etc/cni/net.d"
  - pathPrefix: "/etc/kube-flannel"
  - pathPrefix: "/run/flannel"
  readOnlyRootFilesystem: false
  # Users and groups
  runAsUser:
    rule: RunAsAny
  supplementalGroups:
    rule: RunAsAny
  fsGroup:
    rule: RunAsAny
  # Privilege Escalation
  allowPrivilegeEscalation: false
  defaultAllowPrivilegeEscalation: false
  # Capabilities
  allowedCapabilities: ['NET_ADMIN', 'NET_RAW']
  defaultAddCapabilities: []
  requiredDropCapabilities: []
  # Host namespaces
  hostPID: false
  hostIPC: false
  hostNetwork: true
  hostPorts:
  - min: 0
    max: 65535
  # SELinux
  seLinux:
    # SELinux is unused in CaaSP
    rule: 'RunAsAny'
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: flannel
rules:
- apiGroups: ['extensions']
  resources: ['podsecuritypolicies']
  verbs: ['use']
  resourceNames: ['psp.flannel.unprivileged']
- apiGroups:
  - ""
  resources:
  - pods
  verbs:
  - get
- apiGroups:
  - ""
  resources:
  - nodes
  verbs:
  - list
  - watch
- apiGroups:
  - ""
  resources:
  - nodes/status
  verbs:
  - patch
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: flannel
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: flannel
subjects:
- kind: ServiceAccount
  name: flannel
  namespace: kube-system
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: flannel
  namespace: kube-system
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: kube-flannel-cfg
  namespace: kube-system
  labels:
    tier: node
    app: flannel
data:
  cni-conf.json: |
    {
      "name": "cbr0",
      "cniVersion": "0.3.1",
      "plugins": [
        {
          "type": "flannel",
          "delegate": {
            "hairpinMode": true,
            "isDefaultGateway": true
          }
        },
        {
          "type": "portmap",
          "capabilities": {
            "portMappings": true
          }
        }
      ]
    }
  net-conf.json: |
    {
      "Network": "10.244.0.0/16",
      "Backend": {
        "Type": "vxlan"
      }
    }
---
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: kube-flannel-ds
  namespace: kube-system
  labels:
    tier: node
    app: flannel
spec:
  selector:
    matchLabels:
      app: flannel
  template:
    metadata:
      labels:
        tier: node
        app: flannel
    spec:
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: kubernetes.io/os
                operator: In
                values:
                - linux
      hostNetwork: true
      priorityClassName: system-node-critical
      tolerations:
      - operator: Exists
        effect: NoSchedule
      serviceAccountName: flannel
      initContainers:
      - name: install-cni
        image: quay.io/coreos/flannel:v0.13.1-rc2
        command:
        - cp
        args:
        - -f
        - /etc/kube-flannel/cni-conf.json
        - /etc/cni/net.d/10-flannel.conflist
        volumeMounts:
        - name: cni
          mountPath: /etc/cni/net.d
        - name: flannel-cfg
          mountPath: /etc/kube-flannel/
      containers:
      - name: kube-flannel
        image: quay.io/coreos/flannel:v0.13.1-rc2
        command:
        - /opt/bin/flanneld
        args:
        - --ip-masq
        - --kube-subnet-mgr
        - --iface=ens33
        - --iface=eth0
        resources:
          requests:
            cpu: "100m"
            memory: "50Mi"
          limits:
            cpu: "100m"
            memory: "50Mi"
        securityContext:
          privileged: false
          capabilities:
            add: ["NET_ADMIN", "NET_RAW"]
        env:
        - name: POD_NAME
          valueFrom:
            fieldRef:
              fieldPath: metadata.name
        - name: POD_NAMESPACE
          valueFrom:
            fieldRef:
              fieldPath: metadata.namespace
        volumeMounts:
        - name: run
          mountPath: /run/flannel
        - name: flannel-cfg
          mountPath: /etc/kube-flannel/
      volumes:
      - name: run
        hostPath:
          path: /run/flannel
      - name: cni
        hostPath:
          path: /etc/cni/net.d
      - name: flannel-cfg
        configMap:
          name: kube-flannel-cfg
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
# kubectl apply -f ~/kube-flannel.yml
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
# kubeadm join 172.16.150.129:6443 --token 5vrr9i.oaumys7kboldkkdw \
    --discovery-token-ca-cert-hash sha256:68cabf3f3e25ec2d6294c3eb2d6c9b75ee1cf4a1f99ca0fcceacc61aef63d902
```

### 查看集群状态

```
[root@localhost ~]# kubectl get pods -n kube-system
NAME                                            READY   STATUS    RESTARTS   AGE
coredns-74ff55c5b-452dx                         1/1     Running   0          27m
coredns-74ff55c5b-cw25w                         1/1     Running   0          27m
etcd-localhost.localdomain                      1/1     Running   0          27m
kube-apiserver-localhost.localdomain            1/1     Running   0          27m
kube-controller-manager-localhost.localdomain   1/1     Running   0          27m
kube-flannel-ds-bkw2m                           1/1     Running   0          109s
kube-flannel-ds-t6kwp                           1/1     Running   0          4m14s
kube-flannel-ds-wn66j                           1/1     Running   0          2m15s
kube-proxy-8xgct                                1/1     Running   0          27m
kube-proxy-bj2g4                                1/1     Running   0          109s
kube-proxy-sqcqq                                1/1     Running   0          2m15s
kube-scheduler-localhost.localdomain            1/1     Running   0          27m


[root@localhost ~]# kubectl get nodes
NAME                    STATUS   ROLES                  AGE   VERSION
localhost.localdomain   Ready    control-plane,master   26m   v1.20.4
node1                   Ready    <none>                 61s   v1.20.4
node2                   Ready    <none>                 35s   v1.20.4

# 子节点查看集群状态
kubectl --kubeconfig=/etc/kubernetes/kubelet.conf get nodes
```

