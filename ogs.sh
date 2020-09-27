#!/bin/sh
command=$1
if [ $# -eq 0 ]
  then
    echo "Provide one of the following arguments:"
    echo "status: check status of the server"
    echo "start: start the server"
    echo "stop: stop the server"
    return
fi
java -jar ogs-cli.jar "$command"