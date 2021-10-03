#  Filebeat使用

## 安装filebeat

```bash
curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.15.0-linux-x86_64.tar.gz
tar -zvx -f filebeat-7.15.0-linux-x86_64.tar.gz -C ./
```

## 日志内容和对应的pattern

日志：

```
2021-09-30 17:06:54,001 DEBUG Thread-3 sql.ResultSet: {conn-10013, pstmt-27245, rs-69156} Result: [1245, e76027db1ea94a2e821d264591454a93, 612, WAF发现PHP加密WebShell上传, 1, 0, null, null, 0, 0, 1, , , 2021-07-29 16:53:24.0, 2021-07-30 14:28:33.0, ( (appname:waf) AND (json.waf_type:禁止PHP加密webshell上传) ) | fields json.waf_src_ip, json.waf_dst_ip | rename json.waf_src_ip as src_ip, json.waf_dst_ip as dst_ip, | eval threat_classif = "" | eval extend_threat_classif = "" | eval threat_stage = 0 | eval threat_state = 0 | eval threat_level = 1 | eval att_ck_stage = 0 | eval ttp_no = "" | eval ttp_desc = "" | eval __inner_alert__ = 0 | eval __inner_event__ = 1 | eval desc = src_ip + dst_ip| eval rule_name = "WAF发现PHP加密WebShell上传", null]
```

pattern，测试用的网址:`http://grokdebug.herokuapp.com/`

```
%{LOGTIME:log_time} %{LOGLEVEL:log_level} %{THREATNAME:thread} %{CLASSNAME:java_class}: %{GREEDYDATA:log_content}
```

## filebeat直接发送数据到ES

```bash
cd filebeat-7.15.0-linux-x86_64
vim filebeat.yml
# 设置output.elasticsearch的host为elasticsearch的host和port
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

### 配置pipeline

```json
PUT /_ingest/pipeline/siem-log1 HTTP/1.1
Host: 192.168.50.189:9200
Content-Type: application/json
{
  "description" : "siem-log1",
  "processors": [
    {
      "grok": {
        "field": "message",
        "patterns": ["%{LOGTIME:log_time} %{LOGLEVEL:log_level} %{THREATNAME:thread} %{CLASSNAME:java_class}: %{GREEDYDATA:log_content}"]
      }
    },
    {
        "date": {
            "field": "log_time",
            "formats": ["yyyy-MM-dd HH:mm:ss,SSS"],
            "timezone": "Asia/Shanghai",
            "target_field": "@timestamp"
        }
    }
  ]
}
```

### 配置filebeat

`filebeat.yml`文件

```yaml
filebeat.inputs:
- type: log
  # 是否启动
  enabled: true
  encoding: "utf-8"
  # 从那个路径收集日志，如果存在多个 input ,则这个 paths 中的收集的日志最好不要重复，否则会出现问题
  # 日志路径可以写通配符
  paths:
    - "/Users/huan/soft/elastic-stack/filebeat/filebeat/springboot-admin.log"
  # 如果日志中出现了 DEBUG 的字样，则排除这个日志
  exclude_lines:
    - "DEBUG"
  # 添加自定义字段
  fields:
    "application-servic-name": "admin"
  # fields 中的字段不放在根级别 ，true表示放在根级别
  fields_under_root: false
  # 添加一个自定义标签
  tags:
    - "application-admin"
  # 多行日志的处理，比如java中的异常堆栈
  multiline:
    # 正则表达式
    pattern: '^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}'
    # 是否开启正则匹配，true:开启，false:不开启
    negate: true
    # 不匹配正则的行是放到匹配到正则的行的after(后面)还是before(前面)，此处配置不是时间开头的就是下一行
    match: after
    # 多行日志结束的时间，多长时间没接收到日志，如果上一个是多行日志，则认为上一个结束了
    timeout: 2s
  # 使用es的ignes node 的pipeline处理数据，这个理论上要配置到output.elasticsearch下方，但是测试的时候发现配置在output.elasticsearch下方不生效。
  pipeline: pipeline-filebeat-springboot-admin
  
# 配置索引模板的名字和索引模式的格式
setup.template.enabled: false
setup.template.name: "template-springboot-admin"
setup.template.pattern: "springboot-admin-*"

# 索引的生命周期，需要禁用，否则可能无法使用自定义的索引名字
setup.ilm.enabled: false

# 数据处理，如果我们的数据不存在唯一主键，则使用fingerprint否则可以使用add_id来实现
processors:
  # 指纹，防止同一条数据在output的es中存在多次。（此处为了演示使用message字段做指纹，实际情况应该根据不用的业务来选择不同的字段）
  - fingerprint:
      fields: ["message"]
      ignore_missing: false
      target_field: "@metadata._id"
      method: "sha256"

# 输出到es中
output.elasticsearch:
  # es 的地址
  hosts: ["192.168.50.189:9200"]
  username: "elastic"
  password: "123456"
  # 输出到那个索引，因为我们这个地方自定义了索引的名字，所以需要下方的 setup.template.[name|pattern]的配置
  index: "springboot-admin-%{[agent.version]}-%{+yyyy.MM.dd}"
  # 是否启动
  enabled: true
```

### 启动filebeat

```bash
./filebeat -e -c filebeat.yml -d "publish"
```

如果Kibana的Discover界面看不到索引需要到index pattern中配置索引匹配规则

## 使用logstash

### 配置filebeat

`filebeat.yml`文件

```bash
filebeat.inputs:
- type: log
  enabled: true
  encoding: "utf-8"
  paths:
    - "/root/yotta_siem.log"
  fields:
    "application-servic-name": "siem"
  fields_under_root: false
  tags:
    - "application-siem"
  multiline:
    pattern: '^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}'
    negate: true
    match: next

processors:
  - fingerprint:
      fields: ["message"]
      ignore_missing: false
      target_field: "@metadata._id"
      method: "sha256"

output.logstash:
  hosts: ["192.168.50.189:5044"]
```

### 配置logstash

### `/usr/share/logstash/pipeline/logstash.conf`

```
input {
  beats {
    port => 5044
  }
}

filter {
    grok {
        # 自己定义正则匹配格式，在这里路径下创建文件siem，然后输入内容  POSTFIX_QUEUEID [0-9A-F]{10,11}， 使用的时候就可以类似 %{POSTFIX_QUEUEID:queue_id}
        patterns_dir => ["/usr/share/logstash/patterns"]
        match => { "message" => "%{LOGTIME:log_time} %{LOGLEVEL:log_level} %{THREATNAME:thread} %{CLASSNAME:java_class}: %{GREEDYDATA:log_content}" }
        overwrite => ["message"]
    }
    date {
        match => [ "log_time", "yyyy-MM-dd HH:mm:ss,SSS" ]
        timezone => "Asia/Shanghai"
      }
}

output {
  elasticsearch {
    hosts => ["http://192.168.50.189:9200"]
    index => "siem-log2-%{+YYYY.MM.dd}"
  }
}
```

### 启动logstash和filebeat

本地启动logstash：`logstash -r -f /usr/share/logstash/config/logstash-siem.conf --path.data /usr/share/logstash/data_siem`

docker修改logstash需要修改`/usr/share/logstash/pipeline/logstash.conf`，然后重启docker

## 自我总结

### 重新发送

想要重新发送数据需要把`./data`目录都删掉，里面有`registry`记录了发送信息

### 使用自定义index name

需要在filebeat.yml中禁止使用自动命名

```yaml
setup.ilm.enabled: false
```

