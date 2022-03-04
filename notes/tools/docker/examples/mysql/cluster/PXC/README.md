```
mkdir -m 777 -p ./cert

docker run --name pxc-cert --rm -v $PWD/cert:/cert percona/percona-xtradb-cluster:8.0.23 mysql_ssl_rsa_setup -d /cert
```

