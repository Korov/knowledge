 安装

```bash
docker run -d --restart=always --name=gitea -p 10022:22 -p 10080:3000 -v `pwd`/gitea:/data gitea/gitea:1.13.1
```

访问界面：`localhost:10080`

