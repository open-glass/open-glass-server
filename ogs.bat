@echo off
IF "%1"=="" GOTO NO_ARGS
SET command="%1"
java -jar ogs-cli.jar %command%
exit 0

:NO_ARGS
echo Provide one of the following arguments:
echo status: check status of the server
echo start: start the server
echo stop: stop the server
exit 0