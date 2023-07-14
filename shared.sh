#!/bin/bash

JAVA_VERSION=8.0.342-amzn
GRADLE_VERSION=6.9
TOMCAT_VERSION=8.5.88
ANT_VERSION=1.10.13

TOMCAT_CONF=./conf/tomcat
TOMCAT_LOGS=./logs/tomcat
APACHE2_CONF=./conf/apache2

# ---------------------------------- Function to handle errors ----------------------------------
handle_error() {
    echo "Error: $1"
    return
}

# ---------------------------------- Function to log the start of an operation ----------------------------------
start_log(){
    echo "Start: $1"
}

# ---------------------------------- Check if command is available ----------------------------------
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# ---------------------------------- clean .bashrc ----------------------------------
clean_bashrc(){
    sed -i '/chmod +x \"\${TOMCAT_HOME}\/bin\/catalina.sh\"/d' "${HOME}/.bashrc"
    sed -i '/alias catalina/d' "${HOME}/.bashrc"
    sed -i '/export CATALINA_TMPDIR/d' "${HOME}/.bashrc"
}