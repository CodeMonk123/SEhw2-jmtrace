package cn.edu.nju.czh.jmtrace.testcase.testcase3;

import java.util.Random;

class PartitionerForLong {
    public int begin;
    public int end;
    public int splitPoint;
    public void partition(long[] a) {
        splitPoint = begin;
        long pivot = a[splitPoint];
        for(int i = begin; i <=end; i++) {
            if(a[i] < pivot) {
                splitPoint ++;
                if(i != splitPoint) {
                    long temp = a[i];
                    a[i] = a[splitPoint];
                    a[splitPoint] = temp;
                }
            }
        }
        a[begin] = a[splitPoint];
        a[splitPoint] = pivot;
    }
}

class SorterForLong {

    public void qSortForLong(long[] a, int begin, int end) {
        if(begin < end) {
            PartitionerForLong partitioner = new PartitionerForLong();
            partitioner.begin = begin;
            partitioner.end = end;
            partitioner.partition(a);
            int splitPoint = partitioner.splitPoint;
            qSortForLong(a, begin, splitPoint);
            qSortForLong(a, splitPoint + 1, end);
        }
    }

    public void sortForLong(long[] a) {
        int begin = 0;
        int end = a.length-1;
        qSortForLong(a, begin, end);
    }
}


public class QuickSortForLong {
    public static  void main(String[] args) {
        long[] a = new long[100];
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < a.length; i++) {
            a[i] = random.nextLong();
        }
        SorterForLong sorterForLong = new SorterForLong();
        sorterForLong.sortForLong(a);
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
            if(i > 10) {
                break;
            }
        }
    }
}

