# maven教程

maven是用来管理包依赖的工具，jvm在运行的时候只会识别class文件，而在class文件中所有的类都是通过全限定名来确定的，当有两个类的全限定名称完全相同，就会出现依赖冲突，这是绝对不允许出现的。

jvm如何依赖，在运行java程序的时候会有classpath参数指定了程序运行所需要的所有依赖。

## maven的自动冲突解决

### 就近原则

例如A的0.1.1依赖B的0.1.1，B的0.1.1依赖C的0.2.0。同时D的0.1.1依赖C的0.1.0。此时C的0.2.0在第三层，C的0.1.0在第二层，C的0.1.0近，有先使用C的0.1.0。

### 优先声明原则

如果两个包的距离相同，那谁先声明先使用谁。

### 常见的包冲突异常

- AbstractMethodError
- NoClassDefFoundError
- ClassNotFoundException
- LinkageError

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

### clean

Clean生命周期一共包含了三个阶段：

- pre-clean 执行一些需要在clean之前完成的工作
- clean 移除所有上一次构建生成的文件
- post-clean 执行一些需要在clean之后立刻完成的工作

### Default

- validate
- generate-sources
- process-sources
- generate-resources
- process-resources   复制并处理资源文件，至目标目录，准备打包。
- compile   编译项目的源代码。
- process-classes
- generate-test-sources 
- process-test-sources 
- generate-test-resources
- process-test-resources   复制并处理资源文件，至目标测试目录。
- test-compile   编译测试源代码。
- process-test-classes 
- test   使用合适的单元测试框架运行测试。这些测试代码不会被打包或部署。
- prepare-package 
- package   接受编译好的代码，打包成可发布的格式，如 JAR 。
- pre-integration-test
- integration-test
- post-integration-test 
- verify 
- install   将包安装至本地仓库，以让其它项目依赖。
- deploy   将最终的包复制到远程的仓库，以让其它开发人员与项目共享。

### Site 生命周期

Maven Site 插件一般用来创建新的报告文档、部署站点等。

- pre-site：执行一些需要在生成站点文档之前完成的工作
- site：生成项目的站点文档
- post-site： 执行一些需要在生成站点文档之后完成的工作，并且为部署做准备
- site-deploy：将生成的站点文档部署到特定的服务器上

![maven-lifecycle](picture\maven-lifecycle.jpg)

## scope

- compile 

  > 默认scope为compile，表示为当前依赖参与项目的编译、测试和运行阶段，属于强依赖。打包之时，会达到包里去。

- test 

  > 该依赖仅仅参与测试相关的内容，包括测试用例的编译和执行，比如定性的Junit。

- runtime 

  > 依赖仅参与运行周期中的使用。一般这种类库都是接口与实现相分离的类库，比如JDBC类库，在编译之时仅依赖相关的接口，在具体的运行之时，才需要具体的mysql、oracle等等数据的驱动程序。此类的驱动都是为runtime的类库。

- provided 

  > 该依赖在打包过程中，不需要打进去，这个由运行的环境来提供，比如tomcat或者基础类库等等，事实上，该依赖可以参与编译、测试和运行等周期，与compile等同。区别在于打包阶段进行了exclude操作。

- system 

  > 使用上与provided相同，不同之处在于该依赖不从maven仓库中提取，而是从本地文件系统中提取，其会参照systemPath的属性进行提取依赖。

- import

  > 这个是maven2.0.9版本后出的属性，import只能在dependencyManagement的中使用，能解决maven单继承问题，import依赖关系实际上并不参与限制依赖关系的传递性。

## dependency中的type

引入某一个依赖时，必须指定type，这是因为用于匹配dependency引用和dependencyManagement部分的最小信息集实际上是{groupId，artifactId，type，classifier}。在很多情况下，这些依赖关系将引用没有classifier的jar依赖。这允许我们将标识设置为{groupId，artifactId}，因为type的默认值是jar，并且默认classifier为null。
type的值一般有jar、war、pom等，声明引入的依赖的类型

## dependency中的classifier

