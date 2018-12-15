package thinkinjava.breaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import thinkinjava.conf.DynamicProperties;
import thinkinjava.conf.Plugins;

import static thinkinjava.conf.Constant.BUCKET_COUNT;
import static thinkinjava.conf.Constant.DEFAULT_BUCKET_COUNT;
import static thinkinjava.conf.Constant.DEFAULT_WINDOWS_LENGTH;
import static thinkinjava.conf.Constant.WINDOWS_LENGTH;

/**
 *
 * @author 莫那·鲁道
 * @date 2018-12-15-10:29
 */
public class TimeWindow {
    private static final long serialVersionUID = 4303657461699755715L;

    private transient DynamicProperties properties = Plugins.getInstance().getDynamicProperties();

    /** 窗口长度(单个时间长度) {} 秒 */
    private int windowsLength = properties.getInteger(WINDOWS_LENGTH, DEFAULT_WINDOWS_LENGTH).get();
    /** 桶的数量(10 个,也就是10秒) */
    private int bucketCount = properties.getInteger(BUCKET_COUNT, DEFAULT_BUCKET_COUNT).get();

    private int windowsTime = windowsLength * bucketCount;

    /** 滑动数组 */
    private AtomicReferenceArray<Metrics> array = new AtomicReferenceArray<>(bucketCount);

    private final ReentrantLock lock = new ReentrantLock();

    public TimeWindow() {
    }

    /**
     *
     * @param bucketCount 可以认为单位就是秒. 因为单个滑动窗口的时间长度是 1000 毫秒.
     */
    public TimeWindow(int bucketCount) {
        this.windowsLength = DEFAULT_WINDOWS_LENGTH;
        this.bucketCount = bucketCount;
        this.windowsTime = this.windowsLength * bucketCount;
        this.array = new AtomicReferenceArray<>(bucketCount);
    }

    /**
     *
     * 获取当前秒时间的桶.
     */
    public Metrics getCurrentWindow() {
        // 毫秒
        long time = System.currentTimeMillis();
        // 秒
        long s = time / windowsLength;
        // 获取桶的下标(循环数组)
        int idx = (int) (s % bucketCount);
        // 让该时间从零开始计数
        time = time - time % windowsLength;

        for (; ; ) {
            Metrics old = array.get(idx);
            if (old == null) {
                old = new Metrics(time);
                if (array.compareAndSet(idx, null, old)) {
                    return old;
                } else {
                    Thread.yield();
                }
            } else if (time == old.getStartTime()) {
                // 就是这个
                return old;
            } else if (time > old.getStartTime()) {
                if (lock.tryLock()) {
                    try {
                        // 重置
                        return old.reset(time);
                    } finally {
                        lock.unlock();
                    }
                } else {
                    Thread.yield();
                }
            } else if (time < old.getStartTime()) {
                // 通常不会发生.
                throw new RuntimeException();
            }
        }
    }

    public List<Metrics> values() {
        List<Metrics> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Metrics mb = array.get(i);
            // 必须在当前时间的窗口中
            if (mb == null || System.currentTimeMillis() - windowsTime > mb.getStartTime()) {
                continue;
            }
            list.add(mb);
        }
        return list;
    }

    public void reset() {
        for (int i = 0; i < array.length(); i++) {
            Metrics mb = array.get(i);
            if (mb != null) {
                mb.reset();
            }
        }
    }

}
