#!/bin/bash

bar="--------------------------------------------------------------------------------------------"

ant -version

echo "${bar}"

ant

echo "${bar}"

rsync -av --delete ~/workspace/java/memo-apache-tomcat/hello-world-old/build/hello-world-old-app/ $TOMCAT_HOME/webapps/hello-world-old-app/