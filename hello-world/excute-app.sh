#!/bin/bash

bar="--------------------------------------------------------------------------------------------"

gradle --version

echo "${bar}"

gradle dependencies || return

gradle build || return

gradle buildJar || return

echo "${bar}"

rm -rfv $TOMCAT_HOME/webapps/hello-world-app/

rm -rfv $TOMCAT_HOME/webapps/hello-world-app.war

rsync -avz ./app/build/libs/hello-world-app.war $TOMCAT_HOME/webapps

echo "${bar}"

$TOMCAT_HOME/bin/catalina.sh stop

echo "${bar}"

$TOMCAT_HOME/bin/catalina.sh start

sudo apachectl configtest
sudo systemctl restart apache2