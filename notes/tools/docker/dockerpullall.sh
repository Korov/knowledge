#! /bin/bash

index=0

for image in `docker images --filter "reference=*/*:latest" --format "{{.Repository}}:{{.Tag}}"`; do
  images[${index}]=${image}
  index=`expr ${index} + 1`
done

for image in `docker images --filter "reference=*:latest" --format "{{.Repository}}:{{.Tag}}"`; do
  images[${index}]=${image}
  index=`expr ${index} + 1`
done

for image in ${images[@]}; do
  docker pull ${image}
done