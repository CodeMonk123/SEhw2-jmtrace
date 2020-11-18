package cn.edu.nju.czh.jmtrace.testcase.testcase4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Sorter implements Runnable {

    Long[] array;
    int id;
    int step;
    public static Long[] b = new Long[100];

    public Sorter(int id, int step ,Long[] array) {
        this.id = id;
        this.array = array;
        this.step = step;
    }

    @Override
    public void run() {
        for(int i = id; i < array.length; i+=step) {
            int index = 0;
            for(int j = 0; j< array.length; j++) {
                if(array[j] < array[i]) {
                    index ++;
                }
            }
            b[index] = array[i];
        }
    }
}


public class EnumSort {

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        for(int i = 0; i< 100; i++) {
            list.add((long)i);
        }
        Collections.shuffle(list);
        Long[] array = new Long[100];
        for(int i = 0; i< 100; i++) {
            array[i] = list.get(i);
        }
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 4; i++) {
            service.execute(new Sorter(i, 4, array));
        }
        service.shutdown();
        while(!service.isTerminated()) {}
        for(int i = 0; i < 10; i++) {
            System.out.print(Sorter.b[i] + " ");
        }
        System.out.println();
    }
}
