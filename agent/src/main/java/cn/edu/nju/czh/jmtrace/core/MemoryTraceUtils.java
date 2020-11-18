package cn.edu.nju.czh.jmtrace.core;


public class MemoryTraceUtils {
    public static void test() {
        System.out.println("This is hello from memory trace utils!");
    }

    private static String toHexString(long id) {
        if (id < 0) {
            id = -(id + 1);
        }
        char[] alphaBet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder result = new StringBuilder();
        while (id != 0) {
            int bit = (int) (id % 16);
            id /= 16;
            result.insert(0, alphaBet[bit]);
        }
        for (int i = 0; i < 16 - result.length(); i++) {
            result.append('0');
        }
        return result.toString();
    }

    private static StringBuilder generateArrayInfo(Object arrayRef, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Thread.currentThread().getId());
        stringBuilder.append(" ");
        String id = toHexString(arrayRef.hashCode());
        stringBuilder.append(id);
        String arrayName = arrayRef.getClass().getCanonicalName();
        stringBuilder.append(" ").append(arrayName.substring(0, arrayName.length() - 1));
        stringBuilder.append(index).append("]");
        return stringBuilder;
    }

    public static void traceArrayLoad(Object arrayRef, int index) {
        StringBuilder stringBuilder = generateArrayInfo(arrayRef, index);
        stringBuilder.insert(0, "R ");
        System.out.println(stringBuilder.toString());

    }

    public static void traceArrayStore(Object arrayRef, int index) {
        StringBuilder stringBuilder = generateArrayInfo(arrayRef, index);
        stringBuilder.insert(0, "W ");
        System.out.println(stringBuilder.toString());
    }


    private static StringBuilder generateFieldInfo(String owner, String name) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Thread.currentThread().getId());
        stringBuilder.append(" ");
        stringBuilder.append(toHexString((owner + name).hashCode())).append(" ");
        stringBuilder.append(owner.replace('/', '.')).append('.').append(name);
        return stringBuilder;
    }

    public static void traceGetField(String owner, String name) {
//        System.out.println("Owner " + owner + " name: " + name);
        StringBuilder stringBuilder = generateFieldInfo(owner, name);
        stringBuilder.insert(0, "R ");
        System.out.println(stringBuilder.toString());
    }

    public static void tracePutField(String owner, String name) {
        StringBuilder stringBuilder = generateFieldInfo(owner, name);
        stringBuilder.insert(0, "W ");
        System.out.println(stringBuilder.toString());
    }

}
