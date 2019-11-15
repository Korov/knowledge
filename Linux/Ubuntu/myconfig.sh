echo "start config your linux powered by korov 2019.9.23"
currentDir = `pwd`

# update system
echo "add repository"
apt-get install software-properties-common
apt-add-repository ppa:neovim-ppa/stable
add-apt-repository "deb [arch=amd64] https://mirrors.ustc.edu.cn/docker-ce/linux/debian jessie stable"

sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys BA300B7755AFCFAE
sudo add-apt-repository 'deb http://typora.io linux/'

echo "update system"
apt-get update -y
apt-get upgrade -y

# install some tools:
echo "install git"
apt-get install git -y
echo "install vim"
apt-get install vim -y
echo "install neovim"
apt-get install neovim -y
echo "install curl"
apt-get install curl -y
echo "install unzip"
sudo apt-get install unzip -y
echo "install unrar"
sudo apt-get install unrar -y
echo "install openjdk 11"
sudo apt install openjdk-11-jdk
echo "install maven"
sudo apt-get install maven -y
mvn -version
echo "install docker.io"
curl -fsSL https://mirrors.ustc.edu.cn/docker-ce/linux/debian/gpg | sudo apt-key add -
sudo apt-get install docker-ce

echo "install  fctix"
sudo apt-get install fcitx
sudo apt-get install fcitx-googlepinyin

echo "install typora"
sudo apt-get install typora

echo "install flash plgin"
sudo apt-get install flashplugin-installer

echo "install google chrome"
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb 
sudo dpkg -i google-chrome*
sudo apt-get install -f

echo "install idea 2019.1"
sudo tar -xvf ideaIU-2019.1-jbr11.tar.gz
sudo mv idea-IU-* ideaIU

echo "add ssh key"
if [ ! -d ~/.ssh ];then
    mkdir ~/.ssh
fi
mv /sshkey/id_rsa ~/.ssh/
mv /sshkey/id_rsa.pub ~/.ssh/
mv /sshkey/knoew_hosts ~/.ssh/
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa