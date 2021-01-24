FROM adoptopenjdk:11.0.9.1_1-jre-hotspot
LABEL author="korov" email="korov9@163.com"

WORKDIR /

ADD cmak-3.0.0.5/ /opt/cmak-3.0.0.5

COPY docker-entrypoint.sh /usr/local/bin/

EXPOSE 9000

ENTRYPOINT ["docker-entrypoint.sh"]

