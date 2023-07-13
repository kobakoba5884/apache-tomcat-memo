#!/bin/bash

# ---------------------------------- uninstall java, gradle, and tomcat via sdkman ----------------------------------
uninstall_sdkman_tools(){
    start_log "${FUNCNAME[0]}"

    sdk uninstall java "${JAVA_VERSION}" --force || handle_error "Failed to uninstall Java"
    sdk uninstall gradle "${GRADLE_VERSION}" --force || handle_error "Failed to uninstall Gradle"
    sdk uninstall tomcat "${TOMCAT_VERSION}" --force || handle_error "Failed to uninstall Tomcat"
}

# ---------------------------------- remove tomcat setup ----------------------------------
remove_tomcat_setup(){
    start_log "${FUNCNAME[0]}"

    sed -i '/chmod +x \"\${TOMCAT_HOME}\/bin\/catalina.sh\"/d' "${HOME}/.bashrc"
    sed -i '/alias catalina/d' "${HOME}/.bashrc"
}

# ---------------------------------- uninstall apache ----------------------------------
uninstall_apache(){
    start_log "${FUNCNAME[0]}"

    if command_exists apache2 ; then
        sudo apt remove --purge apache2 -y || handle_error "Failed to uninstall Apache2"
        sudo apt autoremove -y
    else
        echo "Apache2 is not installed"
    fi
}

# ---------------------------------- Call the functions ----------------------------------
source ./shared.sh

uninstall_sdkman_tools
remove_tomcat_setup
uninstall_apache