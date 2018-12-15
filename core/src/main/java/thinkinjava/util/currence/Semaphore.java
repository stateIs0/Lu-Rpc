package thinkinjava.util.currence;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 信号量(限流器).
 */
public class Semaphore implements java.io.Serializable {

    private static final long serialVersionUID = -3109378282317104994L;

    private String unique;

    private AtomicInteger counter = new AtomicInteger(0);

    private AtomicInteger permits;

    public Semaphore(String unique, int permits) {
        this.unique = unique;
        this.permits = new AtomicInteger(permits);
    }


    /** 获取信号量; 注意: 此处 while 在高并发下, 由于自旋, 可能引起 CPU 飙升 */
    public boolean tryAcquire() {
        int expect;
        while ((expect = counter.get()) < permits.get()) {
            return counter.compareAndSet(expect, expect + 1);
        }
        return false;
    }


    /** 归还信号量 */
    public void release() {
        if (counter.get() <= 0) {
            counter.set(0);
        } else {
            counter.decrementAndGet();
        }
    }

    /** 减小信号量阈值 */
    public void reducePermits(int reduction) {
        if (reduction < 0) {
            return;
        }
        if (permits.get() < reduction) {
            permits.set(0);
        }
        permits.compareAndSet(permits.get(), permits.get() - reduction);
    }

    /** 增加信号量阈值 */
    public void incrementPermits(int reduction) {
        if (reduction < 0) {
            return;
        }
        permits.compareAndSet(permits.get(), permits.get() + reduction);
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }
}
