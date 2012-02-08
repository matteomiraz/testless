#!/bin/bash

ERR="report.csv.err"
OK="report.csv"

OUT="report.bad"

# compile mean.c
gcc -o support/mean -O3 support/mean.c

# commands for workers
WCMD="moveBadRuns.sh"
echo -e "#!/bin/bash\n\n#Remove bad runs\n\n" > $WCMD

# $1: path to the runs' dir
# $2: run number
# $3: prefix 
# moves $1 to $1/$3-# (# is a progressive number)
moveBadRun() {
    badRunNumber=0;
    while [ -d $1/$3-${badRunNumber} ];
    do
	badRunNumber=$(( badRunNumber + 1 ))
    done

    echo "  $1/$2 -> $3-${badRunNumber}"
    echo "[ -d $1/$2 ] && mv $1/$2 $1/$3-${badRunNumber}" >> $WCMD 
    mv $1/$2 $1/$3-${badRunNumber}
}

IFS="
"

echo "##################################################" >> $OUT

[ ! -f $ERR ] && touch $ERR
for i in `cat $ERR`;
do

    D="results/`echo $i | sed "y/\t/\//" | cut -d "/" -f 1,2,3,4,5`"

    grep -q `echo $i | cut -d "	" -f 1,2,3,4,5` $OK
    NEWRUN=$?
    
    # if the result still exist (i.e., it has not been marked as bad result)
    # and if a new run does not exit
    if [ -d $D ] && [ $NEWRUN -ne 0 ]
    then

	C=`echo $i | sed "y/\t/;/" | cut -d ";" -f 2`
	T=`echo $i | sed "y/\t/;/" | cut -d ";" -f 3`

	NC=`grep "	$C	" $OK | wc -l`
	NT=`grep "	$T	" $OK | wc -l`

	if [ $NC -le 0 ];
	then
	    P="results/`echo $i | sed "y/\t/\//" | cut -d "/" -f 1,2`"
	    echo "BadClass $P" >> $OUT
	else
	    if [ $NT -le 0 ];
	    then
		echo "BadTool $T"  >> $OUT
	    else
		echo "BadRun $D"  >> $OUT
		
		P="results/`echo $i | sed "y/\t/\//" | cut -d "/" -f 1,2,3,4`"
		N=`echo $i | sed "y/\t/\//" | cut -d "/" -f 5`

		moveBadRun $P $N "bad"

		echo "bad	$i" >> ${ERR}.old

	    fi
	fi
    fi
done
rm $ERR

for i in `cat $OK`;
do

    is=`echo $i | sed "y/\t/\//"`

    # Project Class Tool Time
    pctt=`echo $is | cut -d "/" -f 1,2,3,4 `
    PCTT=`echo $pctt | sed "y/\//\t/"`

    RUN=`echo $is | cut -d "/" -f 5 `

    # branch coverage of the current element
    M=`echo $is | cut -d "/" -f 13 | grep -o ".*\." | sed "y/\./ /" `
    
    O=`grep "$PCTT" $OK | sed "y/\t/\//" | cut -d "/" -f 13 `
    avg=`support/mean $O`

    if [ $(( avg / 2 )) -gt $M ];
    then
	
	echo -e "SlowRun $N\t$M\t$avg\t$pctt/$RUN" >> $OUT

	moveBadRun results/$pctt $RUN "slow"
	
	echo "slow	$i" >> ${ERR}.old
	grep -v "$PCTT	$RUN" $OK >> ${OK}.tmp
	mv ${OK}.tmp $OK
    fi
  
done

