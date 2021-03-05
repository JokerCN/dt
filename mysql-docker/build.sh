#!/bin/bash
# usage: ./build.sh 0.0.1
rm -rf build-temp
mkdir build-temp
cp ../src/main/resources/db/* build-temp/
cp Dockerfile build-temp/
cp run.sh build-temp/
cd build-temp || return
docker build -t harbor.cicconline.com/paas/nacos-test-mysql:"$1" .
cd ..
rm -rf build-temp
# docker login -u "$2" -p "$3"
# docker push harbor.cicconline.com/paas/nacos-test-mysql:"$1"