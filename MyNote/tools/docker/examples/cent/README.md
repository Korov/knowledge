```bash
docker build -f ./Dockerfile -t korov/cent:1.6 .

docker run -it -d --name cent korov/cent:1.6
docker exec -it --user root ubuntu zsh

# 进入容器之后第一步
source ~/.zshrc
```

