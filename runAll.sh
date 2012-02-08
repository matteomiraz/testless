#!/bin/bash

RUNS=`seq 0 9`
TIMES="300 600"

for r in $RUNS;
do
    for time in $TIMES;
    do

	echo "run: $r time: $time" 


	for t in tools/*.sh
	do
	    tool=${t/tools\//}
	    tool=${tool/.sh/}

	    echo "Using ${tool}"

	    bash run.sh $r $tool $time
    
	    S=$?
	    [ $S -ne 0 ] && exit $S

	done

    done
done
