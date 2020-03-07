# maven教程

## maven约定配置

| 目录                               | 目的                                                         |
| :--------------------------------- | :----------------------------------------------------------- |
| ${basedir}                         | 存放pom.xml和所有的子目录                                    |
| ${basedir}/src/main/java           | 项目的java源代码                                             |
| ${basedir}/src/main/resources      | 项目的资源，比如说property文件，springmvc.xml                |
| ${basedir}/src/test/java           | 项目的测试类，比如说Junit代码                                |
| ${basedir}/src/test/resources      | 测试用的资源                                                 |
| ${basedir}/src/main/webapp/WEB-INF | web应用文件目录，web项目的信息，比如存放web.xml、本地图片、jsp视图页面 |
| ${basedir}/target                  | 打包输出目录                                                 |
| ${basedir}/target/classes          | 编译输出目录                                                 |
| ${basedir}/target/test-classes     | 测试编译输出目录                                             |
| Test.java                          | Maven只会自动运行符合该命名规则的测试类                      |
| ~/.m2/repository                   | Maven默认的本地仓库目录位置                                  |

## maven pom

POM（项目对象模型，项目对象模型）是Maven工程的基本工作单元，是一个XML文件，包含了项目的基本信息，用于描述项目如何构造，声明项目依赖，等等。

执行任务或目标时，Maven会在当前目录中查找POM。它读取POM，获取所需的配置信息，然后执行目标。

POM中可以指定以下配置：

- 项目依赖
- 插件
- 执行目标
- 项目建成的资料
- 项目版本
- 项目开发者列表
- 相关邮件列表信息

所有POM文件都需要项目元素和三个必需长度：groupId，artifactId，版本。

## 父（超级）POM

父（超级）POM是Maven默认的POM。所有的POM都继承自一个父POM（无论是否显式定义了这个父POM）。下载POM中的依赖时，它会到Super POM中配置的替代仓库http://repo1.maven.org/maven2去下载。

Maven使用有效的pom（超级pom加上工程自己的配置）来执行相关的目标，它帮助开发者在pom.xml中做替换的配置，当然这些配置可以被替换。

使用以下命令来查看Super POM默认配置：`mvn help：effective-pom`，之后maven将会开始处理并显示有效的pom。

## maven建立生命周期

开始-》validate-》compile-》test-》package-》verify-》install-》deploy-》结束

| 阶段 | 处理     | 描述                                                     |
| :--- | :------- | :------------------------------------------------------- |
| 验证 | 验证项目 | 验证项目是否正确且所有必须信息是可用的                   |
| 编译 | 执行编译 | 源代码编译在此阶段完成                                   |
| 测试 | 测试     | 使用适当的单元测试框架（例如JUnit）运行测试。            |
| 包装 | 打包     | 创建JAR / WAR包如在pom.xml中定义引用的包                 |
| 检查 | 检查     | 对集成测试的结果进行检查，以保证质量达标                 |
| 安装 | 安装     | 安装打包的项目到本地仓库，以供其他项目使用               |
| 部署 | 部署     | 复制最终的工程包到远程仓库中，以共享给其他开发人员和工程 |

Maven具有以下三个标准的生命周期：

- **clean**：项目清理的处理
- **default（或build）**：项目部署的处理
- **站点**：项目站点文档创建的处理

## Site 生命周期

Maven Site 插件一般用来创建新的报告文档、部署站点等。

- pre-site：执行一些需要在生成站点文档之前完成的工作
- site：生成项目的站点文档
- post-site： 执行一些需要在生成站点文档之后完成的工作，并且为部署做准备
- site-deploy：将生成的站点文档部署到特定的服务器上

# maven常用命令

## mvn compile

编译源代码

## mvn test

运行测试，执行完成后会在`target/surefire-reports`目录下生成测试报告。

## mvn test-compile

编译测试代码

## mvn package、mvn install

都是用来打包的，区别在于install会把打好的包放入本地maven仓库，package不会

## mvn clean

清理已经打好的包，通常使用方式：`mvn clean package`

跳过测试类：`mvn clean package -DskipTests`、`mvn clean package -Dmaven.test.skip=true`（不推荐）

## mvn deploy

上传到私服

## mvn site

Maven Site 插件一般用来创建新的报告文档、部署站点等。

```xml
<plugin>
    <artifactId>maven-site-plugin</artifactId>
    <version>3.8.2</version>
</plugin>
```

生成的文件在`target/site`。

# maven中RELEASE和SNAPSHOT的区别

maven中的仓库分为两种SNAPSHOT快照仓库和RELEASE发布仓库。快照仓库用于保存开发过程中不稳定版本号，发布仓库用来保存稳定的发行版本号。

maven会根据模块的版本号(pom文件中的version)中是否带有-SNAPSHOT来判断是快照版本还是正式版本。如果是快照版本，那么在 mvn deploy时会自动发布到快照版本库中，而使用快照版本的模块，在不更改版本号的情况下，直接编译打包时，maven会自动从镜像服务器上下载最新的快照版本。如果是正式发布版本，那么在mvn deploy时会自动发布到正式版本库中，而使用正式版本的模块，在不更改版本号的情况下，编译打包时如果本地已经存在该版本的模块则不会主动去镜像服务 器上下载。

# 自我总结

mvn有一些自己的相关命令，但是还有好多插件可以使用，这些插件都有自己的命令，和mvn自带的命令是分开的，看自己需要什么插件可以安装相应的插件。