1. # maven常用命令

mvn package和mvn install都是用来打包的，区别在于install会把打好的包放入本地maven仓库，package不会

mvn clean清理已经打好的包，通常使用方式：mvn clean package

跳过测试类：mvn clean install -Dmaven.test.skip=true