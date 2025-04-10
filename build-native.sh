#!/usr/bin/env bash

docker build -t al2023-graalvm21:native-web .
docker run -it -v `pwd`:`pwd` -w `pwd` -v ~/.gradle:/root/.gradle al2023-graalvm21:native-web \
  ./gradlew clean build -x test buildNativeLambda