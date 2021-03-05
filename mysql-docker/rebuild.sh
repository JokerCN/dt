#!/bin/bash

docker rm -f $(docker ps -a -f "name=nacos-test-mysql" --format {{.ID}})
docker rmi -f $(docker images harbor.cicconline.com/paas/nacos-test-mysql:0.0.1 --format {{.ID}})
./build.sh 0.0.1
docker run -dp 3307:3306 --name nacos-test-mysql harbor.cicconline.com/paas/nacos-test-mysql:0.0.1