function pid() {
    local pid

    pid=$(ps ax | grep "$(home)" | grep java | grep "lib/sonarqube_monitoring" | grep -v grep | awk '{print $1}')

    echo "$pid"
}


function home() {
    realpath "$0" | sed 's|\(.*\)/bin/.*|\1|'
}

