Kubernetes中的大部分概念如Node,Pod,Replication Controller,Service等都可以被看作一种资源对象，几乎所有资源对象都可以通过Kubernetes提供的kubectl工具执行增删改查等操作并将其保存在etcd中持久化存储。从这个角度来看，Kubernetes其实是一个高度自动化的资源控制系统，他通过跟踪对比etcd库里保存的“资源期望状态”与当前环境中的“实际资源状态”的差异来实现自动控制和自动纠错的高级功能。

Kubernetes平台采用了**核心+外围扩展**的设计思路，在保持平台核心稳定的同时具备持续演进升级的优势。Kubernetes大部分常见的核心资源对象都归属于 `v1` 这个核心 `API` ，比如 `Node,Pod,Service,Endpoints,Namespace,RC,PersistenVolumn` 等。在 `1.9` 版本之后引入了 `apps/v1` 这个正式的扩展 `API` 组。

### Master

Kubernetes里的Master指的是集群控制节点，在每个Kubernetes集群里都需要有一个Master来负责整个集群的管理和控制，基本上Kubernetes的所有控制命令都发给它，它负责具体的执行过程。Master通常会占据一个独立的服务器（高可用部署建议用3台服务器）。

在Master上运行这以下关键进程：

1.  `Kubernetes API Server(kube-apiserver)`:提供了HTTP Rest接口的关键服务进程，是Kubernetes里所有资源的增删改查等操作的唯一入口，也是集群控制的入口进程。
    
2.  `Kubernetes Controller Manager(kube-controller-manager)`:Kubernetes里所有资源对象的自动化控制中心，可以将其理解为资源对象的大总管
    
3.  `Kubernetes Scheduler(kube-scheduler)`:负责资源调度（Pod调度）的进程
    

另外，在Master上通常还需要部署etcd服务，因为Kubernetes里的所有资源对象的数据都被保存在etcd中。

### Node

除了Master，Kubernetes集群中的其他机器被称为Node。Node可以是一台物理机，也可以是一台虚拟机。Node是Kubernetes集群中的工作负载节点，每个Node都会被Master分配一些工作负载（Docker容器），当某个Node宕机时，其上的工作负载会被Master自动转移到其他节点上。

每个Node上都运行着以下关键程序

1.  kubelet:负责Pod对应的容器的创建、启停等任务，同时与Master密切协作，实现集群管理的基本功能
    
2.  kube-proxy:实现Kubernetes Service的通信与负载均衡机制的重要组件
    
3.  Docker Engine(docker):Docker引擎，负责本机的容器的创建和管理工作。
    

Node可已在运行期间动态增加到Kubernetes集群中，前提是这个节点上已经正确安装，配置和启动了上述关键进程，在默认情况下kublet会向Master注册自己，这也是Kubernetes推荐的Node管理方式。一旦Node被纳入集群管理范围，kubelet进程就会定时向Master回报自身的情报，例如操作系统，docker版本，机器的cpu和内存情况，以及当前有哪些pod在运行等，这样Master就可以获知每个node的资源使用情况，并实现高效均衡的资源调度策略。

### Pod

