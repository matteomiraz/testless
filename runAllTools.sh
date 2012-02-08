#!/bin/bash

runId=$1
if [ "a$runId" == "a" ];
then
    echo "specify the run id"
    exit 1
fi

TIME=$2
if [ "a$TIME" == "a" ];
then
    echo "specify the time length"
    exit 1
fi


for t in tools/*.sh
do
    tool=${t/tools\//}

    echo "Using ${tool/.sh/}"

    bash run.sh $runId $tool $TIME
    
    S=$?
    [ $S -ne 0 ] && exit $S
done
