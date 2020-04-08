#  使用docker搭建git服务器

```bash
docker pull gogs
docker run `pwd`/data:/data -p 127.0.0.110022:22 -p 3000:3000 gogs/gogs 
```

