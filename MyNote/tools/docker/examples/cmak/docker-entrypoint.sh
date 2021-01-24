#!/bin/bash

sed -i 's/localhost:2181/$ZK_HOSTS/g' /opt/cmak-3.0.0.5/conf/application.conf

ln -s /opt/cmak-3.0.0.5/bin/cmak /usr/local/bin/cmak
cmak
