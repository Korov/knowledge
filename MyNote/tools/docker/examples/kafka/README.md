 kafka监控界面

```bash
docker run -d --name=kafkaUI -p 18889:8889 korov/kafka-ui:1.1.3
```

登录`localhost:18889`就可以访问界面了