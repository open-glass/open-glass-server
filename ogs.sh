#!/bin/sh
if [ $# -eq 0 ]
  then
    echo "Provide one of the following arguments:"
    echo "status: check status of the server"
    echo "start: start the server"
    echo "stop: stop the server"
    return
fi
command=$1
java -jar ogs-cli.jar "$command"