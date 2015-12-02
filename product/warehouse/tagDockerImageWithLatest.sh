#!/usr/bin/env bash

typeset -r IMAGE_NAME="crisp/codekvast-warehouse"

VERSION=$(grep codekvastVersion ../../gradle.properties | tr -d [:space:] | cut -d= -f2)
IMAGE_ID=$(docker images |grep $IMAGE_NAME | grep $VERSION | awk '{print $3}')
if [ -z "$IMAGE_ID" ]; then
    echo "No Docker image $IMAGE_NAME:$VERSION has been built, do 'gradle distDocker' first!"
    exit 2
fi

echo "Tagging image $IMAGE_ID with :latest ..."
docker tag --force $IMAGE_ID $IMAGE_NAME:latest
docker images |grep $IMAGE_NAME
