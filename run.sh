#!/bin/bash

control_c()
# run if user hits control-c
{
  echo -n " *** killed by the user ***"
  bash $BASE/killSonOf.sh $pid
  echo
  echo "Removing $OUT"
  rm -rf $OUT
  echo
  exit 1
}

trap control_c SIGINT

JAVA="java -Xms1g -Xmx1g"

BASE=`pwd`

runId=$1
if [ "a$runId" == "a" ];
then
    echo "specify the run id"
    exit 1
fi

tool=$2
TOOL=$BASE/tools/$tool.sh
if [ "a$tool" == "a" ]; then
    echo "specify the tool"
    exit 1
elif [ ! -f $TOOL ]; then
    echo "tool $TOOL does not exist"
    exit 1
fi

TIME=$3
if [ "a$TIME" == "a" ];
then
    echo "specify the time length"
    exit 1
fi

if [ "$tool" == "randoop.sh" ] && [ $TIME -gt 600 ];
then
    echo "Cannot execute randoop for more than 5 minutes"
    exit 0;
fi

source $TOOL

for project in `cat projects/projects.txt`;
do

    if [ ! -f projects/$project/toInstrument.txt ]
    then
	find  projects/$project/bin/ -type f -iname *.class | sed "s/projects\/$project\/bin\///" | sed "s/.class//" | sed "y/\//./" | grep -v "^tful\." >  projects/$project/toInstrument.txt
    fi

    instrumentProject

    for CUT in `cat projects/$project/classes.txt`;
    do
	OUT=$BASE/results/$project/$CUT/$tool/$TIME/$runId
	DIR=$BASE/projects/$project/

	if [ ! -d $OUT ];
	then
	    echo "`date --rfc-3339=seconds` Test $runId for $TIME with $tool on $CUT ($project)"
	    
	    mkdir -p $OUT
   
	    getCmd
	    
	    echo $CMD > $OUT/cmd.txt
	    \time -v -o $OUT/cmd.time $CMD > $OUT/cmd.out 2> $OUT/cmd.err&
	    pid=$!
	    start=`date +%s`

	    #                             "00m 00s load: 1.00" 
	    echo -n "  Running ($pid) ...                    "
	    while [ -e /proc/$pid/status ]; 
	    do
		stop=`date +%s`
		len=$(( stop - start ))
		load=`cut -f1 -d " " /proc/loadavg`

		min=$((len/60))
		[ $min -lt 10 ] && min="0$min"

		sec=$(( len%60 ))
		[ $sec -lt 10 ] && sec="0$sec"
		
		#         "  0 0 m _ 0 0 s _ l o a d : _ 1 . 0 0 
		echo -ne  "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b ${min}m ${sec}s load: $load"

		# check if there is an early termination!
		if [ $len -gt 100 ]; 
		then
		    if [[ $load < "0.40" ]];
		    then
			echo -n " "
			bash $BASE/killSonOf.sh $pid
		    fi
		fi
		
		# check if the tool is taking too much time
		if [ $len -gt $(( 2*TIME )) ];
		then
		    echo -n " "
		    bash $BASE/killSonOf.sh $pid
		fi

		sleep 5s
	    done

	    echo
	    getResults $OUT
	    echo 
	fi
    done
done
