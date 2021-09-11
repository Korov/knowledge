FROM alpine:3.14.2

LABEL author="korov" email="korov9@163.com"

ENV appdir /app
RUN mkdir -p $appdir
WORKDIR $appdir
COPY frps/frp_0.37.1_linux_amd64.tar.gz /app/
COPY supervisor/supervisord.conf /etc/supervisord.conf
COPY docker-entrypoint.sh /usr/sbin/

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk update && apk upgrade \
    && apk add python3 py3-pip nginx && pip install supervisor \
    && tar -zxf frp_0.37.1_linux_amd64.tar.gz && mv frp_0.37.1_linux_amd64 frp && rm frp_0.37.1_linux_amd64.tar.gz \
    && mkdir -p /app/logs/frp && mkdir -p /app/logs/nginx \
    && rm -rf /var/cache/apk/* && rm -rf /root/.cache && rm -rf /tmp/*

EXPOSE 7000
EXPOSE 7022
EXPOSE 7080

ENTRYPOINT ["docker-entrypoint.sh"]