# CI/CD是什么

CI(Continuous integration，中文意思是持续集成)是一种软件开发时间。持续集成强调开发人员提交了新代码之后，立刻进行构建、（单元）测试。根据测试结果，我们可以确定新代码和原有代码能否正确地集成在一起。

CD(Continuous Delivery， 中文意思持续交付)是在持续集成的基础上，将集成后的代码部署到更贴近真实运行环境(类生产环境)中。比如，我们完成单元测试后，可以把代码部署到连接数据库的Staging环境中更多的测试。如果代码没有问题，可以继续手动部署到生产环境。

# jenkins安装

此处使用docker安装

```bash
docker pull jenkinsci/blueocean

docker run \
  --name jenkins-blueocean \
  -u root \
  -d \
  -p 8080:8080 \
  -p 50000:50000 \
  -v $PWD/jenkins-data:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkinsci/blueocean

docker stop jenkins-blueocean
docker rm jenkins-blueocean
```

启动完成之后需要到`jenkins-data`中修改`hudson.model.UpdateCenter.xml`，将`https://updates.jenkins-ci.org/update-center.json`修改成`https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json`。重启容器，等待一会就可以了。