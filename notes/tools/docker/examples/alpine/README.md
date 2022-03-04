 启动镜像

```
docker run --name alpine -it alpine:3.13.0 sh
```

更换国内源

```bash
sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories
```

设置语言和时区

```
apk add --no-cache tzdata
```

