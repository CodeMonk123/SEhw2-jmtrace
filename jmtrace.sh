#!/bin/bash

useage() {
    echo "useage:"
    echo "  jmtrace.sh [-j /PATH/TO/JAR]"
    exit -1
}

JAR_PACKAGE=""

while getopts j: OPT; do
    case ${OPT} in
        j) JAR_PACKAGE=${OPTARG};;
        ?) useage;;
    esac
done

if [ -z "$JAR_PACKAGE" ]; then
    useage;
fi


AGENT="agent/target/jmtrace-agent-1.0-SNAPSHOT-jar-with-dependencies.jar"
java -javaagent:${AGENT} -Xbootclasspath/p:${AGENT} -jar ${JAR_PACKAGE}
