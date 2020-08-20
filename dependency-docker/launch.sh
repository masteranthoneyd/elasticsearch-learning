#!/bin/bash

# 创建 docker 网络
export NETWORK_NAME="backend"
filterName=`docker network ls | grep ${NETWORK_NAME} | awk '{ print $2 }'`
if [ "$filterName" == "" ]; then
    docker network create $NETWORK_NAME
fi

elasticsearch_container=`docker ps -a | grep elasticsearch | awk '{ print $1 }'`
if [ "$elasticsearch_container" == "" ]; then

    cd ./elasticsearch

    # 创建 data 目录
    if [ ! -d "./data/" ];then
    mkdir -p ./data/
    chmod g+rwx ./data/
    sudo chgrp 0 ./data/
    fi

    chmod g+rwx ./config/
    sudo chgrp 0 ./config/

    # 启动
    docker-compose up -d
    cd ../
fi

kibana_container=`docker ps -a | grep kibana | awk '{ print $1 }'`
if [ "$kibana_container" == "" ]; then
    cd ./kibana
    docker-compose up -d
    cd ../
fi
