#!/bin/bash

APP_DIR=$TOMCAT_HOME/webapps/hello-world-old-app

bar="--------------------------------------------------------------------------------------------"

ant -version

echo "${bar}"

ant

echo "${bar}"

rsync -av --delete ./build/hello-world-old-app/ $APP_DIR/