[kafka-UI](https://hub.docker.com/r/korov/kafka-ui)

kafka监控界面

```
docker run -d --name=kafkaUI -p 8889:8889 freakchicken/kafka-ui-lite:1.2.11
```

登录`localhost:18889`就可以访问界面了