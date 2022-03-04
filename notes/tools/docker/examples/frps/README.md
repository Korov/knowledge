集成了supervisor，frps，nginx三个组件

```bash
wget https://github.com/fatedier/frp/releases/download/v0.37.1/frp_0.37.1_linux_amd64.tar.gz -P ./frps

docker build -f ./Dockerfile -t korov/frps:1.0 .

rm frps/frp_0.37.1_linux_amd64.tar.gz
```

 

```bash
docker run -d -it --name frps -p 7000:7000 -p 7022:7022 -p 7080:7080 -v path/supervisord.conf:/etc/supervisord.conf -v path/nginx.conf:/etc/nginx.conf -v 
path/frps.ini:/app/frp/frps.ini korov/frps:1.1


docker run -d -it --name frps -p 7000:7000 -p 7022:7022 -p 7080:7080 korov/frps:1.1

docker exec -it --user root frps sh
```

