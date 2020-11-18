package cn.edu.nju.czh.jmtrace.testcase.testcase2;

import java.util.Random;

class Partitioner {
    public int begin;
    public int end;
    public int splitPoint;
    public void partition(int[] a) {
        splitPoint = begin;
        int pivot = a[splitPoint];
        for(int i = begin; i <=end; i++) {
            if(a[i] < pivot) {
                splitPoint ++;
                if(i != splitPoint) {
                    int temp = a[i];
                    a[i] = a[splitPoint];
                    a[splitPoint] = temp;
                }
            }
        }
        a[begin] = a[splitPoint];
        a[splitPoint] = pivot;
    }
}

class Sorter {

    public void qSort(int[] a, int begin, int end) {
        if(begin < end) {
            Partitioner partitioner = new Partitioner();
            partitioner.begin = begin;
            partitioner.end = end;
            partitioner.partition(a);
            int splitPoint = partitioner.splitPoint;
            qSort(a, begin, splitPoint);
            qSort(a, splitPoint + 1, end);
        }
    }

    public void sort(int[] a) {
        int begin = 0;
        int end = a.length-1;
        qSort(a, begin, end);
    }
}

public class QuickSort {
    public static void main(String []args) {
        int[] array = new int[10];
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < 10; i++) {
            array[i] = random.nextInt(100000);
        }

        Sorter sorter = new Sorter();
        sorter.sort(array);
        for(int i = 0; i < 10; i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }
}
