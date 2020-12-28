#!/bin/bash
docker pull selenoid/vnc:chrome_86.0
docker pull aerokube/selenoid:latest-release
docker pull aerokube/selenoid-ui:1.4.3
docker pull frankescobar/allure-docker-service:latest
docker-compose build
# install video
# docker pull selenoid/video-recorder:latest-release





