FROM --platform=linux/amd64 centos:centos8

LABEL author="korov" email="korov9@163.com" version="1.0.0"

WORKDIR /root

COPY ohmyzsh_install.sh /root/
COPY pyenv_install.sh /root/

RUN sed -e 's|^mirrorlist=|#mirrorlist=|g' \
         -e 's|^#baseurl=http://mirror.centos.org|baseurl=https://mirrors.tuna.tsinghua.edu.cn|g' \
         -i.bak \
         /etc/yum.repos.d/CentOS-*.repo; \
	yum install -y epel-release; \
    yum makecache; yum install -y htop neofetch git zsh unzip zip vim telnet \
	gcc zlib-devel bzip2 bzip2-devel readline-devel sqlite sqlite-devel openssl-devel tk-devel libffi-devel xz-devel; \
	bash /root/ohmyzsh_install.sh && \
	curl -s "https://get.sdkman.io" | zsh; zsh /root/pyenv_install.sh; \
	yum clean all;

CMD [ "zsh" ]
   
