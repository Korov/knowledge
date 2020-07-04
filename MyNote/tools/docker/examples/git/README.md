#  使用docker搭建gitlab服务器

```bash
docker pull gitlab/gitlab-ce
docker run -d  -p 443:443 -p 80:80 -p 22:22 --name gitlab -v `pwd`/config:/etc/gitlab -v `pwd`/logs:/var/log/gitlab -v `pwd`/data:/var/opt/gitlab gitlab/gitlab-ce
```

修改`nvim ./config/gitlab.rb`文件

```bash
#192.168.31.88为宿主机ip地址
external_url 'http://192.168.31.88'

gitlab_rails['gitlab_ssh_host'] = '192.168.31.88'
gitlab_rails['gitlab_shell_ssh_port'] = 22
```

修改完成后`docker exec -it gitlab bash`进入容器重新配置容器`gitlab-ctl reconfigure`.

浏览器访问`http://localhost:80`,重置密码,用户名为root，密码重置为root1234
