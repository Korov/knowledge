rm /opt/cmak-3.0.0.5/RUNNING_PID

sed -i 's/localhost:2181/$ZK_HOSTS/g' /opt/cmak-3.0.0.5/conf/application.conf

/opt/cmak-3.0.0.5/bin/cmak