Classifier可能是最容易被忽略的Maven特性，但它确实非常重要，我们也需要它来帮助规划坐标。设想这样一个情况，有一个jar项目，就说是 dog-cli-1.0.jar 吧，运行它用户就能在命令行上画一只小狗出来。现在用户的要求是希望你能提供一个zip包，里面不仅包含这个可运行的jar，还得包含源代码和文档，换句话说，这是比较正式的分发包。这个文件名应该是怎样的呢？dog-cli-1.0.zip？不够清楚，仅仅从扩展名很难分辨什么是Maven默认生成的构件，什么是额外配置生成分发包。如果能是dog-cli-1.0-dist.zip就最好了。这里的dist就是classifier，默认Maven只生成一个构件，我们称之为主构件，那当我们希望Maven生成其他附属构件的时候，就能用上classifier。常见的classifier还有如dog-cli-1.0-sources.jar表示源码包，dog-cli-1.0-javadoc.jar表示JavaDoc包等等。

# maven常用命令

## mvn compile

编译源代码

## mvn test

运行测试，执行完成后会在`target/surefire-reports`目录下生成测试报告。

## mvn test-compile

编译测试代码

## mvn package、mvn install

mvn package只会将包大好放到代码路径下面，mvn install会将包大好放到代码路径下并且复制一份到maven的本地仓库中，放到本地仓库中之后其他项目就可以引用你发布的jar包。

## mvn clean

清理已经打好的包，通常使用方式：`mvn clean package`

跳过测试类：`mvn clean package -DskipTests`、`mvn clean package -Dmaven.test.skip=true`（不推荐）

## mvn deploy

上传到私服，首先要用docker搭建一个nexus私服。

首先需要在项目的pom文件中引入deploy插件

```xml
<plugin>
    <artifactId>maven-deploy-plugin</artifactId>
     <version>2.8.2</version>
 </plugin>
```

同时增加部署的服务器和仓库

```xml
<distributionManagement>
    <snapshotRepository>
      <id>nexus-snapshots</id>
      <url>http://localhost:8081/nexus/content/repositories/my/</url>
    </snapshotRepository>
  </distributionManagement>
```

需要在settings.xml中配置仓库的用户名和密码

```xml
<server>
      <id>nexus-snapshots</id>
      <username>admin</username>
      <password>admin23</password>
    </server>
```

之后运行`mvn deploy`就可以将插件部署到私服。

使用的时候需要增加一个插件专用的仓库

```xml
<pluginRepositories>
    <pluginRepository>
      <id>nexus-snapshots</id>
      <url>http://localhost:8081/nexus/content/repositories/my/</url>
    </pluginRepository>
  </pluginRepositories>
```



## mvn site

Maven Site 插件一般用来创建新的报告文档、部署站点等。

```xml
<plugin>
    <artifactId>maven-site-plugin</artifactId>
    <version>3.8.2</version>
</plugin>
```

生成的文件在`target/site`。

## mvn dependency

mvn dependency:tree>temp/tree.txt将项目的依赖数据打印到文件中。

# maven中RELEASE和SNAPSHOT的区别

maven中的仓库分为两种SNAPSHOT快照仓库和RELEASE发布仓库。快照仓库用于保存开发过程中不稳定版本号，发布仓库用来保存稳定的发行版本号。

maven会根据模块的版本号(pom文件中的version)中是否带有-SNAPSHOT来判断是快照版本还是正式版本。如果是快照版本，那么在 mvn deploy时会自动发布到快照版本库中，而使用快照版本的模块，在不更改版本号的情况下，直接编译打包时，maven会自动从镜像服务器上下载最新的快照版本（默认每天更新一次，可以使用mvn compile -U强制更新SNAPSHOT）。如果是正式发布版本，那么在mvn deploy时会自动发布到正式版本库中，而使用正式版本的模块，在不更改版本号的情况下，编译打包时如果本地已经存在该版本的模块则不会主动去镜像服务 器上下载。

# maven插件

插件是maven的灵魂，实际上就是一组代码。插件可以声明多个目标，称为Maven Old Java Object，同一个目标可以绑定到多个阶段上，执行多次

引入一个插件之后需要绑定到某个阶段，可以根据官方文档进行相应的配置实现定制的各种功能：

