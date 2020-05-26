package myself.demodocker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: ThreadTest
 * @description:
 * @author: YuZhiYuan
 * @date: 2020-05-25 15:59
 **/
public class ThreadTest {
    static volatile int i = 0;
    static volatile int t = 1;
    static AtomicInteger atomicInteger = new AtomicInteger();
    public static void main(String[] args) {
//        doSync();
//        doAtomic();
        for(int i = 0; i < 10; i++) {
            new Thread(()->{
                testLimit();
            }).start();
        }
    }
    @RateLimit
    public static String testLimit() {
        System.out.println("hello.........");
        return "Success..........";
    }

    public static void doSync() {
        Object lock = new Object();
        new Thread(()->{
            synchronized (lock) {
                while (i < 10) {
                    System.out.println("当前线程："+ Thread.currentThread().getName() + "值：" + ++i);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }).start();

        new Thread(()->{
            synchronized (lock) {
                while (i < 10) {
                    System.out.println("当前线程："+ Thread.currentThread().getName() + "值：" + ++i);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }).start();
    }

    public static void doAtomic() {

        new Thread(()->{
           while (atomicInteger.get() < 10) {
               while (t != 1) {}
               System.out.println("当前线程："+ Thread.currentThread().getName() + "值：" +atomicInteger.incrementAndGet());
               t = 2;
               try {
                   Thread.sleep(10);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }).start();

        new Thread(()->{
            while (atomicInteger.get() < 10) {
                while (t != 2) {}
                System.out.println("当前线程："+ Thread.currentThread().getName() + "值：" +atomicInteger.incrementAndGet());
                t = 1;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}