package cn.edu.nju.czh.jmtrace.testcase.testcase1;

class Student {
    public int id;
    public double height;
    public static int num = 0;
    public int[] a;
    public Student(int id, double height) {
        this.id = id;
        this.height = height;
        this.a = new int[]{1,2,3,4,5};
    }

    public Student() {
        this.a = new int[]{1,2,3,4,5};
    }
}

public class StudentTest {
    public static void main(String[] args)  {
        Student jack = new Student();
        jack.id = 1;
        jack.height = 180.0;
        jack.id = 2;
        int jid = jack.id;
        Student.num += 1;
        Student tom = new Student(2, 156.0);
        int x = tom.id;
        double y = tom.height;
        Student.num += 1;
        tom.a[0] = 3;
    }
}
