1. # maven常用命令

mvn package和mvn install都是用来打包的，区别在于install会把打好的包放入本地maven仓库，package不会

mvn clean清理已经打好的包，通常使用方式：mvn clean package

跳过测试类：mvn clean install -Dmaven.test.skip=true

# maven中RELEASE和SNAPSHOT的区别

maven中的仓库分为两种SNAPSHOT快照仓库和RELEASE发布仓库。快照仓库用于保存开发过程中不稳定版本号，发布仓库用来保存稳定的发行版本号。

maven会根据模块的版本号(pom文件中的version)中是否带有-SNAPSHOT来判断是快照版本还是正式版本。如果是快照版本，那么在 mvn deploy时会自动发布到快照版本库中，而使用快照版本的模块，在不更改版本号的情况下，直接编译打包时，maven会自动从镜像服务器上下载最新的快照版本。如果是正式发布版本，那么在mvn deploy时会自动发布到正式版本库中，而使用正式版本的模块，在不更改版本号的情况下，编译打包时如果本地已经存在该版本的模块则不会主动去镜像服务 器上下载。