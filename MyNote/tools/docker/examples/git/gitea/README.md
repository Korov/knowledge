 安装

```bash
docker run -d --restart=always --name=gitea -p 10022:22 -p 13000:3000 -v `pwd`/gitea:/data gitea/gitea:1.14.2

docker run -d --name=gitea -p 10022:22 -p 13000:3000 gitea/gitea:1.14.2

# 支持asciidoc
docker exec -it --user root gitea bash
# 进入容器之后安装渲染asciidoc渲染器
apk --no-cache add asciidoctor
# 在 /data/gitea/conf/app.ini 中添加如下内容
[markup.asciidoc]
ENABLED = true
FILE_EXTENSIONS = .adoc,.asciidoc
RENDER_COMMAND = "asciidoctor -s -a showtitle --out-file=- -"
; Input is not a standard input but a file
IS_INPUT_FILE = false
# 重启就可以了
docker restart gitea
```

在配置界面中执行以下步骤：

- `SSH Server Domain`中的localhost修改为服务器的ip
- `SSH Server Port`修改为10022
- `Gitea Base URL`中的localhost修改为服务器ip，3000修改为13000

访问界面：`localhost:13000`

帐号密码：`korov/korov`

