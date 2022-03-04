FROM centos:centos6.9
LABEL author="korov" eamil="korov9@163.com" date="2020-09-16"

ENV APP_HOME /arango

RUN mkdir $APP_HOME

WORKDIR $APP_HOME

COPY arangodb3-3.6.6.tar.gz /arango

RUN tar -xzvf /arango/arangodb3-3.6.6.tar.gz -C /arango

RUN tar -xzvf /arango/arangodb3-3.6.6.tar.gz -C /arango

RUN rm -rf /arango/arangodb3-3.6.6.tar.gz

ADD docker-startup.sh bin/docker-startup.sh

RUN chmod +x bin/docker-startup.sh

EXPOSE 8529
ENTRYPOINT ["bin/docker-startup.sh"]