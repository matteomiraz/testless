#!/bin/bash

# kill the program PROG after a given execution time (MAXTIME) 

PROG="java"
MAXTIME="00:30:00"

while [ true ]
do

    Ps=`pgrep $PROG`;
    if [ "A$Ps" != "A" ]
    then 

	for P in $Ps;
	do
	    T=`ps -otime= $P`
	    if [[ "a$T" > "a$MAXTIME" ]]; 
	    then 
		echo "`date` KILL: $T $P"
		kill -9 $P
	    fi
	done
    fi
    
    sleep 1m;
    
done
