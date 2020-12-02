# Java插桩：内存访问检测
---
## 环境
- Java版本：java version "1.8.0_221"
- Maven版本：Apache Maven 3.6.2

## 打包
``` shell
$ cd agent
$ mvn package
```

## 运行
- 打包之后，可以通过[jmtrace.sh](./jmtrace.sh)来进行插桩（Linux和MacOS）:
``` shell
# ./jmtrace.sh [-j /PATH/TO/JAR]
$ ./jmtrace.sh -j xxx.jar
```

- 也可以使用打包之后target下的`jmtrace-agent-1.0-SNAPSHOT.jar`作为javaagent来进行插桩
``` shell
$ java -javaagent:jmtrace-agent-1.0-SNAPSHOT.jar -jar xxx.jar
```

## 参考
[https://docs.oracle.com/javase/specs/jvms/se8/jvms8.pdf](https://docs.oracle.com/javase/specs/jvms/se8/jvms8.pdf)

[https://docs.oracle.com/javase/specs/jvms/se7/html/index.html](https://docs.oracle.com/javase/specs/jvms/se7/html/index.html)

[字节码增强技术探索](https://tech.meituan.com/2019/09/05/java-bytecode-enhancement.html)

