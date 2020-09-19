在服务器上启动`scrapyd`，需要修改`/usr/local/lib/python3.6/site-packages/scrapyd/default_scrapyd.conf`中的`bind_address`为`0.0.0.0`方便外网用户登录

在本地机器中执行一下命令`scrapyd-deploy mingyan -p mingyan -v v1`将项目部署到服务器上

通过一下命令执行对应的爬虫`curl http://172.16.193.141:6800/schedule.json -d project=mingyan -d spider=mingyan`