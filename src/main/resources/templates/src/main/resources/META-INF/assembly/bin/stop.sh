#!/bin/bash
set -e
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

SERVER_PORT=`sed '/provider.port/!d;s/.*=//' classes/application.properties | tr -d '\r'`
SERVER_NAME=`sed '/application.name/!d;s/.*=//' classes/application.properties | tr -d '\r'`
#PID=`ps -ef|grep $SERVER_NAME |grep -v .sh | grep -v grep | awk '{print $2}'`
PIDS=`/usr/sbin/lsof -n -P -i :${SERVER_PORT}|grep LISTEN |awk '{print $2}'`
if [ -n "$PIDS" ]; then
	echo "kill -9 $PIDS"
	kill -9 $PIDS
	echo "$SERVER_NAME 关闭成功"
else
	echo "$SERVER_NAME 没有开启"
fi