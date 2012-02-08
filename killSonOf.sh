#!/bin/bash

if [ "a$1" == "a" ];
then
    echo "specify the pid"
    exit 1
fi

ppid=$1

if [ ! -e /proc/$ppid/status ];
then
    # process already terminated
    exit 0
fi

for i in /proc/*/status; 
do 
    pid=${i/\/proc\//}
    pid=${pid/\/status/}; 

    grep -q "^PPid:.*$ppid$" $i; 
    if [ $? -eq 0 ];
    then
	echo -n "killing $pid"
	kill -9 $pid
	exit 0
    fi
done
