#! /bin/bash

# restricting the permissions of the profile
sudo chown -R root master/config
sudo chgrp -R root master/config
sudo chown -R root slave/config
sudo chgrp -R root slave/config

# create a docker network for mysql
have_net_mysql=`sudo docker network ls | grep net_mysql`
 if [ -n have_net_mysql ];then
        echo 'net_mysql has been created'
else
        sudo docker network create --driver bridge --subnet 172.20.0.0/16 net_mysql
fi

# create containers
sudo docker-compose -f docker-compose.yaml up -d