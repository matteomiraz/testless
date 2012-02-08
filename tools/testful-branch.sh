#!/bin/bash

VERSION="branch"

# set $CMD with the command to execute
function getCmd() {
	CMD="$JAVA -Dtestful.debug=true -Dtestful.junit.assert=false -Dtestful.fault=false -Dtestful.maxExecTime=100 -Dtestful.log.level=FINE -Dtestful.log.dir=$OUT/log -jar $BASE/tools/testful.jar -cut $CUT -dir $DIR -dirTests $OUT/tests -dirInstrumented instrumented-$VERSION -popSize 128 -fitnessInheritance DISABLED -time $TIME -disableDefUse -localSearchPeriod 20"
}

function getResults() {
    bzip2 -v $OUT/log/testful.log
    [ -d $OUT/tests ] && [ `find $OUT/tests -iname *.java | wc -l` -gt 0 ] && gzip `find $OUT/tests -iname *.java`
}

function instrumentProject() {
    if [ ! -d $BASE/projects/$project/instrumented-$VERSION ]
    then
	$JAVA -jar $BASE/tools/instrumenter.jar -dataFlowCoverage DISABLED -dir $BASE/projects/$project -file $BASE/projects/$project/toInstrument.txt -dirInstrumented instrumented-$VERSION > $BASE/projects/$project/instrument-$VERSION.out 2> $BASE/projects/$project/instrument-$VERSION.err
    fi
}
