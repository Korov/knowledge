# 镜像
FROM frolvlad/alpine-oraclejre8:latest
VOLUME ./temp
# 添加jar包
ADD eureka-1.0-SNAPSHOT.jar app.jar

RUN sh -c 'touch /app.jar'

# 暴露端口
# EXPOSE 7001

# 加快tomcat启动时间
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
