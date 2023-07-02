#!/bin/bash

bar="--------------------------------------------------------------------------------------------"

gradle --version

echo "${bar}"

gradle dependencies || 1

gradle build || exit 1

echo "${bar}"

rm -rfv $TOMCAT_HOME/webapps/hello-world-app/

rm -rfv $TOMCAT_HOME/webapps/hello-world-app.war

rsync -avz ~/workspace/java/memo-apache-tomcat/hello-world/app/build/libs/hello-world-app.war $TOMCAT_HOME/webapps

echo "${bar}"

$TOMCAT_HOME/bin/catalina.sh stop

echo "${bar}"

$TOMCAT_HOME/bin/catalina.sh start