package cn.edu.nju.czh.jmtrace.core;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MemoryTraceClassVisitor extends ClassVisitor {
    // Constructor
    public MemoryTraceClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // ignore constructor
        if (name.equals("<init>") || name.equals("<clinit>")) {
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }
        return new MemoryTraceMethodVisitor(cv.visitMethod(access, name, descriptor, signature, exceptions));
    }

    // InnerClass
    class MemoryTraceMethodVisitor extends MethodVisitor {
        public MemoryTraceMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM5, methodVisitor);
        }

        /*
         * 在JVM的Operand Stack中，long和double占两个slots
         * 其他类型(int, char等和引用类型)占一个slot
         * */

        @Override
        public void visitInsn(int opcode) {
            // *aload
            // Stack
            // before   : [...,arrayRef, index] <-
            // after    : [...,value] <-
            if (opcode == Opcodes.ALOAD || opcode >= Opcodes.IALOAD && opcode <= Opcodes.SALOAD) {
                mv.visitInsn(Opcodes.DUP2);
                // [...,arrayRef, index, arrayRef, index] <-
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "traceArrayLoad", "(Ljava/lang/Object;I)V", false);
                // [...,arrayRef, index] <-
            }
            // *astore
            // Stack
            // before   : [...,arrayRef, index, value] <-
            // after    : [...]<-
            if (opcode == Opcodes.ASTORE || opcode >= Opcodes.IASTORE && opcode <= Opcodes.SASTORE) {
                // 需要考虑value的长度
                if (opcode == Opcodes.LASTORE || opcode == Opcodes.DASTORE) {
                    // value is long or double
                    // [...,arrayRef, index, long value] <-
                    mv.visitInsn(Opcodes.DUP2_X2);
                    // [...,long value, arrayRef, index, long value] <-
                    mv.visitInsn(Opcodes.POP2);
                    // [...,long value, arrayRef, index] <-
                    mv.visitInsn(Opcodes.DUP2_X2);
                    // [...,arrayRef, index,long value, arrayRef, index] <-
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "traceArrayStore", "(Ljava/lang/Object;I)V", false);
                    // [...,arrayRef, index, long value] <-
                } else {
                    // [...,arrayRef, index, value] <-
                    mv.visitInsn(Opcodes.DUP_X2);
                    // [...value, arrayRef, index, value] <-
                    mv.visitInsn(Opcodes.POP);
                    // [...value, arrayRef, index] <-
                    mv.visitInsn(Opcodes.DUP2_X1);
                    // [...arrayRef, index, value, arrayRef, index] <-
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "traceArrayStore", "(Ljava/lang/Object;I)V", false);
                    // [...arrayRef, index, value] <-
                }
            }

            super.visitInsn(opcode);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            // Read a field.
            // Before: [..., objRef] <-
            // After : [..., value] <-
            if (opcode == Opcodes.GETFIELD) {
                mv.visitInsn(Opcodes.DUP);
                // [..., objRef，objRef] <-
                mv.visitLdcInsn(owner);
                // [..., objRef, objRef, ownerStringRef] <-
                mv.visitLdcInsn(name);
                // [..., objRef, objRef, ownerStringRef, nameStringRef] <-
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "traceGetField", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", false);
                // [..., objRef] <-
            }

            // Read a static property.
            // Before: [...] <-
            // After: [...,value] <-
            if (opcode == Opcodes.GETSTATIC) {
                mv.visitLdcInsn(owner);
                // [..., ownerStringRef] <-
                mv.visitLdcInsn(name);
                // [..., ownerStringRef, nameStringRef] <-
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "traceGetStatic", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                // [...] <-
            }


            // Write a field
            // Before: [...,objRef, value] <-
            // Note: We do not konw the type of the value
            // After: [...,]<-
            if (opcode == Opcodes.PUTFIELD) {
//                mv.visitVarInsn(Opcodes.ALOAD, 0);
                // [..., objRef, value, objRef] <-
//                mv.visitLdcInsn(owner);
                // [..., objRef, value, objRef, ownerStringRef] <-
//                mv.visitLdcInsn(name);
                // [..., objRef, value, objRef, ownerStringRef, nameStringRef] <-
//                mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "tracePutField", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", false);
                // [..., objRef, value] <-
            }

            // Write a static property
            if (opcode == Opcodes.PUTSTATIC) {
                // [..., value] <-
                mv.visitLdcInsn(owner);
                // [..., value ownerStringRef] <-
                mv.visitLdcInsn(name);
                // [..., value ownerStringRef nameStringRef] <-
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(MemoryTraceUtils.class), "tracePutStatic", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                // [... value] <-
            }

            super.visitFieldInsn(opcode, owner, name, descriptor);

        }

    }
}
