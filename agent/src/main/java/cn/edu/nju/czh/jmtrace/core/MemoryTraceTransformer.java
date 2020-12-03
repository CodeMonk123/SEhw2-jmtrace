package cn.edu.nju.czh.jmtrace.core;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MemoryTraceTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if(className.startsWith("java")|| className.startsWith("sun")) {
            return null;
        }

        ClassReader reader = new ClassReader(classfileBuffer);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new MemoryTraceClassVisitor(writer);
        reader.accept(visitor, 0);



        return writer.toByteArray();
    }
}
