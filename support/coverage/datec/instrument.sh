#run this script to instrument the classes
#provide the zip or jar files (containing the classes to be analyzed) as a parameter

java -Xmx2000m -cp /usr/lib/jvm/java-6-sun/jre/lib/rt.jar:`dirname $0`/target/DaTeC-1.0-SNAPSHOT.jar:`dirname $0`/lib/sootclasses-2.3.0.jar:`dirname $0`/lib/polyglot.jar:`dirname $0`/lib/java_cup.jar:`dirname $0`/lib/sqlite-jdbc-3.6.23.1-beta-1-SNAPSHOT.jar:`dirname $0`/lib/jasminclasses-2.3.0.jar:$1 ch.unisi.inf.datec.Instrument
