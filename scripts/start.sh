#!/bin/bash

. $(dirname $0)/common.sh

function module_start() {
    local pid=$(pid)

    if [ "$pid" != "" ]; then
        echo ">>> already started"
    else
        java -Xms256m -Xmx256m -Dspring.config.additional-location=file:$(home)/application.yml -jar $(home)/lib/sonarqube_monitoring-0.0.1-SNAPSHOT.jar  > "$(home)/logs/sonarqube_monitoring.log" &
        echo ">>> starting sonarqube_monitoring"

    fi
}

module_start
