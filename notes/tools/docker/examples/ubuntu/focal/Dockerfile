FROM --platform=linux/amd64 ubuntu:focal

LABEL author="korov" email="korov9@163.com" version="1.0.0"

ENV DEBIAN_FRONTEND noninteractive

WORKDIR /root

COPY ohmyzsh_install.sh /root/
COPY pyenv_install.sh /root/
COPY nvm_install.sh /root/

RUN sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list ; \
    set -ex ; \
	apt-get update ; \
	if ! which gpg ; then \
		apt-get install -y --no-install-recommends gnupg ; \
	fi ; \
	if ! gpg --version | grep -q '^gpg (GnuPG) 1\.' ; then \
		apt-get install -y --no-install-recommends dirmngr ; \
	fi ; \
    apt-get upgrade -y ; \
    apt-get install -y tzdata language-pack-zh-hans htop neofetch git zsh unzip zip vim iputils-ping \
	telnet make build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget \
	curl llvm libncursesw5-dev xz-utils tk-dev libxml2-dev libxmlsec1-dev libffi-dev liblzma-dev --fix-missing && \
	bash /root/ohmyzsh_install.sh && \
	curl -s "https://get.sdkman.io" | zsh && \
	zsh /root/pyenv_install.sh && zsh /root/nvm_install.sh && \
	echo "source /root/.zshrc" >> /etc/profile && \
	rm -rf /var/lib/apt/lists/* && rm /root/ohmyzsh_install.sh && rm /root/pyenv_install.sh && rm /root/nvm_install.sh

CMD [ "zsh" ]
   
