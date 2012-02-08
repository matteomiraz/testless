#run this script to perform the static analysis. 
#Provide the jar or zip (containing all the classes to be analyzed) as a parameter. 
java -Xmx2000m -cp `dirname $0`/target/DaTeC-1.0-SNAPSHOT.jar:`dirname $0`/lib/sootclasses-2.3.0.jar:`dirname $0`/lib/polyglot.jar:`dirname $0`/lib/java_cup.jar:`dirname $0`/lib/sqlite-jdbc-3.6.23.1-beta-1-SNAPSHOT.jar ch.unisi.inf.datec.Main $1
