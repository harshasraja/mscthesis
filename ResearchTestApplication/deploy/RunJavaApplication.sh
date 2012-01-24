#!/bin/bash
JAVA_HOME=/usr/bin/java
APP_HOME=/home/chinnu/workspace/ResearchTestApplication/deploy
CLASSPATH=$CLASSPATH:$APP_HOME/lib/log4j-1.2.16.jar:$APP_HOME/lib/slf4j-log4j12-1.6.1.jar:$APP_HOME/lib/ResearchTestApplication.jar:$APP_HOME/lib/apache-cassandra-0.8.4.jar:$APP_HOME/lib/apache-cassandra-thrift-0.8.4.jar:$APP_HOME/lib/commons-lang-2.4.jar:$APP_HOME/lib/commons-pool-1.5.3.jar:$APP_HOME/lib/FastInfoset-1.2.2.jar:$APP_HOME/lib/guava-r08.jar:$APP_HOME/lib/hector-core-0.8.0-2.jar:$APP_HOME/lib/hector-core-0.8.0-2-tests.jar:$APP_HOME/lib/high-scale-lib-1.1.1.jar:$APP_HOME/lib/jul-to-slf4j-1.6.1.jar:$APP_HOME/lib/libthrift-0.6.jar:$APP_HOME/lib/opencsv-2.3.jar:$APP_HOME/lib/slf4j-api-1.6.1.jar:$APP_HOME/lib/uuid-3.2.0.jar
clear
echo $CLASSPATH
$JAVA_HOME -cp $CLASSPATH harsha.thesis.app.java.JavaApplication "192.168.43.2:9160@Test Cluster/UNIVERSITY" harsha.thesis.app.java.Solution0 harsha.thesis.api.solution0.entity.Course insert /home/chinnu/workspace/ResearchTestApplication/data/Solution3/CourseDataS1.csv
exit 0

