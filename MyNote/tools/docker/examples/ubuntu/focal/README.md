```bash
docker build -f ./Dockerfile -t korov/ubuntu:1.4 .

docker run -it -d --name ubuntu korov/ubuntu:1.4
docker exec -it --user root ubuntu zsh

# 进入容器之后第一步
source ~/.zshrc
```