![pod](http://korov.myqnapcloud.cn:19000/images/pod.png)

每个pod都有一个特殊的被称为根容器的**Pause**容器。**Pause**容器对应的镜像属于kubernetes平台的一部分，除了pause容器，每个pod还包含一个或多个紧密相关的用户业务容器。

pause容器作为pod的根容器，它的状态代表整个容器组的状态，pod中的多个业务容器共享pause容器的ip，共享pause容器挂接的volume，可以简化密切关联的业务容器之间的通信问题，也很好的解决了他们之间的文件共享问题。

kubernetes为每个pod都分配了唯一的ip地址（pod ip），一个pod中的多个容器共享pod ip，一个pod里的容器可以与另外主机上的pod容器直接通信。

pod有两种类型，普通的pod及静态pod。静态pod并没有被存放在kubernetes的etcd存储里，而是被存放在某个具体的node上的一个具体的文件中，并且只在此node上启动，运行。普通的pod一旦被创建，就会被放入etcd中存储，随后会被kubernetes master调度到某个具体的node上并进行绑定，随后该pod被对应的node上的kubelet进程实例化成一组相关的docker容器并启动。默认情况下，pod中的某个容器停止时，kubernetes会自动检测到这个问题并且重新启动这个pod，如果pod所在的node宕机，就会将这个node上的所有pod重新调度到其他节点上。

![pod node](http://korov.myqnapcloud.cn:19000/images/pod-node.png)

```
# 核心api v1
apiVersion: v1
# 这是一个pod的定义
kind: Pod
metadata:
  name: myweb
  labels:
    name: myweb
# pod里所包含的容器组的定义在spec中声明
spec:
  containers:
  - name: myweb
    image: kubeguide/tomcat-app:v1
    # 资源配额限定
    resources:
      requests:
        memory: "64Mi"
        cpu: "250m"
      limits:
        memory: "64Mi"
        cpu: "250m"
    ports:
    - containerPort: 8080
    env:
    - name: MYSQL_SERVICE_HOST
      value: 'mysql'
    - name: MYSQL_SERVICE_PORT
      value: '3306'
```

<table><tbody><tr><td><i title="Note"></i></td><td>requests表示该资源的最小申请量，系统必须满足要求，limits表示该资源最大允许使用的量，不能被突破，当容器试图使用超过这个量的资源时，可能会被kubernetes杀掉重启。cpu是相对值，通常一个容器的cpu配额被定义成100-300m，即0.1-0.3个cpu。memory就是内存的字节数。</td></tr></tbody></table>

### Event

Event是一个事件的记录，记录了事件的最早产生时间，最后重现时间，重复次数，发起者，类型，以及导致此事件的原因等众多信息。Event通常会被关联到某个具体的资源对象上，是排故障的重要参考信息， `kubectl describe pod …` 来查看具体pod的event信息

### Label

一个label是一个key=value的键值对，key和value都由用户自己指定。label可以被附加到各种资源对象上，例如node，pod，service，rc等，一个资源对象可以定义任意数量的label，同一个label可以被添加到任意数量的资源对象上，label通常在资源对象定义时确定，也可以在对象创建后动态添加删除。

我们可以通过label selector（标签选择器）查询和筛选拥有某些label的资源对象。

### rplication controller

简称RC，它定义了一个期望的场景，即声明某种pod的副本数量在任意时刻都符合某个预期值，其定义包括如下几个部分：

1.  pod期待的副本数量
    
2.  用于筛选目标pod的lable selector
    
3.  当pod的副本数量小于预期数量的时候，用于创建新pod的pod模板
    

```
aptVersion: v1
kind: ReplicationController
metadata:
  name: frontend
spec:
  replicas: 1
  selector:
    tier: frontend
  template:
    metadata:
      labels:
        app: app-demo
        tier: frontend
    spec:
      containers:
      - name: tomcat-demo
        image: tomcat
        imagePullPolicy: IfNotPresent
        env:
        - name: GET_HOSTS_FROM
          value: dns
        ports:
        - containerPort: 80
```

当我们定义了一个RC并将其提交到kubernetes集群中后，master上的controller manager组件就得到通知，定期巡检系统中当前存活的目标pod，并确保目标pod实例的数量刚好等于rc的期望值，如果有过多的pod副本在运行，系统就会停掉一些pod，否则系统会再自动创建一些pod。

kubernetes 1.2中将rplication controller更新为replica set，RS支持集合的label selector。

### Deployment

Deployment内部使用Replica Set来实现目的，无论从Deployment的作用与目的，YAML定义，还是从它的具体命令操作来看，我们都可以把它看作RC的一次升级。

```
aptVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
spec:
  replicas: 1
  selector:
    matchLabels:
      tier: frontend
    matchExpressions:
      - {key: tier, operator: In, vlaues:[frontend]}
  template:
    metadata:
      labels:
        app: app-demo
        tier: frontend
    spec:
      containers:
      - name: tomcat-demo
        image: tomcat
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 80
```

### Horizontal Pod Autoscaler

HPA与之前的RC、Deployment一样，也属于Kubernetes资源对象。通过追踪分析指定RC控制的所有目标Pod的负载变化情况，来确定是否需要有针对性的调整目标Pod的副本数量，当前HPA有以下两种方式作为Pod负载的度量指标：

1.  CPUUtilizationPercentage
    
2.  应用程序自定义的度量指标，比如服务在每秒内的相应请求数（TPS或QPS）
    

CPUUtilizationPercentage是一个算数平均值，即目标Pod所有副本自身的CPU利用率的平均值。一个Pod自身的CPU利用率是该Pod当前CPU的使用量除以它的Pod Request的值，比如定义一个Pod的Pod Request为0.4，而当前Pod的CPU使用量为0.2，则他的CPU使用率为50%。如果某一时刻CPUUtilizationPercentage的值超过了80%，则意味着当前Pod副本数量很可能不足以支撑接下来更多的请求，需要进行动态扩容，而在请求高分时段过去后，Pod的CPU利用率又会降下来，此时对应的Pod副本数量应该自动减少到一个合理的水平。如果目标Pod没有定义Pod Request的值，则无法使用CPUUtilizationPercentage实现Pod横向自动扩容。

### StatefulSet

Pod的管理对象RC、Deployment、DaemonSet和Job都面向无状态的服务。但现实中有很多服务是有状态的，特别是一些复杂的中间件集群，例如MySQL集群，这些应用集群有4个共同点：

1.  每个节点都有固定的身份ID，通过这个ID，集群中的成员可以相互发现并通信
    
2.  集群的规模比较固定，集群规模不能随意变动
    
3.  集群中每个节点都是有状态的，通常会持久化数据到永久存储中
    
4.  如果磁盘损坏，则集群里的某个节点无法正常运行，集群功能受损
    

StatefulSet有如下特性：

1.  StatefulSet里的每个Pod都有稳定、唯一的网络标识，可以用来发现集群内的其他成员。假设StatefulSet的名称为kafka，那么第一个Pod叫kafka-0，第2个叫kafka-1
    
2.  StatefulSet控制的Pod副本的起停顺序是受控制的，操作第n个Pod时，前n-1个Pod已经是运行且准备好的状态
    
3.  StatefulSet里的Pod采用稳定的持久化存储卷，通过PV或PVC来实现，删除Pod时默认不会删除与StatefulSet相关的存储卷
    

StatefulSet除了要与PV卷捆绑使用以存储Pod的数据状态，还要与Headless Service配合使用，即在每个StatefulSet定义中都要声明它属于那个Headless Service，Headless Service没有Cluster IP，如果解析Headless Service的DNS域名，则返回的是该Service对应的全局Pod的Endpoint列表。StatefulSet在Headless Service的基础上又为StatefulSet控制的每个Pod实例都创建了一个DNS域名，这个域名的格式为： `$(podname).$(headless service name)`。比如一个3节点的Kafka的StatefulSet集群对应的Headless Service的名称为kafka，StatefulSet的名称为kafka，则StatefulSet里的3个Pod的DNS名称分别为kafka-0.kafka、kafka-1.kafka、kafka-2.kafka，这些DNS名称可以直接在集群的配置文件中固定下来

### Service

kubernetes里的每个Service其实就是我们经常提起的微服务架构中的一个微服务。

![Snipaste 2021 11 20 11 29 18](http://korov.myqnapcloud.cn:19000/images/Snipaste_2021-11-20_11-29-18.png)

Service定义了一个微服务的访问入口地址，前端的应用Pod通过这个入口地址访问其背后的一组由Pod副本组成的集群实例，Service与其后端Pod副本集群之间则是通过Label Selector来实现无缝对接的。RC的作用实际上是保证Service的服务能力和服务质量始终符合预期标准。

每个Node上会有一个kube-proxy进程，本质是一个智能的软件负载均衡器，负责把对Service的请求转发到后端的某个Pod实例上，并在内部实现服务的负载均衡与会话保持机制。但是Kubernetes发明了一种很巧妙的设计：Service没有共用一个负载均衡器的IP地址，每个Service都被分配了一个全局唯一的虚拟IP地址，这个虚拟IP被成为Cluster IP，这样一来，每个服务就变成了具备唯一IP地址的通信节点，服务调用就变成了最基础的TCP网络通信问题。

当一个Pod销毁和重新创建的时候Pod的IP地址与之前旧Pod不同，而Service一旦被创建，Kubernetes就会自动为它分配一个可用的Cluster IP，而且在Service的整个生命周期内，它的Cluster IP不会发生改变，但是Kubernetes用Service的Name与Service的Cluster IP地址做了一个DNS域名映射，解决了IP地址变更的问题。

tomcat-server.yaml

```
apiVersion: v1
kind: Service
metadata:
  name: tomcat-service
spec:
  ports:
  - port: 8080
  selector:
    tier: frontend
```

上述内容定义了一个名为tomcat-service的Service，它的服务端口为8080，拥有 `tier: frontend` 的所有Pod实例都属于它，运行以下命令进行创建： `kubectl create -f tomcat-server.yaml`

很多服务都存在多个端口的问题，通常一个端口提供业务服务，另外一个端口提供管理服务，Service支持多个Endpoint，在存在多个Endpoint的情况下，要求每个Endpoint都定义一个名称来区分。例如

```
apiVersion: v1
kind: Service
metadata:
  name: tomcat-service
spec:
  ports:
  - port:8080
    name: service-port
  -port: 8005
    name: shutdown-port
  selector:
    tier: frontend
```

#### 外部系统访问Service的问题

为了更深入的理解和掌握Kubernetes，我们需要弄明白Kubernetes里的3种IP：

1.  Node IP：Node的IP地址
    
2.  Pod IP：Pod的IP地址
    
3.  Cluster IP：Service的IP地址
    

首先，Node IP是Kubernetes集群种每个节点的物理网卡的IP地址，是一个真实存在的物理网络，所有属于这个网络的服务器都能通过这个网络直接通信，不管其中是否有部分节点不属于这个kubernetes集群。这也表明在kubernetes集群之外的节点访问kubernetes集群之内的某个节点或者TCP/IP服务时，都必须通过Node IP通信。

Pod IP是每个Pod的IP地址，他是Docker Engine根据docker0网桥的IP地址段进行分配的，通常是一个虚拟的二层网络，kubernetes里一个Pod的容器访问另外一个Pod里的容器时，就是通过Pod IP所在的虚拟二层网络进行通信的，而真实的TCP/IP流量时通过Node IP所在的物理网卡流出的

Cluster IP是一种虚拟的IP，但更像一个伪造的IP网络，因为：

1.  Cluster IP仅仅作用于kubernetes Service这个对象，并由kubernetes管理和分配
    
2.  Cluster IP无法被Ping，因为没有一个实体网络对象来响应
    
3.  Cluster IP只能结合Service Port组成一个具体的通信端口，单独的Cluster IP不具备TCP/IP通信的基础，并且他们属于kubernetes集群这样一个封闭的空间，集群外的节点如果要访问这个通信端口，则需要做一些额外的工作
    
4.  kubernetes集群内，Node IP网络，Pod IP网络与Cluster IP网络之间的通信，采用的是kubernetes自己设计的一种编程方式的特殊路由规则，与我们熟知的IP路由有很大的不同
    

那要如何实现外部应用访问集群内部的服务模块，可以使用NodePort

```
apiVersion: v1
kind: Service
metadata:
  name: tomcat-service
spec:
  type: NodePort
  ports:
  - port:8080
    nodePort: 31002
  selector:
    tier: frontend
```

其中，nodePort:31002这个属性表明手动指定tomcat-service的NodePort为31002，否则Kubernetes会自动分配一个可用的端口。

NodePort的实现方式是在Kubernetes集群里的每个Node上都为需要外部访问的Service开启一个对应的TCP监听端口，外部系统只要用任意一个Node的IP地址+具体的NodePort端口号即可以访问此服务，在任意Node上运行netstat命令，就可以看到有NodePort端口被监听。

### Job

批处理任务通常并行（或者串行）启动多个计算进程去处理一批工作项（work item），在处理完成后，整个批处理任务结束。Job也是一组Pod容器，但是Job控制Pod副本与RC等控制器的工作机制有以下重要差别

1.  Job所控制的Pod副本是短暂运行的，可以将其视为一组Docker容器，其中的每个Docker容器都仅仅运行一次。当Job控制的所有Pod副本都运行结束时，对应的Job也就结束了。Job在实现方式上与RC等副本控制器不同，Job生成Pod副本时不能自动重启的，对应Pod副本的RestartPoliy都被设置为Never。CronJob提供了类似crontab的定时任务，解决了某些批处理任务需要定时反复执行的问题
    
2.  Job所控制的Pod副本的工作模式能够多实例并行计算，以TensorFlow框架为例，可以将一个机器学习的计算任务分不到10台机器上，在每台机器上都运行一个worker执行计算任务，这很适合通过Job生成10个Pod副本同事启动运算。
    

### Volume

存储卷是Pod中能够被多个容器访问的共享目录。Kubernetes的Volume概念、用途和目的与Docker的Volume比较类似，但两者不能等价。首先Kubernetes中的Volume被定义在Pod上，然后被一个Pod里的多个容器挂载到具体的文件目录下；其次，Kubernetes中的Volume与Pod的生命周期相同，但与容器的生命周期不相关，当容器终止或重启时，Volume中的数据也不会丢失。最后，Kubernetes支持多种类型的Volume，例如GlusterFS、Ceph等先进的分布式文件系统。

Volume的使用也比较简单，在大多数情况下，我们先在Pod上声明一个Volume，然后在容器里引用该Volume并挂载（Mount）到容器里的某个目录上。举例来说，我们要给之前的Tomcat Pod增加一个名为datavol的Volume，并且挂载到容器的 `/mydata-data` 目录上，则只要对Pod的定义文件做如下修正即可

```
template:
  metadata:
    labels:
      app: app-demo
      tier: frontend
  spec:
    volumes:
    - name: datavol
      emptyDir: {}
    containers:
    - name: tomcat-demo
      image: tomcat
      volumeMounts:
      - mountPath: /mydata-data
        name: datavol
      imagePullPolicy: IfNotPresent
```

Kubernetes提供了非常丰富的Volume类型，下面逐一进行说明：

1.  emptyDir：一个emptyDir Volume是在Pod分配到Node时创建的。从它的名称就可以看出，他的初始内容为空，并且无须指定宿主机上对应的目录文件，因为这是Kubernetes自动分配的一个目录，当Pod从Node上移除时，emptyDir中的数据也会被永久删除。emptyDir的一些用途如下：临时空间；长时间任务的中间过程CheckPoint的临时保存目录；一个容器需要从另一个容器中获取数据的目录。
    
2.  hostPath：hostPath为在Pod上挂载宿主机上的文件或目录，它通常可以用于以下几个方面：1，容器应用程序生成的日志文件需要永久保存时，可以使用宿主机的高速文件系统进行存储；2，需要访问宿主机上Docker引擎内部数据结构的容器应用时，可以通过定义hostPath为宿主机 `/var/lib/docker` 目录，使容器内部应用可以直接访问Docker的文件系统。在使用这种类型的Volume时，需要注意以下几点：1，在不同的Node上具有相同配置的Pod，可能会因为宿主机上目录和文件不同而导致Volume上目录和文件的访问结果不一致；2，如果使用了资源配额管理，则Kubernetes无法将hostPath在宿主机上使用的资源纳入管理。
    
    ```
    volumes:
    - name: "persistent-storage"
      hostPath:
        path: "/data"
    ```
    
3.  gcePersistentDisk：使用这种类型的Volume表示使用谷歌公有云提供的永久磁盘（Persistent Disk，PD）存放Volume的数据，它与emptyDir不同，PD上的内容会被永久保存，当Pod被删除时，PD只是被卸载（Unmount），但不会被删除。
    
    ```
    volumes:
    - name: test-volume
      gcePersistentDisk:
        pdName: my-data-disk
        fsType: ext4
    ```
    
4.  awsElasticBlockStore：亚马逊公有云提供的EBS Volume存储数据
    
    ```
    volumes:
    - name: test-volume
      awsElasticBlockStore:
        volumeID: aws://<availability-zone>/<volume-id>
        fsType: ext4
    ```
    
5.  NFS：使用NFS网络文件系统提供的共享目录存储数据时，我们需要在系统中部署一个NFS Server。
    
    ```
    volumes:
    - name: nfs
      nfs:
        server: nfs-server.localhost
        path: "/"
    ```
    
6.  其他类型的Volume：
    
    1.  iscis：使用iSCSI存储设备上的目录挂载到Pod中
        
    2.  flocker：使用Flocker管理存储卷
        
    3.  glusterfs：使用开源GlusterFS网络文件系统的目录挂载到Pod中
        
    4.  rbd：使用Ceph块设备共享存储（Rados Block Device）挂载到Pod中
        
    5.  gitRepo：通过挂载一个空目录，并从Git库中clone一个仓库以供Pod使用
        
    6.  secret： 一个Secret Volume用于为Pod提供加密的信息，你可以将定义在Kubernetes中的Secret直接挂载为文件让Pod访问。Secret Volume是通过TMFS（内存文件系统）实现的，这种类型的Volume是不会被持久化的
        
    

### Persistent Volume

之前提到的Volume是被定义在Pod上的，属于计算资源的一部分，而实际上，网络存储是相对独立于计算资源而存在的一种实体资源。比如在使用虚拟机的情况下，我们通常会先定义一个网络存储，然后从中划出一个网盘并挂接到虚拟机上。Persistent Volume（PV）和与之相关联的Persistent Volume Claim（PVC）也起到了类似的作用

PV可以被理解成Kubernetes集群中某个网络存储对应的一块存储，它与Volume类似，但有以下区别

1.  PV只能是网络存储，不属于任何Node，但可以在每个Node上访问
    
2.  PV并不是被定义在Pod上的，而是独立与Pod之外定义的
    
3.  PV目前支持的类型包括：gcePersistentDisk、awsElasticBlockStore、AzureFile、AzureDisk、FC（Fibre Channel）、Flockers、NFS、iSCSI、RBD（Rados Block Device）、CephFS、Cinder、GlusterFS、VsphereVolume、Quobyte Volumes、VMware Photon、Portworx Volumes、ScaleIO Volumes
    

下面给出了NFS类型的PV的一个yaml定义文件，声明了需要5Gi的存储空间

```
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv003
spec:
  capacity:
    storage: 5Gi
  accessModes:
  - ReadWriteOnce
  nfs:
    path: /somepath
    server: 172.17.0.2
```

比较重要的是PV的 `accessModes` 属性，目前有以下类型：

1.  ReadWriteOnce：读写权限，并且只能被单个Node挂载
    
2.  ReadOnlyMany：只读权限，允许被多个Node挂载
    
3.  ReadWriteMany：读写权限，允许被多个Node挂载
    

如果某个Pod想申请某种类型的PV，则首先需要定义一个PersistentVolumeClain对象

```
kind: PersistentVolumeClain
apiVersion: v1
metadata:
  name: myclain
spec:
  accessModes:
  - ReadWriteOnce
  resources:
    requests:
      storage: 8Gi
```

然后，在Pod的Volume定义中引用上述PVC即可

```
volumes:
  -name: mypd
    persistentVolumeClain:
      clainName: myclaim
```

最后说说PV的状态，PV是有状态的对象，它的状态有以下几种：

-   Available：空闲状态
    
-   Bound：已经绑定到某个PVC上
    
-   Released：对应的PVC已经被删除，但资源还没有被集群收回
    
-   Failed：PV自动回收失败
    

### Namespace

Namespace（命名空间）是Kubernetes系统中的另一个非常重要的概念，Namespace在很多情况下用于实现多租户的资源隔离。Namespace通过将集群内部的资源对象分配到不同的Namespace中，形成逻辑上分组的不同项目、小组或用户组，便于不同的分组在共享使用整个集群的资源的同事还能被分别管理

Kubernetes集群在启动后会创建一个 `default` 的Namespace，通过kubectl可以查看： `kubectl get namespaces`

Namespace的定义很简单，如下所示的yaml定义了名为 `development` 的 Namespace

```
apiVersion: v1
kind: Namespace
metadata:
  name: development
```

一旦创建了Namespace，我们在创建资源对象时就可以指定这个资源对象属于那个Namespace。

```
apiVersion: v1
kind: Pod
metadata:
  name: busybox
  namespace: development
spec:
  containers:
  - image: busybox
    command:
    - sleep
    - "3600"
  name: busybox
```

此时查看对应Namespace的Pod：`kubectl get pods --namespace=development`

### Annotation

Annotation（注解）与Label类似，也使用key/value键值对的形式进行定义。不同的是Label具有严格的命名规则，它定义的是Kubernetes对象的元数据（Metadata），并且用户Label Selector。Annotation则是用户任意定义的附加信息，以便于外部工具查找。在很多时候，Kubernetes的模块自身会通过Annotation标记资源对象的一些特殊信息。

通常来说，用Annotation来记录的信息如下：

-   build信息，release信息，Docker镜像信息，例如时间戳、release id号、PR号，镜像Hash值
    
-   日志库、监控库、分析库等资源库的地址信息
    
-   程序调试工具信息，例如工具名称、版本号等
    
-   团队的联系信息，例如电话号码、负责人名称、网址等
    

### ConfigMap

为了集中管理系统的配置参数，而不是管理一堆配置文件。Kubernetes把所有的配置项都当作 `key-value` 字符串，当然value可以来自某个文本文件。这些配置项可以作为Map表中的一个项，整个Map的数据可以被持久化存储在Kubernetes的Etcd数据库中，然后提供API以方便Kubernetes相关组件或客户应用CRUD操作这些数据，上述专门用来保存配置参数的Map就是Kubernetes ConfigMap资源对象。

接下里Kubernetes提供了一种内建机制，将存储在etcd中的ConfigMap通过Volume映射的方式变成目标Pod内的配置文件，不管目标Pod被调度到哪台服务器上，都会完成自动映射。进一步地，如果ConfigMap中的key-value数据被修改，则映射到Pod中的配置文件也会随之更新。

## Kubernetes安装配置指南

### kubectl命令行工具用法详解

kubectl作为客户端CLI工具，可以让用户通过命令行对Kubernetes集群进行操作。

#### kubectl用法概述

kubectl命令行的语法如下：

```
kubectl [command] [TYPE] [NAME] [flags]
```

command：子命令，用于操作Kubernetes集群对象的命令，例如create、delete、describe、get、apply等

Type：资源对象的类型，区分大小写，能以单数、复数或者简写形式表示。例如以下3种TYPE是等价的

```
kubectl get pod pod1
kubectl get pods pod1
kubectl get po pod1
```

NAME：资源对象的名称，区分大小写。如果不指定名称，系统则将返回属于TYPE的全部对象的列表

flags：kubectl子命令的可选参数，例如使用-s指定API Server的URL地址而不用默认值

获取多个Pod信息： `kubectl get pods pod1 pod2`

获取多种对象的信息： `kubectl get pod/pod1 rc/rc1`

同时应用多个yaml文件

```
kubectl get pod -f pod1.yaml -f pod2.yaml
kubectl create -f pod1.yaml -f rc1.yaml
```

## 深入掌握Pod

### Pod定义详解

yaml格式的Pod定义文件的完整内容如下

```
# yaml格式的pod定义文件完整内容：
apiVersion: v1        　　#必选，版本号，例如v1
kind: Pod       　　　　　　#必选，Pod
metadata:       　　　　　　#必选，元数据
  name: string        　　#必选，Pod名称
  namespace: string     　　#必选，Pod所属的命名空间
  labels:       　　　　　　#自定义标签
    - name: string      　#自定义标签名字
  annotations:        　　#自定义注释列表
    - name: string
spec:         　　　　　　　#必选，Pod中容器的详细定义
  containers:       　　　　#必选，Pod中容器列表
  - name: string      　　#必选，容器名称
    image: string     　　#必选，容器的镜像名称
    imagePullPolicy: [Always | Never | IfNotPresent]  #获取镜像的策略 Alawys表示下载镜像 IfnotPresent表示优先使用本地镜像，否则下载镜像，Nerver表示仅使用本地镜像
    command: [string]     　　#容器的启动命令列表，如不指定，使用打包时使用的启动命令
    args: [string]      　　 #容器的启动命令参数列表
    workingDir: string      #容器的工作目录
    volumeMounts:     　　　　#挂载到容器内部的存储卷配置
    - name: string      　　　#引用pod定义的共享存储卷的名称，需用volumes[]部分定义的的卷名
      mountPath: string     #存储卷在容器内mount的绝对路径，应少于512字符
      readOnly: boolean     #是否为只读模式
    ports:        　　　　　　#需要暴露的端口库号列表
    - name: string      　　　#端口号名称
      containerPort: int    #容器需要监听的端口号
      hostPort: int     　　 #容器所在主机需要监听的端口号，默认与Container相同
      protocol: string      #端口协议，支持TCP和UDP，默认TCP
    env:        　　　　　　#容器运行前需设置的环境变量列表
    - name: string      　　#环境变量名称
      value: string     　　#环境变量的值
    resources:        　　#资源限制和请求的设置
      limits:       　　　　#资源限制的设置
        cpu: string     　　#Cpu的限制，单位为core数，将用于docker run --cpu-shares参数
        memory: string      #内存限制，单位可以为Mib/Gib，将用于docker run --memory参数
      requests:       　　#资源请求的设置
        cpu: string     　　#Cpu请求，容器启动的初始可用数量
        memory: string      #内存清楚，容器启动的初始可用数量
    livenessProbe:      　　#对Pod内个容器健康检查的设置，当探测无响应几次后将自动重启该容器，检查方法有exec、httpGet和tcpSocket，对一个容器只需设置其中一种方法即可
      exec:       　　　　　　#对Pod容器内检查方式设置为exec方式
        command: [string]   #exec方式需要制定的命令或脚本
      httpGet:        　　　　#对Pod内个容器健康检查方法设置为HttpGet，需要制定Path、port
        path: string
        port: number
        host: string
        scheme: string
        HttpHeaders:
        - name: string
          value: string
      tcpSocket:      　　　　　　#对Pod内个容器健康检查方式设置为tcpSocket方式
         port: number
       initialDelaySeconds: 0   #容器启动完成后首次探测的时间，单位为秒
       timeoutSeconds: 0    　　#对容器健康检查探测等待响应的超时时间，单位秒，默认1秒
       periodSeconds: 0     　　#对容器监控检查的定期探测时间设置，单位秒，默认10秒一次
       successThreshold: 0
       failureThreshold: 0
       securityContext:
         privileged: false
    restartPolicy: [Always | Never | OnFailure] #Pod的重启策略，Always表示一旦不管以何种方式终止运行，kubelet都将重启，OnFailure表示只有Pod以非0退出码退出才重启，Nerver表示不再重启该Pod
    nodeSelector: obeject   　　#设置NodeSelector表示将该Pod调度到包含这个label的node上，以key：value的格式指定
    imagePullSecrets:     　　　　#Pull镜像时使用的secret名称，以key：secretkey格式指定
    - name: string
    hostNetwork: false      　　#是否使用主机网络模式，默认为false，如果设置为true，表示使用宿主机网络
    volumes:        　　　　　　#在该pod上定义共享存储卷列表
    - name: string     　　 　　#共享存储卷名称 （volumes类型有很多种）
      emptyDir: {}      　　　　#类型为emtyDir的存储卷，与Pod同生命周期的一个临时目录。为空值
      hostPath: string      　　#类型为hostPath的存储卷，表示挂载Pod所在宿主机的目录
        path: string      　　#Pod所在宿主机的目录，将被用于同期中mount的目录
      secret:       　　　　　　#类型为secret的存储卷，挂载集群与定义的secre对象到容器内部
        scretname: string
        items:
        - key: string
          path: string
      configMap:      　　　　#类型为configMap的存储卷，挂载预定义的configMap对象到容器内部
        name: string
        items:
        - key: string
          path: string
```

### Pod的基本用法

Kubernetes要求我们自己创建的Docker镜像并以一个前台命令作为启动命令

如果两个容器为紧耦合的关系，并组合成一个整体对外提供服务时，应将这两个容器打包为一个Pod

```
apiVersion: v1
kind: Pod
metadata:
  name: redis-php
  labels:
    name: redis-php
spec:
  containers:
  - name: frontend
    image: jjjj
    ports:
    - containerPort: 80
  -name: redis
    image: llll
    ports:
    - containerPort: 6379
```

属于同一个Pod的多个容器应用之间相互访问时仅需通过localhost就可以通信，使得这一组容器被绑定在了一个环境中。

### 静态Pod

静态Pod是又kubelet进行管理的仅存在与特定Node上的Pod。他们不能通过API Server进行管理，无法与ReplicationController、Deployment或者DaemonSet进行关联，并且kubelet无法对他们进行健康检查。静态Pod总是由Kubelet创建的，并且总在Kubelet所在的Node上运行。

静态Pod由两种创建方式：

1.  配置文件方式：首先，需要设置Kubelet的启动参数 `--config`， 指定Kubelet需要监控的配置文件所在的目录，Kubelet会定期扫描该目录，并根据该目录下的 `.yaml` 或 `.json` 文件进行创建操作，删除此Pod只能到Kubelet所在机器上删除对应的配置文件即可
    
2.  HTTP方式：通过设置Kubelet的启动参数 `--manifest-url`，Kubelet将会定期从该URL地址下载Pod的定义文件，并以 `.yaml` 或 `.json` 文件的格式进行解析，然后创建Pod
    

### Pod容器共享Volume

同一个Pod中的多个容器能够共享Pod级别的存储卷Volume。Volume可以被定义为各种类型，多个容器各自进行挂载操作，将一个Volume挂载为容器内部需要的目录，如图所示

![Snipaste 2021 11 20 18 21 17](http://korov.myqnapcloud.cn:19000/images/Snipaste_2021-11-20_18-21-17.png)

配置文件如下：

pod-volume-applogs.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: volume-pod
spec:
  containers:
  - name: tomcat
    image: tomcat
    ports:
    - containerPort: 8080
      volumeMounts:
      - name: app-logs
        mountPath: /usr/local/tomcat/logs
  - name: busybox
    image: busybox
    command: ["sh", "-c", "tail -f /logs/catalina*.log"]
    volumeMounts:
    - name: app-logs
      mountPath: /logs
  volumes:
  - name: app-logs
    emptyDir: {}
```

这里设置的Volume名为app-logs，类型为emptyDir，挂载到tomcat容器内的 `/usr/local/tomcat/logs` 目录，同时挂载在busybox容器内的 `/logs` 目录。tomcat容器在启动后会向 `/usr/local/tomcat/logs` 目录写文件，busybox容器就可以读取其中的文件了。

### Pod的配置管理

#### ConfigMap概述

ConfigMap供容器使用的典型用法如下：

1.  生成为容器内的环境变量
    
2.  设置容器启动命令的启动参数（需设置为环境变量）
    
3.  以Volume的形式挂载为容器内部的文件或目录
    

ConfigMap以一个或多个key:value的形式保存在Kubernetes系统中供应用使用，既可以用于表示一个变量的值（例如apploglevel=info），也可以用于表示一个完整配置文件的内容（例如 `server.xml=<?xml…>…`）

可以通过yaml配置文件或者直接使用 `kubectl create configmap` 命令行的方式来创建ConfigMap

#### 创建ConfigMap资源对象

##### 通过yaml配置文件方式创建

cm-appvars.yaml

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: cm-appvars
data:
  apploglevel: info
  appdatadir: /var/data
  key-serverxml: |
    <?xml ...>...
```

执行kubectl create命令创建该ConfigMap： `kubectl create -f cm-appvars.yaml`

查看创建好的ConfigMap：

```
# 获取信息
kubectl get configmap
# 获取详细信息
kubectl describe configmap cm-appvars
```

##### 通过kubectl命令行方式创建

通过 `--from-file` 参数从文件中进行创建，可以指定key的名称，也可以在一个命令行中创建包含多个key的ConfigMap，语法为： `kubectl create configmap NAME --from-file=[key=]source --from-file=[key=]source`

通过 `--from-file` 参数从目录中进行创建，该目录下的每个配置文件名都被设置为key，文件的内容被设置为value，语法为： `kubectl create configmap NAME --from-file=config-files-dir`

使用 `--from-literal` 时会从文本中进行创建，直接将指定的 `key#=value#` 创建为ConfigMap的内容，语法为： `kubectl create configmap NAME --from-literal=key1=value1 --from-literal=key2=value2`

#### 在Pod中使用ConfigMap

##### 通过环境变量方式使用ConfigMap

以前面创建的ConfigMap `cm-appvars` 为例

cm-appvars.yaml

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: cm-appvars
data:
  apploglevel: info
  appdatadir: /var/data
```

使用如下文件创建Pod之后会在容器内生成APPLOGLEVEL和APPDATADIR两个环境变量

cm-appvars.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: cm-test-pod
spec:
  containers:
  - name: cm-test
    image: busybox
    command: ["/bin/sh", "-c", "env | grep APP"]
    env:
    - name: APPLOGLEVEL #定义环境变量的名称
      valueFrom:  # key apploglevel对应的值
        configMapKeyRef:
          name: cm-appvars
          key: apploglevel
    - name: APPDATADIR
      valueFrom:
        configMapKeyRef:
          name: cm-appvars
          key: appdatadir
```

使用如下文件创建Pod将会在容器内部生成apploglevel和appdatadir两个环境变量

cm-appvars.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: cm-test-pod
spec:
  containers:
  - name: cm-test
    image: busybox
    command: ["/bin/sh", "-c", "env | grep APP"]
    envFrom:
    - configMapRef
      name: cm-appvars # 根据 cm-appvars中的key=value自动生成环境变量
  restartPolicy: Never
```

<table><tbody><tr><td><i title="Important"></i></td><td>需要说明的是，环境变量的名称受POSIX命名规范（[a-zA-Z_][a-zA-Z0-9_]*）约束，不能以数字开头，如果包含非法字符，则系统将跳过该环境变量的创建，并记录一个Event来提示环境变量无法生成，但并不组织Pod的启动</td></tr></tbody></table>

##### 通过volumeMount使用ConfigMap

在Pod `cm-test-app` 的定义中，将ConfigMap `cm-appconfigfiles` 中的内容以文件的形式mount到容器内部 `/configfiles` 目录下。

cm-test-app.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: cm-test-pod
spec:
  containers:
  - name: cm-test-app
    image: kubeguide/tomcat-app:v1
    ports:
    - containerPort: 8080
    volumeMounts:
    - name: serverxml  # 引用Volume的名称
      mountPath: /configfiles # 挂载到容器内的目录
  volumes:
  - name: serverxml  # 定义Volume的名称
    configMap:
      name: cm-appconfigfiles # 使用ConfigMap cm-appconfigfiles
      item:
      - key: key-serverxml  # key=key-serverxml
        path: server.xml # value将server.xml文件名进行挂载
```

如果在引用ConfigMap时不指定items，则使用volumeMount方式在容器内的目录下为每个item都生成一个文件名为key的文件。

#### 使用ConfigMap的限制条件

-   ConfigMap必须在Pod之前创建
    
-   ConfigMap受Namespace限制，只有处于相同Namespace中的Pod才可以引用它
    
-   ConfigMap中的配额管理还未能实现
    
-   kubelet只支持可以被API Server管理的Pod使用ConfigMap。kubelet在本Node上通过 `--manifest-url` 或 `--config` 自动创建的静态Pod将无法引用ConfigMap。
    
-   在Pod对ConfigMap进行挂载操作时，在容器内部只能挂载为 **目录** ，无法挂载为 **文件** 。在挂载到容器内部后，在目录下将包含ConfigMap定义的每个item，如果在该目录下原来还有其他文件，则容器内的该目录将被挂载的ConfigMap覆盖。
    

### Pod声明周期和重启策略

状态：

-   Pending：API Server已经创建该Pod，但在Pod内部还有一个或多个容器的镜像没有创建，包括正在下载镜像的过程
    
-   Running：Pod内所有容器均已创建，且至少有一个容器处于运行状态、正在启动状态或正在重启状态
    
-   Succeeded：Pod内所有容器均成功执行后退出，且不会再重启
    
-   Failed：Pod内所有容器均已退出，但至少有一个容器退出为失败状态
    
-   Unknown：由于某种原因无法获取该Pod的状态，可能由于网络通信不畅导致
    

Pod的重启策略（RestartPolicy）,应用于Pod内的所有容器，并且仅再Pod所处的Node上由kubelet进行判断和重启操作。

-   Always：当容器失效时，由kubelet自动重启该容器
    
-   OnFailure：当容器终止运行且退出码不为0时，由kueblet自动重启该容器
    
-   Never：不论容器运行状态如何，kubelet都不会重启该容器
    

kubelet重启失效容器的时间间隔以 `sync-frequency` 乘以2n来计算，例如1、2、4、8倍等，最长延时5min，并且再成功重启后的10min后重置该时间。

Pod的重启策略与控制方式息息相关。每种控制器对Pod的重启策略要求如下：

-   RC和DaemonSet：必须设置为Always，需要保证该容器持续运行
    
-   Job：OnFailuer或Never，确保容器执行完成后不再重启
    
-   kubelet：在Pod失效时自动重启它，不论将RestartPolicy设置为什么值，也不会对Pod进行健康检查
    

Kubernetes对Pod的健康状态可以通过两类探针来检查：LivenessProbe和ReadinessProbe，kubelet定期执行这两类探针来诊断容器的健康状况

LivenessProbe探针：用于判断容器是否存活（Running状态），如果LivenessProbe探针探测到容器不健康，则kubelet将杀掉该容器，并根据容器的重启策略做相应的处理。如果一个容器不包含LivenessProbe探针，那么kubelet认为该容器的LivenessProbe探针返回的值永远时Success。

ReadinessProbe探针：用于判断容器服务是否可用（Ready状态），达到Reay状态的Pod才可以接收请求。对于被Service管理的Pod，Service与Pod Endpoint的关联关系也将基于Pod是否Ready进行设置。如果在运行过程中Ready状态变为False，则系统自动将其从Service的后端Endpoint列表中隔离出去，后续再把恢复到Ready状态的Pod加回后端Endpoint列表。这样就能保证客户端在访问Service时不会被转发到服务不可用的Pod实例上。

LivenessProbe和ReadinessProbe均可配置以下三种实现方式：

1.  ExecAction：在容器内执行一个命令，如果该命令的返回码为0，则表明容器健康。以下通过执行 `cat /tmp/health` 命令来判断一个容器运行是否正常。在该Pod运行后，将创建/tmp/health文件10s后删除该文件，而LivenessProbe健康检查的初始探测时间（initialDeplaySeconds）为15s，探测结果是Fail，将导致kubelet杀掉该容器并重启它
    
    ```
    apiVersion: v1
    kind: Pod
    metadata:
      labels:
        test: liveness
      name: liveness-exec
    spec:
      containers:
      - name: liveness
        image: gcr.io/google_containers/busybox
        args:
        - /bin/sh
        - -c
        - echo ok > /tmp/health; sleep 10; rm -rf /tmp/health; sleep 600
        livenessProbe:
          exec:
            command:
            - cat
            - /tmp/health
          initialDeplaySeconds: 15
          timeoutSeconds: 1
    ```
    
2.  TCPSocketAction：通过容器的IP地址和端口号执行TCP检查，如果能够建立TCP连接，则表明容器健康。如下通过与容器内的 `localhost:80` 建立TCP连接进行健康检查
    
    ```
    apiVersion: v1
    kind: Pod
    metadata:
      name: pod-with-healthcheck
    spec:
      containers:
      - name: nginx
        image: nginx
        ports:
        - containerPort: 80
        livenessProbe:
          tcpSocket:
            port: 80
          initialDeplaySeconds: 30
          timeoutSeconds: 1
    ```
    
3.  HTTPGetAction：通过容器IP的IP地址、端口号及路径调用HTTP Get方法，如果响应的状态码大于等于200且小于400，则认为容器健康。
    
    ```
    apiVersion: v1
    kind: Pod
    metadata:
      name: pod-with-healthcheck
    spec:
      containers:
      - name: nginx
        image: nginx
        ports:
        - containerPort: 80
        livenessProbe:
          httpGet:
            path: /_status/healthz
            port: 80
          initialDeplaySeconds: 30
          timeoutSeconds: 1
    ```
    

<table><tbody><tr><td><i title="Note"></i></td><td>initialDeplaySeconds：启动容器后进行首次健康检查的等待时间，单位为s。timeoutSeconds：健康检查发送请求后等待响应的超时时间，单位为s。当超时发生时，kubelet会认为容器已经无法提供服务，将会重启该容器</td></tr></tbody></table>

### 玩转Pod调度

严谨的说，RC的继任者其实并不是Deployment，而是ReplicaSet，因为ReplicaSet进一步增强了RC标签选择器的灵活性。之前RC的标签选择器只能选择一个标签，而ReplicaSet拥有集合式的标签选择器，可以选择多个Pod标签，如下所示

```
selector:
  matchLabels:
    tier: frontend
  matchExpressions:
    - {key: tier, operator: In, values: [frontend]}
```

与RC不同，ReplicaSet被设计成能控制多个不同标签的Pod副本。一种常见的应用场景是，应用MyApp目前发布了v1与v2两个版本，用户希望MyApp的Pod副本数保持为3个，可以同时包含v1和贰版本的Pod，就可以用ReplicaSet来实现这种控制

```
selector:
  matchLabels:
    tier: frontend
  matchExpressions:
    - {key: tier, operator: In, values: [v1,v2]}
```

其实，Kubernetes的滚动升级就是巧妙运用ReplicaSet的这个特性来实现的，同时，Deployment也是通过ReplicaSet来实现Pod副本自动控制功能的。我们不应该直接使用底层ReplicaSet来控制Pod副本，而应该使用管理ReplicaSet的Deployment对象来控制副本，这是来自官方的建议。

当我们希望某种Pod的副本全部在指定的一个或者一些节点上运行，比如希望MySQL数据库调度到一个具有SSD磁盘的目标节点上，此时Pod模板中的NodeSelector属性就开始发挥作用了，上述MySQL定向调度案例的是实现方式可以分为以下两步：

1.  把具有SSD磁盘的Node都打上自己定义标签 `disk=ssd`
    
2.  在Pod模板中设定NodeSelector的值为 `disk:ssd`
    

如此一来，Kubernetes在调度Pod副本的时候，就会先按照Node的标签过滤出合适的目标节点，然后选择一个最佳节点进行调度。

上述逻辑看起来即简单又完美，但在真实的生产环境中可能面临以下问题：

1.  如果NodeSelector选择的Label不存在或者不符合条件，比如这些目标节点此时宕机或者资源不足，该怎么办
    
2.  如果要选择多种合适的目标节点，比如SSD磁盘的节点或者超高速硬盘的节点，该怎么办？
    

在真实的生产环境中还存在如下所述的特殊需求

1.  不同Pod之间的亲和性（Affinity）。比如MySQL数据库与Redis中间件不能被调度到同一个目标节点上，或者两种不同的Pod必须被调度到同一个Node上，以实现本地文件共享或本地网络通信等特殊需求，这就是 `PodAffinity` 要解决的问题
    
2.  又状态集群的调度。对于Zookeeper、Elasticsearch、MongoDB、Kafka等有状态集群，虽然集群中的每个Worker节点看起来都是相同的，但每个Worker节点都必须有明确的、不变的唯一ID（主机名或IP地址），这些节点的启动和停止次序通常有严格的顺序。此外，由于集群需要持久化保存状态数据，所以集群中的Worker节点对应的Pod不管在哪个Node上恢复，都需要挂载原来的Volume，因此这些Pod还需要捆绑具体的PV。针对这种复杂的需求，Kubernetes提供了StatefulSet这种特殊的副本控制器来解决
    
3.  在每个Node上调度并且仅仅创建一个Pod副本。这种调度通常用在系统监控相关的Pod，比如主机上的日志采集、主机性能采集等进程需要被部署到集群中的每个节点，并且只能部署一个副本，这就是DaemonSet这种特殊Pod副本控制所解决的问题
    
4.  对于批处理组作业，需要创建多个Pod副本来协同工作，当这些Pod副本都完成自己的工作任务时，整个批处理作业就结束了。这种Pod运行且仅运行一次的特殊调度，有Job和CronJob
    

Kubernetes 1.9之前，在RC等对象被删除后，他们所创建的Pod副本都不会被删除；Kubernetes 1.9以后，这些Pod副本会被一并删除。如果不希望这样做，则可以通过 `kubectl` 命令的 `--cascade=false` 参数来取消这一默认特性： `kubectl delete replicaset my-repset --cascade=false`

#### NodeSelector： 定向调度

有时候我们需要将Pod调度到指定的一些Node上，可以通过Node的标签（Label）和Pod的nodeSelector属性相匹配，来达到上述目的。

1.  首先通过 `kubectl label` 命令给目标Node打上一些标签： `kubectl label nodes <node-name> <label-key>=<label-value>`。
    
2.  然后，在Pod的定义中加上nodeSelector的设置，以 `redis-master-controller.yaml`
    
    redis-master-controller.yaml
    
    ```
    apiVersion: v1
    kind: ReplicationController
    metadata:
      name: redis-master
      labels:
        name: redis-master
    spec:
      replicas: 1
      selector:
        name: redis-master
      template:
        metadata:
          labels:
            name: redis-master
        spec:
          containers:
          - name: master
            image: kubeguide/redis-master
            ports:
            - containerPort: 6379
          nodeSelector:
            zone: north
    ```
    

<table><tbody><tr><td><i title="Note"></i></td><td>如果我们指定了Pod的nodeSelector条件，且在集群中不存在包含相应标签的Node，则即使在集群中还有其他可供使用的Node，这个Pod也无法被成功调度。</td></tr></tbody></table>

#### NodeAffinity: Node亲和性调度

NodeAffinity意为Node亲和性的调度策略，用于替换NodeSelector的全新调度策略，目前有两种节点亲和性表达

-   RequiredDuringSchedulingIgnoredDuringExecution：必须满足指定的规则才可以调度Pod到Node上
    
-   PreferredDuringSchedulingIgnoredDuringExecution：强调优先满足指定规则，调度器会尝试调度Pod到Node上，但并不强求，相当于软限制。多个优先级规则还可以设置权重（weight）值，以定义执行的先后顺序
    

IgnoredDuringExecution的意思是：如果一个Pod所在的节点在Pod运行期间标签发生了变更，不再符合Pod的节点亲和性需求，则系统将忽略Node的Lebel变化

下面的例子设置了NodeAffinity调度如下规则：

-   requiredDuringSchedulingIgnoredDuringExecution要求只运行在amd64的节点上
    
-   preferredDuringSchedulingIgnoredDuringExecution的要求是尽量运行在磁盘类型为ssd的节点上
    

```
apiVersion: v1
kind: Pod
metadata:
  name: with-node-affinity
spec:
  affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
        - matchExpressions:
          - key: beta.kubernetes.io/arch
            operator: In
            values:
            - amd64
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 1
        preference:
          matchExpressions:
          - key: disk-type
            operator: In
            values:
            - ssd
  containers:
  - name: with-node-affinity
    image: gcr.io/google_containers/pause:2.0
```

NodeAffinity语法支持的操作符包括 `In`, `NotIn`, `Exists`, `DoesNotExist`, `Gt`, `Lt`。

NodeAffinity规则设置的注意事项如下：

-   如果同时定义了 nodeSelector和 nodeAffinity，那么必须两个条件都得到满足，Pod才能最终运行在指定的Node上。
    
-   如果nodeAffinity指定了多个nodeSelectorTerms，那么其中一个能够匹配成功即可
    
-   如果在nodeSelectorTerms中有多个matchExpressions，则一个几点必须满足所有matchExpressions才能运行该Pod
    

#### PodAffinity： Pod亲和与互斥调度策略

根据在节点上正在运行的Pod的标签而不是节点的标签进行判断和调度，要求对节点和Pod两个条件进行匹配。这种规则可以描述为：如果在具有标签X的Node上运行了一个或者多个符合条件Y的Pod，那么Pod应该（如果是互斥的情况，那么就变成拒绝）运行在这个Node上

这里的X指的是集群中的节点、机架区域等概念，通过Kubernetes内置节点标签中的key来进行声明。这个key的名字为 `topologyKey` （ `kubernetes.io/hostname`, `failure-domain.beta.kubernetes.io/zone`, `failure-domain.beta.kubernetes.io/region` ）

与节点不同，Pod是属于某个命名空间的，所以条件Y表达的是一个或者全部命名空间中的一个Label Selector。Pod亲和与互斥条件设置也是 `requiredDuringSchedulingIgnoredDuringExecution` 和 `preferredDuringSchedulingIgnoredDuringExecution`

假设现在有一个名为pod-flag的Pod，带有标签 `security=S1` 和 `app=nginx`。

```
apiVersion: v1
kind: Pod
metadata:
  name: pod-affinity
spec:
  affinity:
    podAffinity:
      requiredDuringSchedulingIgnoreDuringExecution:
      - labelSelector:
          matchExpressions:
          - key: security
            operator: In
            values:
            - S1
        topologyKey: kubernetes.io/hostname
  containers:
  - name: with-pod-affinity
    image: gcr.io/google_containers/pause:2.0
```

互斥，要求新Pod与 `security=S1` 的Pod为同一个zone，但是不与 `app=nginx` 的Pod为同一个Node。创建Pod之后，

```
apiVersion: v1
kind: Pod
metadata:
  name: pod-affinity
spec:
  affinity:
    podAffinity:
      requiredDuringSchedulingIgnoreDuringExecution:
      - labelSelector:
          matchExpressions:
          - key: security
            operator: In
            values:
            - S1
        topologyKey: failure-domain.beta.kubernetes.io/zone
    podAntiAffinity:
      requiredDuringSchedulingIgnoreDuringExecution:
      - labelSelector:
          matchExpressions:
          - key: app
            operator: In
            values:
            - nginx
        topologyKey: kubernetes.io/hostname
  containers:
  - name: with-pod-affinity
    image: gcr.io/google_containers/pause:2.0
```

Pod亲和性的操作符也包括 `In`, `NotIn`, `Exists`, `DoesNotExist`, `Gt`, `Lt`。

原则上，topologyKey可以使用任何合法的标签Key赋值，但是出于性能和安全方面的考虑，对topologyKey有如下限制：

-   在Pod亲和性和RequiredDuringScheduling的Pod互斥性的定义中，不允许使用空的topologyKey
    
-   如果Admission controller包含了LimitPodHardAntiAffinityTopology，那么针对Required DuringScheduling的Pod互斥性定义就被限制为 `kubernetes.io/hostname`，要使用自定义的 topologyKey，
    
-   在PreferredDuringScheduling类型的Pod互斥性定义中，空的topologyKey会被解释为 `kubernetes.io/hostname`, `failure-domain.beta.kubernetes.io/zone`, \`failure-domain.beta.kubernetes.io/region\`的组合
    
-   如果不是上述情况，就可以采用任意合法的topologyKey
    

PodAffinity规则设置的注意事项如下：

-   除了Label Selector和topologyKey，用户还可以指定Namespace列表来进行限制，Namespace和Label Selector同级，省略Namespace表示使用定义了 affinity/anti-affinity 的Pod所在的Namespace，如果Namespace被设置为空值（""），则表示所有Namespace
    
-   在所有关联requiredDuringSchedulingIgnoreDuringExecution的matchExpressions全都满足之后，系统才能将Pod调度到某个Node上
    

#### Taints和Tolerations（污点和容忍）

Taint让Node拒绝Pod的运行。Taints需要和Tolerations配合使用，让Pod避开那些不合适的Node，在Node上设置一个或多个Taint之后，除非Pod明确声明能够容忍这些污点，否则无法在这些Node上运行。Tolerations是Pod的属性，让Pod能够（非必须）运行在标注了Taint的Node上。

创建Taint信息命令： `kubectl taint nodes node1 key=value:NoSchedule`

这个设置为node1加上了一个Taint，该Taint的键为key，值为value，Taint的效果是NoSchedule。这意为着除非Pod明确声明可以容忍这个Taint，否则就不会被调度到node1上。

然后需要在Pod上声明Toleration，下面的两个Toleration都被设置为可以容忍具有该Taint的Node，使得Pod能够被调度到node1上

```
toerations:
- key: "key"
  operator: "Equal"
  value: "value"
  effect: "NoSchedule"
```

或

```
toerations:
- key: "key"
  operator: "Exists"
  effect: "NoSchedule"
```

Pod的Toleration声明中的key和effect需要与Taint的设置保持一致，并且满足以下条件之一

-   operator的值是Exists（无须指定value）
    
-   operator的值是Equal并且value相等。如果不指定operator，则默认值为Equal
    

另外，有如下两个特例

-   空的key配合Exists操作符能够匹配所有的键和值
    
-   空的effect匹配所有的effect
    

系统允许在同一个Node上设置多个Taint，也可以在Pod上设置多个Toleration，Kubernetes调度器处理多个Taint和Toleration的逻辑顺序为：首先列出节点中所有的Taint，然后忽略Pod的Toleration能够匹配的部分，剩下的没有忽略的Taint就是对Pod的效果了。下面是几种特殊情况

-   如果在剩余的Taint中存在 `effect=NoSchedule`，则调度器不会把该Pod调度到这一节点上
    
-   如果在剩余的Taint中没有NoSchedule效果，但有PreferNodeSchedule效果（系统尽量避免把这个Pod调度到这一节点上，但不是强制的），则调度器会尝试不把这个Pod指派给这个节点
    
-   如果在剩余的Taint中有NoExecute小果果，并且这个Pod已经在该节点上运行，则会被驱逐；如果没有在该节点上运行，则也不会再被调度到该节点上。（如果Pod没有设置tolerationSeconds赋值，则会一直留在这一节点中）
    

#### Pod Priority Preemption: Pod优先级调度

在Kubernetes 1.8版本之前，当集群的可用资源不足时，在用户提交新的Pod创建请求后，该Pod会一直处于Pending状态，即使这个Pod是一个很重要的Pod，也只能被动等待其他Pod被删除并释放资源，才能有机会被调度成功。Kubernetes 1.8版本引入了基于Pod优先级抢占的调度策略，此时Kubernetes会尝试释放目标节点上低优先级的Pod，以腾出空间安置高优先级的Pod。我们可以通过以下几个维度来定义：

-   Priority，优先级
    
-   QoS，服务质量等级
    
-   系统定义的其他度量指标
    

优先级抢占调度策略的核心行为分别是驱逐（Eviction）与抢占（Preemption），这两种行为的使用场景不同，效果相同。Eviction是kubelet进程的行为，即当一个Node发生资源不足（under resource pressure）的情况下，该节点上的kubelet进程会执行驱逐动作，此时kubelet会综合考虑Pod的优先级、资源申请量与实际使用量等信息来计算哪些Pod需要被驱逐；当同样优先级的Pod需要被驱逐时，实际使用的资源量超过申请量最大倍数的高耗能Pod会被首先驱逐。对于QoS等级为 `Best Effort` 的Pod来说，由于没有定义资源申请（CPU/Memory Request），所以他们实际使用的资源可能非常大。Preemption则是Scheduler执行的行为，当一个新的Pod因为资源无法满足而不能被调度时，Scheduler可能（有权决定）选择驱逐部分低优先级的Pod实例来满足此Pod的调度目标。

<table><tbody><tr><td><i title="Note"></i></td><td>Scheduler可能会驱逐Node A上的一个Pod以满足Node B上的一个新Pod的调度任务。比如：一个低优先级的Pod A在Node A（属于机架R）上运行，此时有一个高优先级的Pod B等待调度，目标节点同属于机架R的Node B，他们中的一个或全部都定义了anti-affinity规则，不允许在同一个机架上运行，此时Scheduler只好驱逐低优先级的Pod A以满足高优先级的Pod B的调度。</td></tr></tbody></table>

Pod优先级调度示例如下：

首先由集群管理员创建PriorityClass，PriorityClass不属于任何命名空间

```
apiVersion: scheduling.k8s.io/v1beta1
kind: PriorityClass
metadata:
  name: high-priority
value: 100000
globalDefault: false
description: "This priority class should be used for XYZ serice pods only"
```

上述yaml文件定义了一个名为high-priority的优先级类别，优先级为100000，数字越大，优先级越高，超过一亿的数字被系统保留，用于指派给系统组件。

我们可以在任意Pod中引用上述Pod优先级类别：

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    env: test
spec:
  containers:
  - name: nginx
    image: nginx
    imagePullPolicy: IfNotPresent
  priorityClassName: high-priority
```

优先级抢占的调度方式可能会导致调度陷入死循环状态，当Kubernetes集群配置了多个调度器时，这一行为可能就会发生。使用优先级抢占的调度策略可能会导致某些Pod永远无法被成功调度。因此优先级调度不但增加了系统的复杂性，还可能带来额外不稳定的因素。因此，一旦发生资源紧张的局面，首先要考虑的是集群的扩容，如果无法扩容，则再考虑有监管的优先级调度特性，比如结合基于Namespace的资源配额限制来约束任意优先级抢占行为。

### Init Container（初始化容器）

在很多应用场景中，应用在启动之前都需要进行如下初始化操作

-   等待其他关联组件正确运行（例如数据库或某个后台服务）
    
-   基于环境变量或配置模板生成配置文件
    
-   从远程数据库获取本地所需配置，或者将自身注册到某个中央数据库中
    
-   下载相关依赖包，或者对系统进行一些预配置操作
    

`init conatiner` 用于在启动应用容器（app container）之前启动一个或多个初始化容器，完成应用容器所需的预置条件。 `init conatiner` 与应用容器在本质上是一样的，但他们是仅运行一次就结束的任务，并且必须在成功执行完成后，系统才能继续执行下一个容器。根据Pod的重启策略（RestartPolicy），当 `init conatiner` 执行失败，而且设置了 `RestartPolicy=Never` 时，Pod将会启动失败；而设置了 `RestartPolicy=Always` 时，Pod将会被系统自动重启

![Snipaste 2021 11 21 15 33 20](http://korov.myqnapcloud.cn:19000/images/Snipaste_2021-11-21_15-33-20.png)

下面以Nginx应用为例，在启动Nginx之前，通过初始化容器busybox为Nginx创建一个index.html主页文件。这里init container和Nginx设置了一个共享的Volume，以供Nginx访问init container设置的index.html文件

nginx-init-containers.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  annotations:
spec:
  # These containers are run during pod
  initialization
  initContainers:
  - name: install
    image: busybox
    command:
    - wget
    - "-O"
    - "/work-dir/index.html"
    - http://kubernetes.io
    volumeMounts:
    - name: workdir
      mountPath: "/work-dir"
  containers:
  - name: nginx
    image: nginx
    ports:
    - containerPort: 80
    volumeMounts:
    - name: workdir
      mountPath: /usr/share/nginx/html
  dnsPolicy: Default
  volumes:
  - name: workdir
    emptyDir: {}
```

`init conatiner` 与应用容器的区别如下：

1.  `init conatiner` 的运行方式与应用容器不同，他们必须先于应用容器执行完成，当设置了多个 `init conatiner` 时，将按顺序逐个运行，并且只有前一个 `init conatiner` 运行成功后才能运行后一个 `init conatiner`。当所有 `init conatiner` 都成功运行后，Kubernetes才会初始化Pod的各种信息，并开始创建和运行应用容器
    
2.  在 `init conatiner` 的定义中也可以设置资源限制、 Volume的使用和安全策略，等等。但资源限制的设置与应用容器略有不同
    
    1.  如果多个 `init conatiner` 都定义了资源请求/资源限制，则取最大的值作为所有 `init conatiner` 的资源请求值/资源限制值
        
    2.  Pod的有效（effective）资源请求值/资源限制值取以下二者中的较大值：所应用容器的资源请求值/资源限制值之和； `init conatiner` 的有效资源请求值/资源限制值
        
    3.  调度算法将基于Pod的有效资源请求值/资源限制值进行计算，也就是说 `init conatiner` 可以初始化操作预留系统资源，即使后续应用容器无须使用这些资源
        
    4.  Pod的有效QoS等级适用于 `init conatiner` 和应用容器
        
    5.  资源配额和限制将根据Pod的有效资源请求值/资源限制值计算生效
        
    6.  Pod级别的cgroup将基于Pod的有效资源请求/限制，与调度机制一致
        
    
3.  `init conatiner` 不能设置readinessProbe探针，因为必须在他们成功运行后才能继续运行在Pod中定义的普通容器。在Pod重新启动时， `init conatiner` 将会重新运行，常见的Pod重启场景如下：
    
    1.  `init conatiner` 的镜像被更新时，`init conatiner` 将会重新运行，导致Pod重启。仅更新应用容器的镜像指挥使得应用容器被重启
        
    2.  Pod的infrastructure容器更新时，Pod将会重启
        
    3.  若Pod中的所有应用容器都终止了，并且 `RestartPolicy=Always`，则Pod会重启
        
    

### Pod的升级和回滚

#### Deployment的升级

以Deployment nginx为例：

nginx-deployment.yaml

```
apiVersion: v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  replicas: 3
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
        - containerPort: 80
```

现在Pod镜像需要被更新为 `Nginx:1.9.1`，我们可以通过 `kubectl set image` 命令为Deplyment设置新的镜像名称： `kubectl set image deployment/nginx-deployment nginx=nginx:1.9.1`

另一种更新的方法是使用 `kubectl edit` 命令修改Deployment的配置，将 `spec.template.spec.containers[0].image` 从 `Nginx:1.7.9` 更改为 `Nginx:1.9.1` : `kubectl edit deployment/nginx-deployment`

更新的流程，初始创建Deployment时，系统创建了一个ReplicaSet，并按照用户的需求创建了3个Pod副本，当更新Deployment时，系统创建了一个新的ReplicaSet，并将其副本数量扩展到1，然后将旧的ReplicaSet缩减为2.之后继续按照相同的更新策略对新旧两个ReplicaSet进行逐个调整。最后，新的ReplicaSet运行了3个新版本Pod副本，旧的ReplicaSet副本数量则缩减为0.如图所示

![Snipaste 2021 11 21 16 10 38](http://korov.myqnapcloud.cn:19000/images/Snipaste_2021-11-21_16-10-38.png)

在整个升级的过程中，系统会保证至少有两个Pod可用，并且最多同时运行4个Pod，这是Deployment通过复杂的算法完成的。Deployment需要确保在整个更新过程中只有一定数量的Pod可能处于不可用状态。在默认情况下，Deployment确保可用的Pod总数至少为所需的副本数量减1，也就是最多1个不可用（maxUnavailable=1）。Deployment还需要确保在整个更新过程中Pod的总数最多比所需的Pod数量多1个，也就是最多1个浪涌值（maxSurge=1）。Kubernetes 1.6版本开始，maxUnavailable和maxSurge的默认值将从1，1更新为所需副本数量的25%，25%。

在Deployment的定义中可以通过 `spec.strategy` 指定Pod的更新策略，目前支持两种策略： Recreate（重建）和RollingUpdate（滚动更新），默认值为RollingUpdate。

-   Recreate： 设置 `spec.strategy.type=Recreate`， 表示Deployment在更新Pod时，会先杀掉所有正在运行的Pod，然后创建新的Pod
    
-   RollingUpdate： 设置 `spec.strategy.type=RollingUpdate`，表示Deployment会以滚动更新的方式来逐个更新Pod。同时，可以通过设置 \`spec.strategy.rollingUpdate\`下的两个参数（maxUnavaliable和maxSurge）来控制滚动更新的过程
    

下面对滚动更新时两个主要参数的说明如下：

-   spec.strategy.rollingUpdate.maxUnavailable: 用于指定Deployment在更新过程中不可用状态的Pod数量的上限。该maxUnavailable的数值可以时绝对值或Pod期望的副本数的百分比，如果被设置为百分比，那么系统会先以向下取整的方式计算出绝对值（整数）。而当另一个参数maxSurge被设置为0时，maxUnavailable则必须设置为绝对数值大于0.
    
-   spec.strategy.rollingUpdate.maxSurge: 用于指定Deployment更新Pod的过程中Pod总数超过Pod期望副本数部分的最大值。该maxSurge的数值可以时绝对值或Pod期望副本数的百分比。如果设置为百分比，那么系统会先按照向上取整的方式计算出绝对数值。
    

这里需要注意多重更新（Rollover）的情况，如果Deployment的上一次更新正在进行，此时用户再次发起Deployment的更新操作，那么Deployment会为每一次更新都创建一个ReplicaSet，而每次在新的ReplicaSet创建成功后，会逐个增加Pod副本数，同时将之前正在扩容的ReplicaSet停止扩容，并将其加入旧版本ReplicaSet列表中，然后开始缩容至0的操作。

#### Deployment的回滚

在默认情况下，所有Deployment的发布历史记录都被保留在系统中，以便我们随时进行回滚（可以配置历史记录数量）。

为了回滚到之前稳定版本的Deployment，首先用 `kubectl rollout history` 命令检查这个Deployment部署的历史记录：

```
kubectl rollout history deployment/nginx-deployment
# 查看特定版本的详细信息
kubectl rollout history deployment/nginx-deployment --revision=3
# 回滚到上一个部署版本
kubectl rollout undo deployment/nginx-deployment
# 回滚到指定版本
kubectl rollout undo deployment/nginx-deployment --to-revision=2
```

<table><tbody><tr><td><i title="Note"></i></td><td>在创建Deployment时使用 <code>--record</code> 参数，就可以在 <code>CHANGE-CAUSE</code> 列看到每个版本使用的命令了。另外Deployment的更新操作是在Deployment进行部署（Rollout）时被触发的，这意为者当且仅当Deployment的Pod模板（即spec.template）被更改时才会创建新的修订版本，例如更新模板标签或容器镜像。其他更新操作（如扩展副本数）将不会触发Deployment的更新操作，这也意味着我们将Deployment回滚到之前的版本时，只有Deployment的Pod模板部分会被修改。</td></tr></tbody></table>

#### 暂停和恢复Deployment的部署操作，以完成复杂的修改

对于一次复杂的Deployment配置修改，为了避免频繁触发Deployment的更新操作，可以先暂停Deployment的更新操作，然后进行配置修改，再恢复Deployment，一次性触发完整的更新操作，就可以避免不必要的Deployment更新操作了。

```
# 暂停Deployment的更新操作
kubectl rollout pause deployment/nginx-deployment
# 修改Deployment的镜像信息
kubectl set image deploy/nginx-deployment nginx=nginx:1.9.1
# 恢复这个Deployment的部署操作
kubectl rollout resume deploy nginx-deployment
```

#### 使用kubectl rolling-update 命令完成RC的滚动升级

对于RC的滚动升级，Kubernetes还提供了一个 \`kubectl rolling-update\`命令进行实现。该命令创建了一个新的RC，然后自动控制旧的RC中的Pod副本数量逐渐减少到0，同时新的RC中的Pod副本数量从0逐步增加到目标值，来完成Pod的升级。需要注意的是，系统要求新的RC与旧的RC都在相同的命名空间内。

#### 其他管理对象的更新策略

Kubernetes从1.6版本开始，对DaemonSet和StatefulSet的更新策略也引入类似与Deployment的滚动升级，通过不同的策略自动完成应用的版本升级。

##### DaemonSet

目前DaemonSet的升级策略包括两种： OnDelete和RollingUpdate

1.  OnDelete： DaemonSet的默认升级策略，在创建好新的DaemonSet配置之后，新的Pod并不会被自动创建，直到用户手动删除旧版本的Pod，才触发新建操作
    
2.  RollingUpdate： 旧版本的Pod将被自动杀掉，然后自动创建新版本的DaemonSet Pod，整个过程与普通Deployment的滚动升级一样是可控的。不过有两点不同于普通Pod的滚动升级：一是目前Kubernetes还不支持查看和管理DaemonSet的更新历史记录；二是DaemonSet的回滚（Rollback）并不能如同Deployment一样直接通过 \`kubectl rollback\`命令实现，必须通过再次提交旧版本的配置方式实现。
    

##### StatefulSet的更新策略

Kubernetes从1.6版本开始，针对StatefulSet的更新策略主键向Deployment和DaemonSet的更新策略看齐，也将实现RollingUpdate、Paritioned和OnDelete这几种更新策略，

### Pod的扩缩容

Pod的扩缩容操作有手动和自动两种模式，手动模式通过执行 `kubectl scale` 命令或通过RESTful API对一个Deployment/RC进行Pod副本数量的设置，即可一键完成。自动模式则需要用户更具某个性能指标或者自定义业务指标，并指定Pod副本数量的范围，系统将自动在这个范围内根据性能指标的变化进行调整

#### 自动扩缩容机制

`Horizontal Pod AutoScaler(HPA)` 控制器，用于实现基于CPU使用率进行自动Pod扩缩容的功能。HPA控制器基于Master的 `kube-controller-manager` 服务启动参数 `--horizontal-pod-autoscaler-sync-period` 定义的探测周期（默认值为15s），周期性的监测目标Pod的资源性能指标，并与HPA资源对象中的扩缩容条件进行对比，在满足条件时对Pod副本数量进行调整。

HPA的工作原理：Kubernetes中的某个Metrics Server（Heapster或自定义Metrics Server）持续采集所有Pod副本的指标数据。HPA控制器通过Metrics Server的API（Heapster的API或聚合API）获取这些数据，基于用户定义的扩缩容规则进行计算，得到目标Pod副本数量。当目标副本数量与当前副本数量不同时，HPA控制器就向Pod的副本控制器发起scale操作。

**指标的类型**

Master的kube-controller-manager服务持续监测目标Pod的某种性能指标，以计算是否需要调整副本数量。目前Kubernetes支持的指标类型如下：

-   Pod资源使用率：Pod级别的性能指标，通常是一个比率值，例如CPU使用率
    
-   Pod自定义指标：Pod级别的性能指标，通常是一个数值，例如接收请求数量
    
-   Object自定义指标或外部自定义指标：通常是一个数值，需要容器应用以某种方式提供
    

扩缩容算法详解：Autoscaler控制器从聚合API获取到Pod性能指标数据之后，基于下面的算法计算出目标Pod副本数量，与当前运行的Pod副本数量进行对比，决定是否需要进行扩缩容操作： `desiredReplicas = ceil[currentPeplicas * ( currentMetricValue / desiredMetricValue)]`

即当前副本数\*（当前指标值/期望的指标值），将结果向上取整。

此外，存在几种Pod异常的情况，如下所述：

-   Pod正在被删除（设置了删除时间戳）：将不会计入目标Pod副本数量
    
-   Pod的当前指标值无法获得：本次探测不会将这个Pod纳入目标Pod副本数量，后续的探测会被重新纳入计算范围
    
-   如果指标类型是CPU使用率，则对于正在启动但是还未达到Ready状态的Pod，也暂时不会纳入目标副本数量范围。可以通过 kubectl-controller-manager 服务的启动参数 `--horizontal-pod-autoscaler-initial-readiness-delay` 设置首次探测Pod是否Ready的延时时间，默认值为30s。另一个启动参数 `--horizontal-pod-autoscaler-cpuinitialization-period` 设置首次采集Pod的CPU使用率的延时时间
    

### 使用StatefulSet搭建MongoDB集群

本节以MongoDB为例，使用StatefulSet完成MongoDB集群的创建，为每个MongoDB实例在共享存储中（这里采用GlusterFS）都申请一片存储空间，以实现一个无单点故障、高可用、可动态扩展的MongoDB集群。

![Snipaste 2021 11 22 16 40 19](http://korov.myqnapcloud.cn:19000/images/Snipaste_2021-11-22_16-40-19.png)

#### 前提条件

在创建StatefulSet之前，需要确保在Kubernetes集群中管理员已经创建好共享存储，并能够与StorageClass对接，以实现动态存储供应的模式。

#### 创建StatefulSet

为了完成MongoDB集群的搭建，需要创建如下三个资源对象

-   一个StorageClass，用于StatefulSet自动为各个应用Pod申请PVC
    
-   一个Headless Service，用于维护MongoDB集群的状态
    
-   一个StatefulSet
    

首先创建一个StorageClass对象

storageclass-fast.yaml

```
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: fast
provisioner: kubernetes.io/glusterfs
parameters:
  resturl: "http://<heketi-rest-url>"
```

执行 `kubectl create` 命令创建该StorageClass： `kubectl create -f storageclass-fast.yaml`

接下来，创建对应的Headless Service。

`mongo-sidecar` 作为MongoDB集群的管理者，将使用此Headless Service来维护各个MongoDB实例之间的集群关系，以及集群规模变化时自动更新。

mongo-headless-service.yaml

```
apiVersion: v1
kind: Service
metadata:
  name: mongo
  labels:
    name: mongo
spec:
  ports:
  - port: 27017
    targetPort: 27017
  clusterIp: None
  selector:
    role: mongo
```

使用 `kubectl create` 命令创建该Service： `kubectl create -f mongo-headless-service.yaml`

最后，创建MongDB StatefulSet

statefulset-mongo.yaml

```
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mongo
spec:
  serviceName: "mongo"
  replicas: 3
  template:
    metadata:
      labels:
        role: mongo
        environment: test
    spec:
      terminationGracePeriodSeconds: 10
      containers:
      - name: mongo
        image: mongo
        command:
        - mongod
        - "--replSet"
        - rs0
        - "--smallfiles"
        - "--noprealloc"
        ports:
        -containerPort: 27017
        volumeMounts:
        - name: mongo-persistent-storage
          mountPath: /data/db
      - name: mongo-sidecar
        image: cvallance/mongo-k8s-sidecar
        env:
        - name: MONGO_SIDECAR_POD_LABELS
          value: "role=mongo,environment=test"
        - name: KUBERNETES_MONGO_SERVICE_NAME
          value: "mongo"
    volumeClainTemplates:
    - metadata:
        name: mongo-persistent-storage
        annotations:
          volume.beta.kubernetes.io/storage-class: "fast"
      spec:
        accessModes: ["ReadWriteOnce"]
        resources:
          requests:
            storage: 100Gi
```

其中的主要配置说明如下：

1.  在该StatefulSet的定义中包括两个容器： mongo和mongo-sidecar。mongo时主服务程序，mongo-sidecar是将多个mongo实例进行集群设置的工具。mongo-sidecar中的环境变量如下：
    
    1.  MONGO\_SIDECAR\_POD\_LABELS：设置为mongo容器的标签，用于sidecar查询它所需要管理的MongoDB集群实例
        
    2.  KUBERNETES\_MONGO\_SERVICE\_NAME： 它的值为mongo，表示sidecar将使用mongo这个服务名来完成MongoDB集群的设置。
        
    
2.  replicas=3表示这个MongoDB集群由3个mongo实例组成
    
3.  volumeClainTemplates是StatefulSet最重要的存储设置。 `annotations` 设置为 `volume.beta.kubernetes.io/storage-class: "fast"` 表示使用名为fast的StorageClass自动为每个mongo Pod实例分配后端存储。 `resources.requests.storage=100Gi` 表示为每个mongo实例都分配100GiB的磁盘空间。
    

使用 `kubectl create` 命令创建这个StatefulSet： `kubectl create -f statefulset-mongo.yaml`

## 深入掌握Service

### Service定义详解

```
apiVersion: v1   // Required
kind: Service    // Required
metadata:
  name: String   // Required
  namespace: String  // Required
  labels:
    - name: string
  annotations:
    - name: string
spec:            // Required
  selector: []   // Required
  type: string   // Required
  clusterIP: string
  sessionAffinity: string
  ports:
  - name: string
    protocol: string
    port: int
    targetPort: int
    nodePort: int
  status:
    loadBalancer:
      ingress:
        ip: string
        hostname: string
```

Table 1. 属性说明    
| 属性名称 | 取值类型 | 是否必选 | 取值说明 |
| --- | --- | --- | --- |
| 
version

 | 

string

 | 

Required

 | 

v1

 |
| 

kind

 | 

string

 | 

Required

 | 

Service

 |
| 

metadata

 | 

object

 | 

Required

 | 

元数据

 |
| 

metadata.name

 | 

string

 | 

Required

 | 

Service名称，需符合RFC 1035规范

 |
| 

metadata.namespace

 | 

string

 | 

Required

 | 

命名空间，不指定系统时间将使用default的命名空间

 |
| 

metadata.labels\[\]

 | 

list

 |  | 

自定义标签属性列表

 |
| 

metadata.annotation\[\]

 | 

list

 |  | 

自定义注解属性列表

 |
| 

spec

 | 

object

 | 

Required

 | 

详细描述

 |
| 

spec.selector\[\]

 | 

list

 | 

Required

 | 

Label Selector配置，将选择具有指定Label标签的Pod作为管理范围

 |
| 

spec.type

 | 

string

 | 

Required

 | 

Service的类型，指定Service的访问方式，默认值为ClusterIP。

 |
| 

spec.clusterIP

 | 

string

 |  | 

虚拟服务IP地址，当type=ClusterIP时，如果不指定，则系统进行自动分配，也可以手工指定；当type=LoadBalancer时，则需要指定

 |
| 

spec.sessionAffinity

 | 

string

 |  | 

是否支持Session，可选值为ClientIP，默认值为空。ClientIP：表示将同一个客户端（根据客户端的IP地址决定）的访问请求都转发到同一个后端Pod

 |
| 

spec.ports\[\]

 | 

list

 |  | 

Service需要暴露的宽口列表

 |
| 

spec.ports\[\].name

 | 

string

 |  | 

端口名称

 |
| 

spec.ports\[\].protocol

 | 

string

 |  | 

端口协议，支持TCP和UDP，默认值为TCP

 |
| 

spec.ports\[\].port

 | 

int

 |  | 

服务监听的端口号

 |
| 

spec.ports\[\].targetPort

 | 

int

 |  | 

需要转发到后端Pod的端口号

 |
| 

spec.ports\[\].nodePort

 | 

int

 |  | 

当spec.type=NodePort时，指定映射到物理机的端口号

 |
| 

Status

 | 

object

 |  | 

当spec.type=LoadBalancer时，设置外部负载均衡器的地址，用于公有云环境

 |
| 

status.loadBalancer

 | 

object

 |  | 

外部负载均衡器

 |
| 

status.loadBalancer.ingress

 | 

object

 |  | 

外部负载均衡器

 |
| 

status.loadBalancer.ingress.ip

 | 

string

 |  | 

外部负载均衡器的IP地址

 |
| 

status.loadBalancer.ingress.hostname

 | 

string

 |  | 

外部负载均衡器的主机名

 |