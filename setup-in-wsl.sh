#!/bin/bash

# ---------------------------------- install pacakges ----------------------------------
install_packages(){
    start_log "${FUNCNAME[0]}"

    PKG="git curl zip unzip"

    sudo apt-get update -y
    sudo apt-get install ${PKG} -y
}

# ---------------------------------- install sdkman for java ----------------------------------
install_sdkman(){
    start_log "${FUNCNAME[0]}"

    curl -s "https://get.sdkman.io" | bash || handle_error "Failed to install SDKMAN"
    source "${HOME}/.sdkman/bin/sdkman-init.sh"

    sdk install java "${JAVA_VERSION}" || handle_error "Failed to install Java"

    sdk install gradle "${GRADLE_VERSION}" || handle_error "Failed to install Gradle"

    sdk install tomcat "${TOMCAT_VERSION}" || handle_error "Failed to install Tomcat"
}

# ---------------------------------- setup tomcat ----------------------------------
setup_tomcat(){
    start_log "${FUNCNAME[0]}"

    if ! grep -q 'alias catalina' "${HOME}/.bashrc" ; then
        echo 'chmod +x "${TOMCAT_HOME}/bin/catalina.sh"' >> "${HOME}/.bashrc"
        echo 'alias catalina="$TOMCAT_HOME/bin/catalina.sh"' >> "${HOME}/.bashrc"
    else
        echo "Tomcat is already set up"
    fi

    ln -s $TOMCAT_HOME/conf ${TOMCAT_CONF}
}

# ---------------------------------- install apache ----------------------------------
install_apache(){
    start_log "${FUNCNAME[0]}"

    if ! command_exists apache2 ; then
        sudo apt update -y || handle_error "Failed to update package list"
        sudo apt install apache2 -y || handle_error "Failed to install Apache2"
    else
        echo "Apache2 is already installed"
    fi

    sudo ln -s /etc/apache2/ ${APACHE2_CONF}
}

# ---------------------------------- Call the functions ----------------------------------
source ./shared.sh

install_packages
install_sdkman
setup_tomcat
install_apache