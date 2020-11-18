cd src/main/java/

javac cn/edu/nju/czh/jmtrace/testcase/testcase1/StudentTest.java
jar cfe testcase1.jar cn/edu/nju/czh/jmtrace/testcase/testcase1/StudentTest cn/edu/nju/czh/jmtrace/testcase/testcase1/*.class

javac cn/edu/nju/czh/jmtrace/testcase/testcase2/QuickSort.java
jar cfe testcase2.jar cn/edu/nju/czh/jmtrace/testcase/testcase2/QuickSort cn/edu/nju/czh/jmtrace/testcase/testcase2/*.class

javac cn/edu/nju/czh/jmtrace/testcase/testcase3/QuickSortForLong.java
jar cfe testcase3.jar cn/edu/nju/czh/jmtrace/testcase/testcase3/QuickSortForLong cn/edu/nju/czh/jmtrace/testcase/testcase3/*.class

javac cn/edu/nju/czh/jmtrace/testcase/testcase4/EnumSort.java
jar cfe testcase4.jar cn/edu/nju/czh/jmtrace/testcase/testcase4/EnumSort cn/edu/nju/czh/jmtrace/testcase/testcase4/*.class

rm cn/edu/nju/czh/jmtrace/testcase/testcase1/*.class
rm cn/edu/nju/czh/jmtrace/testcase/testcase2/*.class
rm cn/edu/nju/czh/jmtrace/testcase/testcase3/*.class
rm cn/edu/nju/czh/jmtrace/testcase/testcase4/*.class

mv testcase*.jar ../../../testcase/

cd ../../../
mvn package

AGENT="target/jmtrace-agent-1.0-SNAPSHOT-jar-with-dependencies.jar"


java -javaagent:${AGENT} -Xbootclasspath/p:${AGENT} -jar ./testcase/testcase1.jar
java -javaagent:${AGENT} -Xbootclasspath/p:${AGENT} -jar ./testcase/testcase2.jar
java -javaagent:${AGENT} -Xbootclasspath/p:${AGENT} -jar ./testcase/testcase3.jar
java -javaagent:${AGENT} -Xbootclasspath/p:${AGENT} -jar ./testcase/testcase4.jar
