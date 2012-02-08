#!/bin/bash

# takes good runs from the results directory, and creates virtual project runs
#
# for example, if the project A has the following classes
#  a - runs:
#      results/A/a/tool/time/0
#      results/A/a/tool/time/1
#  b - runs:
#      results/A/b/tool/time/1
#      results/A/b/tool/time/2
#  c - runs:
#      results/A/c/tool/time/2
#      results/A/c/tool/time/3
#
# this script creates two virtual runs:
# coverage/tool/time/0/A
#                       /a -> results/A/a/tool/time/0
#                       /b -> results/A/a/tool/time/1
#                       /c -> results/A/a/tool/time/2
# coverage/tool/time/1/A
#                       /a -> results/A/a/tool/time/1
#                       /b -> results/A/a/tool/time/2
#                       /c -> results/A/a/tool/time/3
# and inserts in each original run (those in results) a file "coverage.txt"
# with the path of the run under the coverage directory
# e.g. results/A/c/tool/time/2/coverage.txt contains (without quotes):
#      "dir:coverage/tool/time/0/A/c"

for run in `find results -path "results/*/*/*/*/?" | sort`
do

    # check for bad runs
    if [ -f $run/log/testful.log.bz2 ] && [ -d $run/tests ]
    then

	# check if the run has not been considered
	if [ ! -f $run/coverage.txt ];
	then

	    project=`echo $run | cut -d "/" -f 2`
	    class=`echo $run | cut -d "/" -f 3`
	    tool=`echo $run | cut -d "/" -f 4`
	    time=`echo $run | cut -d "/" -f 5`

	    newRunId=0
	    while [ -d coverage/$tool/$time/$newRunId/$project/$class ];
	    do
		newRunId=$(( newRunId + 1 ))
	    done

	    newRun=coverage/$tool/$time/$newRunId/$project
	    [ ! -d $newRun ] && mkdir -p $newRun

	    ln -s ../../../../../$run $newRun/$class
	    echo "dir:$newRun/$class" > $run/coverage.txt

	    echo "$run -> $newRun/$class"  
	fi

    else
	# if there is the testful.log.bz2 (=> directory $run/tests is missing)
	[ -f $run/log/testful.log.bz2 ] && echo "BAD: $run"
    fi
done
