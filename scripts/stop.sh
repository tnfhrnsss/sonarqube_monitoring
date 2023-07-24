#!/bin/bash

. $(dirname $0)/common.sh

function module_stop() {
    local pid=$(pid)
    if [ "$pid" != "" ]; then
        kill -15 "$pid"
        echo "stop sonarqube_monitoring"
    fi
}

module_stop
