package thinkinjava.breaker;

import java.util.concurrent.atomic.LongAdder;


public class Metrics {

    /** 请求总数量 */
    private LongAdder total = new LongAdder();
    /** 异常数量 */
    private LongAdder exception = new LongAdder();
    /** 此桶的计数开始时间 */
    private long startTime;

    public Metrics(long startTime) {
        this.startTime = startTime;
    }

    public void addException() {
        exception.add(1L);
    }

    public long getException() {
        return exception.sum();
    }

    public void addTotal() {
        total.add(1L);
    }

    public long getTotal() {
        return this.total.sum();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Metrics reset(long time) {
        total.reset();
        exception.reset();
        startTime = time;
        return this;
    }

    public Metrics reset() {
        total.reset();
        exception.reset();
        return this;
    }

    @Override
    public String toString() {
        return "\r\nMetrics{" +
            "total=" + total +
            ", exception=" + exception +
            ", startTime=" + startTime +
            '}';
    }
}
