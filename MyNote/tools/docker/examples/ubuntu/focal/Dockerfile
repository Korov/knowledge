FROM --platform=linux/amd64 ubuntu:focal

LABEL author="korov" email="korov9@163.com" version="1.0.0"

ENV DEBIAN_FRONTEND noninteractive

WORKDIR /root

COPY ohmyzsh_install.sh /root/
COPY pyenv_install.sh /root/

RUN sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list; \
    set -ex; \
	apt-get update; \
	if ! which gpg; then \
		apt-get install -y --no-install-recommends gnupg; \
	fi; \
	if ! gpg --version | grep -q '^gpg (GnuPG) 1\.'; then \
# Ubuntu includes "gnupg" (not "gnupg2", but still 2.x), but not dirmngr, and gnupg 2.x requires dirmngr
# so, if we're not running gnupg 1.x, explicitly install dirmngr too
		apt-get install -y --no-install-recommends dirmngr; \
	fi; \
    apt-get upgrade -y; \
    apt-get install -y tzdata language-pack-zh-hans htop neofetch git zsh unzip zip vim iputils-ping \
	telnet make build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget \
	curl llvm libncursesw5-dev xz-utils tk-dev libxml2-dev libxmlsec1-dev libffi-dev liblzma-dev --fix-missing; \
	bash /root/ohmyzsh_install.sh && \
	curl -s "https://get.sdkman.io" | zsh; zsh /root/pyenv_install.sh; \
	rm -rf /var/lib/apt/lists/*

RUN chsh -s $(which zsh) && /bin/sh -c zsh -c "source ~/.zshrc"

CMD [ "zsh" ]
   