```xml
<build>
        <plugins>
            <plugin>
                <!--引入此插件-->
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.6.0</version>
                <executions>
                    <execution>
                        <id>exec1</id>
                        <!--将此插件绑定到validate阶段，即执行mvn validate时会使用此插件-->
                        <phase>validate</phase>

                        <!--执行此版本插件的exec的echo答应Hello！-->
                        <goals>
                            <goal>exec</goal>
                        </goals>
                        <configuration>
                            <executable>echo</executable>
                            <arguments>
                                <argument>Hello!</argument>
                            </arguments>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

也可以配置多个execution绑定到多个阶段。

## 查看插件的详细信息

一般都是去maven plugin官网查看插件是如何使用的但是也可以通过此命令查看插件的详细信息：`mvn help:describe -Dplugin=org.codehaus.mojo:exec-maven-plugin`。

## 插件是如何解析的

根据坐标去仓库中查询（插件仓库和仓库是不同的）；如果groupId不指定，则默认org.apache.maven.plugins；如果version不指定，则可以从父pom中继承；如果groupId/artifactId不指定，可按照prefix调用。

## 实战：自己写插件

### 目标

扫描source文件夹下所有的代码，并统计代码行数

### 前言

需要生成一个maven项目，此项目的打包方式为maven-plugin。

### 动手

使用archetype生成项目结构

```bash
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-plugin -DarchetypeVersion=1.4
```

插件执行的代码：

```java
import org.apache.maven.plugin.AbstractMojo;
        import org.apache.maven.plugin.MojoExecutionException;

        import org.apache.maven.plugins.annotations.LifecyclePhase;
        import org.apache.maven.plugins.annotations.Mojo;
        import org.apache.maven.plugins.annotations.Parameter;

        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.nio.file.FileVisitResult;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.SimpleFileVisitor;
        import java.nio.file.attribute.BasicFileAttributes;
        import java.util.Objects;

/**
 * defaultPhase：指定绑定的阶段
 */
