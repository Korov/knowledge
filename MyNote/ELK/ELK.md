#  Filebeat

## 安装Filebeat

```bash
curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.15.0-linux-x86_64.tar.gz
tar -zvx -f filebeat-7.15.0-linux-x86_64.tar.gz -C ./
```

链接Elastic Stack和Kibana

```bash
cd filebeat-7.15.0-linux-x86_64
vim filebeat.yml
# 设置output.elasticsearch的host为elasticsearch的host和port
# 设置setup.kibana的host为指定的地址
```

以上数据将直接发到Elasticsearch，如果想要将数据发送到Logstash进一步处理则需要修改配置。

```
output.logstash:
      hosts: ["127.0.0.1:5044"]
```



## 指定日志文件

展示内置的模块：`./filebeat modules list`

捕获日志：

```bash
./filebeat modules enable system nginx mysql
cd modules.d
vim nginx.yml.disabled
# 做出如下修改
- module: nginx
  access:
    var.paths: ["/var/log/nginx/access.log*"]
```



## 解析日志数据属性并发送到Elasticsearch

```bash
# 不知道为什么不行
./filebeat setup --dashboards

./filebeat -e -c filebeat.yml -d "publish"
```



## 在Kibana中可视化日志数据

