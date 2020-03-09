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

## 常用插件

### maven-assembly-plugin

用于制作项目分发包，该分发包包含了项目的可执行文件、源代码、readme、平台脚本等。maven-assembly-plugin支持各种主流的格式如zip、tar.gz、jar和war等，具体打包哪些文件是高度可控的，例如用户可以 按文件级别的粒度、文件集级别的粒度、模块级别的粒度、以及依赖级别的粒度控制打包，此外，包含和排除配置也是支持的。maven-assembly- plugin要求用户使用一个名为assembly.xml的元数据文件来表述打包，它的single目标可以直接在命令行调用，也可以被绑定至生命周期。

### maven-dependency-plugin

maven-dependency-plugin最大的用途是帮助分析项目依赖，dependency:list能够列出项目最终解析到的依赖列表，dependency:tree能进一步的描绘项目依赖树，dependency:analyze可以告诉你项目依赖潜在的问题，如果你有直接使用到的却未声明的依赖，该目标就会发出警告。maven-dependency-plugin还有很多目标帮助你操作依赖文件，例如dependency:copy-dependencies能将项目依赖从本地Maven仓库复制到某个特定的文件夹下面。

### maven-enforcer-plugin

在一个稍大一点的组织或团队中，你无法保证所有成员都熟悉Maven，那他们做一些比较愚蠢的事情就会变得很正常，例如给项目引入了外部的 SNAPSHOT依赖而导致构建不稳定，使用了一个与大家不一致的Maven版本而经常抱怨构建出现诡异问题。maven-enforcer- plugin能够帮助你避免之类问题，它允许你创建一系列规则强制大家遵守，包括设定Java版本、设定Maven版本、禁止某些依赖、禁止 SNAPSHOT依赖。只要在一个父POM配置规则，然后让大家继承，当规则遭到破坏的时候，Maven就会报错。除了标准的规则之外，你还可以扩展该插 件，编写自己的规则。maven-enforcer-plugin的enforce目标负责检查规则，它默认绑定到生命周期的validate阶段。

### maven-help-plugin

maven-help-plugin是一个小巧的辅助工具，最简单的help:system可以打印所有可用的环境变量和Java系统属性。help:effective-pom和help:effective-settings最 为有用，它们分别打印项目的有效POM和有效settings，有效POM是指合并了所有父POM（包括Super POM）后的XML，当你不确定POM的某些信息从何而来时，就可以查看有效POM。有效settings同理，特别是当你发现自己配置的 settings.xml没有生效时，就可以用help:effective-settings来验证。此外，maven-help-plugin的describe目标可以帮助你描述任何一个Maven插件的信息，还有all-profiles目标和active-profiles目标帮助查看项目的Profile。

### maven-release-plugin

maven-release-plugin的用途是帮助自动化项目版本发布，它依赖于POM中的SCM信息。release:prepare用来准备版本发布，具体的工作包括检查是否有未提交代码、检查是否有SNAPSHOT依赖、升级项目的SNAPSHOT版本至RELEASE版本、为项目打标签等等。release:perform则 是签出标签中的RELEASE源码，构建并发布。版本发布是非常琐碎的工作，它涉及了各种检查，而且由于该工作仅仅是偶尔需要，因此手动操作很容易遗漏一 些细节，maven-release-plugin让该工作变得非常快速简便，不易出错。maven-release-plugin的各种目标通常直接在 命令行调用，因为版本发布显然不是日常构建生命周期的一部分。

### maven-resources-plugin

为了使项目结构更为清晰，Maven区别对待Java代码文件和资源文件，maven-compiler-plugin用来编译Java代码，maven-resources-plugin则用来处理资源文件。默认的主资源文件目录是src/main/resources，很多用户会需要添加额外的资源文件目录，这个时候就可以通过配置maven-resources-plugin来实现。此外，资源文件过滤也是Maven的一大特性，你可以在资源文件中使用${propertyName}形式的Maven属性，然后配置maven-resources-plugin开启对资源文件的过滤，之后就可以针对不同环境通过命令行或者Profile传入属性的值，以实现更为灵活的构建。

### maven-surefire-plugin

然而在当你想要跳过测试、排除某些 测试类、或者使用一些TestNG特性的时候，了解maven-surefire-plugin的一些配置选项就很有用了。例如 mvn test -Dtest=FooTest 这样一条命令的效果是仅运行FooTest测试类，这是通过控制maven-surefire-plugin的test参数实现的。

