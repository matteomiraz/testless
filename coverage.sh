#!/bin/bash

export BASE=`pwd`
export JUNIT="$BASE/support/coverage/junit-4.8.1.jar"

# ibm jvm: disable core dump
#export JAVA="java -Xmx2g -Xdump:none"
export JAVA="java -Xmx2g"

# $1: the (relative) path to the run
# $2: the name of the project
# $3: the name of the class
processClass() {

    local run=$1/$2
    local project=$2
    local class=$3

    if [ ! -f $run/$class/coverage.txt ];
    then
	echo "  No coverage.txt in $class ($run)"
	return 1
    fi
    
    if [ ! -d $run/$class/tests ] || [ ! -f $run/$class/coverage.txt ];
    then
	echo "  No tests in $class ($run)"
	return 1
    fi

    #header
    h=0

    for COV in `ls $BASE/support/coverage/ | grep "\.sh$"`
    do
	local cov=${COV/.sh/}
	local COV=$BASE/support/coverage/$COV

	bash $COV "check" "$project"; local CHECK=$?
 	grep -q "tool:$cov" $run/$class/coverage.txt; local DONE=$?
 	if [ $CHECK -eq 0 ] && [ $DONE -eq 1 ]
	then
	    [ $h -eq 0 ] && echo "  $class" && h=1

	    echo -n "    $cov (`date "+%D %T"` "
	    secStart=`date +%s`
	    bash $COV "run" "$project" "$run" "$class"
	    status=$?
	    secEnd=`date +%s`
	    secLength=$(( secEnd - secStart ))
	    echo -n "- `date "+%D %T"` : $secLength s) "

	    if [ $status -eq 0 ]
	    then
		echo "OK"
		echo "tool:$cov" >> $run/$class/coverage.txt
	    else
		echo "ERROR"
		bash $COV "recovery" "$project" "$run"
	    fi
	fi
    done
}

# $1: the name of the project
# $2: the (relative) path to the run
processRun() {

    local p=$1
    local run=$2

    # check if the tool run on this project in this run
    [ ! -d $run/$p ] && return 1

    [ ! -d $run/$p/coverage ] && mkdir $run/$p/coverage

    # locking 
    [ -f $run/$p/coverage/lock ] && return 1
    touch $run/$p/coverage/lock

    echo "Working on $run/$p" 

    for c in `find $run/$p -type l -printf "%f\n"`
    do
	processClass $run $p $c
    done

    # unlocking 
    rm $run/$p/coverage/lock
}

# $1: the name of the project
processProject() {

    local p=$1

    if [ ! -d results/$p ]
    then
	echo "$p does not exist!"
	return 1
    fi

    for COV in `ls $BASE/support/coverage/ | grep "\.sh$"`
    do
	local cov=${COV/.sh/}
	local COV=$BASE/support/coverage/$COV

	bash $COV "check" "$p"
	if [ $? -ne 0 ];
	then
	    echo 
	    echo "##################################################"
	    echo ">>> ERRROR $p has not been instrumented with $cov"
	    echo "##################################################"
	fi
    done

    for run in coverage/*/*/*
    do
        # if the tool run on this project in this "$run"
	[ -d $run/$p ] && processRun $p $run
    done
}

#for p in `ls results`;
for p in NanoXML 
do

    processProject $p
done