@Mojo(name = "countLines", defaultPhase = LifecyclePhase.COMPILE)
public class MyMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.sourceDirectory}", property = "sourceDirectory", required = true)
    private File sourceDirectory;

    public void execute() throws MojoExecutionException {
        try {
            Files.walkFileTree(sourceDirectory.toPath(), new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    int lineCount = Files.readAllLines(file).size();
                    getLog().info("Line count of file " + file + " is " + lineCount);
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

使用mvn install部署到本地仓库。

在另外一个项目中使用此插件

```xml
<plugin>
                <groupId>org.korov.plugin</groupId>
                <artifactId>myplugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <id>exec222</id>
                        <phase>initialize</phase>
                        <goals>
                            <goal>countLines</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

执行结果：

```bash
C:\Users\korov\IdeaProjects\demo>mvn initialize
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------------< demo:demo >------------------------------
[INFO] Building demo 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- myplugin:1.0-SNAPSHOT:countLines (exec222) @ demo ---
[INFO] Line count of file C:\Users\korov\IdeaProjects\demo\src\main\java\kkk.java is 9
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.437 s
[INFO] Finished at: 2020-04-11T21:15:05+08:00
[INFO] ------------------------------------------------------------------------
```

如果想传递参数到插件中可以使用-D全局变量的方式，在启动的时候赋予全局变量，然后插件中获取变量进行相应的操作。

# maven多模块之间的依赖传递

使用`<dependencyManagement></dependencyManagement>`进行多模块版本管理。这个只会声明版本，在子模块声明引用的时候不需要声明version。

`pluginManagement`和`dependencyManagement`功能类似，子模块继承父类的插件版本。

对于父模块的所有`dependencies`都会被子模块继承。`properties`也会被继承。

## 超级pom

每个pom中会有`<modelVersion>4.0.0</modelVersion>`表明超级pom的版本，此文件在maven安装目录中`maven-model-builder`的jar包中。里面会有一个`pom-4.0.0.xml`，里面预定义了一些参数，例如中央仓库，插件仓库，main，test这些目录功能的约定。可以自己修改。

# profiles

多环境配置

一个pom文件中的profiles

```xml
<profiles>
		<profile>
			<id>dev</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<project.active>dev</project.active>
			</properties>
		</profile>
		<profile>
			<id>test</id>
			<properties>
				<project.active>test</project.active>
			</properties>
		</profile>
	</profiles>
```

activeByDefault标签的值为true的话表示为默认的profile，使用mvn install命令起作用的就是默认的
 profiles.activation为我们配置激活的profile

profile中的其他属性

```
<profile>
            <id>prod</id>
            <properties>
                <profiles.active>prod</profiles.active>
            </properties>
            <!--activation用来指定激活方式，可以根据jdk环境，环境变量，文件的存在或缺失-->
            <activation>
                <!--配置默认激活-->
                <activeByDefault>true</activeByDefault>
                
                <!--通过jdk版本-->
                <!--当jdk环境版本为1.5时，此profile被激活-->
                <jdk>1.5</jdk>
                <!--当jdk环境版本1.5或以上时，此profile被激活-->
                <jdk>[1.5,)</jdk>

                <!--根据当前操作系统-->
                <os>
                    <name>Windows XP</name>
                    <family>Windows</family>
                    <arch>x86</arch>
                    <version>5.1.2600</version>
                </os>

                <!--通过系统环境变量，name-value自定义-->
                <property>
                    <name>env</name>
                    <value>test</value>
                </property>

                <!--通过文件的存在或缺失-->
                <file>
                    <missing>target/generated-sources/axistools/wsdl2java/
                        com/companyname/group</missing>
                    <exists/>
                </file>
            </activation>
        </profile>
```

使用指定的profile

```bash
mvn install -Ptest
```

表明会将`src\main\resources\test`作为资源路径。

也可以在setting.xml中是定profile

```xml
<activeProfiles>  
     <activeProfile>dev</activeProfile>  
</activeProfiles>  
```

# maven私服

## nexus

```bash
docker run -d -p 8081:8081 --name nexus sonatype/nexus:oss
```

浏览器登录`http://localhost:8081/nexus`，用户名密码是`admin，admin123`，进入之后需要创建一个自己的仓库。

# maven小知识

## 语义化版本管理

版本格式：主版本号.次版本号.修订号，版本号递增规则如下：

主版本号：当你做了不兼容的 API 修改，
 次版本号：当你做了向下兼容的功能性新增，
 修订号：当你做了向下兼容的问题修正。
 先行版本号及版本编译元数据可以加到“主版本号.次版本号.修订号”的后面，作为延伸。

### 语义化版本控制规范

以下关键词 MUST、MUST NOT、REQUIRED、SHALL、SHALL NOT、SHOULD、SHOULD NOT、  RECOMMENDED、MAY、OPTIONAL 依照 RFC 2119 的叙述解读。（译注：为了保持语句顺畅，  以下文件遇到的关键词将依照整句语义进行翻译，在此先不进行个别翻译。）

1. 使用语义化版本控制的软件必须（MUST）定义公共 API。该 API 可以在代码中被定义或出现于严谨的文件内。无论何种形式都应该力求精确且完整。
2. 标准的版本号必须（MUST）采用 X.Y.Z 的格式，其中 X、Y 和 Z 为非负的整数，且禁止（MUST NOT）在数字前方补零。X  是主版本号、Y 是次版本号、而 Z 为修订号。每个元素必须（MUST）以数值来递增。例如：1.9.1 -> 1.10.0 ->  1.11.0。
3. 标记版本号的软件发行后，禁止（MUST NOT）改变该版本软件的内容。任何修改都必须（MUST）以新版本发行。
4. 主版本号为零（0.y.z）的软件处于开发初始阶段，一切都可能随时被改变。这样的公共 API 不应该被视为稳定版。
5. 1.0.0 的版本号用于界定公共 API 的形成。这一版本之后所有的版本号更新都基于公共 API 及其修改内容。
6. 修订号 Z（x.y.Z | x > 0）必须（MUST）在只做了向下兼容的修正时才递增。这里的修正指的是针对不正确结果而进行的内部修改。
7. 次版本号 Y（x.Y.z | x > 0）必须（MUST）在有向下兼容的新功能出现时递增。在任何公共 API  的功能被标记为弃用时也必须（MUST）递增。也可以（MAY）在内部程序有大量新功能或改进被加入时递增，其中可以（MAY）包括修订级别的改变。每当次版本号递增时，修订号必须（MUST）归零。
8. 主版本号 X（X.y.z | X > 0）必须（MUST）在有任何不兼容的修改被加入公共 API 时递增。其中可以（MAY）包括次版本号及修订级别的改变。每当主版本号递增时，次版本号和修订号必须（MUST）归零。
9. 先行版本号可以（MAY）被标注在修订版之后，先加上一个连接号再加上一连串以句点分隔的标识符来修饰。标识符必须（MUST）由 ASCII  字母数字和连接号 [0-9A-Za-z-] 组成，且禁止（MUST NOT）留白。数字型的标识符禁止（MUST  NOT）在前方补零。先行版的优先级低于相关联的标准版本。被标上先行版本号则表示这个版本并非稳定而且可能无法满足预期的兼容性需求。范例：1.0.0-alpha、1.0.0-alpha.1、1.0.0-0.3.7、1.0.0-x.7.z.92。
10. 版本编译元数据可以（MAY）被标注在修订版或先行版本号之后，先加上一个加号再加上一连串以句点分隔的标识符来修饰。标识符必须（MUST）由  ASCII 字母数字和连接号 [0-9A-Za-z-] 组成，且禁止（MUST  NOT）留白。当判断版本的优先层级时，版本编译元数据可（SHOULD）被忽略。因此当两个版本只有在版本编译元数据有差别时，属于相同的优先层级。范例：1.0.0-alpha+001、1.0.0+20130313144700、1.0.0-beta+exp.sha.5114f85
11. 版本的优先层级指的是不同版本在排序时如何比较。判断优先层级时，必须（MUST）把版本依序拆分为主版本号、次版本号、修订号及先行版本号后进行比较（版本编译元数据不在这份比较的列表中）。由左到右依序比较每个标识符，第一个差异值用来决定优先层级：主版本号、次版本号及修订号以数值比较，例如：1.0.0 < 2.0.0 < 2.1.0 <  2.1.1。当主版本号、次版本号及修订号都相同时，改以优先层级比较低的先行版本号决定。例如：1.0.0-alpha <  1.0.0。有相同主版本号、次版本号及修订号的两个先行版本号，其优先层级必须（MUST）透过由左到右的每个被句点分隔的标识符来比较，直到找到一个差异值后决定：只有数字的标识符以数值高低比较，有字母或连接号时则逐字以 ASCII  的排序来比较。数字的标识符比非数字的标识符优先层级低。若开头的标识符都相同时，栏位比较多的先行版本号优先层级比较高。范例：1.0.0-alpha < 1.0.0-alpha.1 < 1.0.0-alpha.beta < 1.0.0-beta <  1.0.0-beta.2 < 1.0.0-beta.11 < 1.0.0-rc.1 < 1.0.0。

### 元数据系统

jar包本身没有元信息，很难知道他依赖谁，因此maven仓库中存储了一个pom文件里面表明了这个包依赖了哪些jar，依次层层递进然后获取所有依赖数据。

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

## maven wrapper

类似gradle wrapper，使得项目的mvn版本一致，与本地安装的maven版本相隔离。此时需要将settings.xml温江放到用户目录的.m2文件夹下。

项目目录下会生成maven文件夹和mvnw、mvnw.bat。

### 在pom文件中添加plugin

```xml
<plugin>
    <groupId>com.rimerosolutions.maven.plugins</groupId>
    <artifactId>wrapper-maven-plugin</artifactId>
    <version>0.0.5</version>
</plugin>
```

执行`mvn wrapper:wrapper`生成相应文件。

### 命令初始化

```bash
mvn -N io.takari:maven:wrapper -Dmaven=3.6.3

mvn -N io.takari:maven:0.7.7:wrapper -Dmaven=3.5.4
```

表明想要使用3.6.3版本的maven。

## maven设置代理

```xml
    <proxy>
      <id>optional</id>
      <active>true</active>
      <protocol>socks</protocol>
      <host>127.0.0.1</host>
      <port>1089</port>
      <nonProxyHosts>localhost|aliyun.com</nonProxyHosts>
    </proxy>
```

或者命令行设置

```bash
mvn package assembly:single -DskipTests -DproxySet=true -DproxyHost=127.0.0.1 -DproxyPort=10808

mvn package assembly:single -DskipTests -DsocksProxyHost=true -DsocksProxyPort=127.0.0.1 -DproxyPort=10808
```

## maven使用制定配置文件

```bash
mvn -s C:\Users\korov\.m2\settings-siem.xml clean
```

## maven打包指定模块

```bash
mvn clean -pl 指定模块名 -al 依赖模块
```

## debug

maven的bin目录下有mvnDebug命令，用这个命令执行编译过程就可以使用ide连接上调试器