#!/bin/bash
set -e
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`

LOGS_FILE=$DEPLOY_DIR/logs/stdout.log
tail -n 100 -f $LOGS_FILE