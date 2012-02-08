#run this script to generate the html reports
java -Xmx2000m -cp `dirname $0`/target/DaTeC-1.0-SNAPSHOT.jar:`dirname $0`/lib/sqlite-jdbc-3.6.23.1-beta-1-SNAPSHOT.jar ch.unisi.inf.datec.Report
