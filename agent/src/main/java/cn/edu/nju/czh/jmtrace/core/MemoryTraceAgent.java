package cn.edu.nju.czh.jmtrace.core;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;


/**
 * Hello world!
 */
public class MemoryTraceAgent {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public static void premain(String args, Instrumentation instrumentation) throws UnmodifiableClassException {
        // System.out.println("Hi, I am agent");
        instrumentation.addTransformer(new MemoryTraceTransformer());
    }
}
