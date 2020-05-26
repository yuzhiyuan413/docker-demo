package myself.demodocker.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: CounterAtomic
 * @description:
 * @author: YuZhiYuan
 * @date: 2020-05-25 18:54
 **/
public class CounterAtomic {
    static final AtomicInteger atomicInteger = new AtomicInteger();
    public static int incr() {
        return atomicInteger.incrementAndGet();
    }

    public static int desc() {
        return atomicInteger.decrementAndGet();
    }

    public static int get() {
        return atomicInteger.get();
    }
}