package thinkinjava.util.currence;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thinkinjava.breaker.TimeWindow;
import thinkinjava.conf.DynamicProperties;
import thinkinjava.conf.DynamicProperty;
import thinkinjava.conf.Plugins;
import thinkinjava.exception.FlowBufferAddException;
import thinkinjava.exception.LuTimeoutException;

import static thinkinjava.conf.Constant.DEFAULT_FLOW_BUFFER_QUEUE_MAX_SIZE;
import static thinkinjava.conf.Constant.DEFAULT_FLOW_BUFFER_WAIT_TIME_OUT;
import static thinkinjava.conf.Constant.FLOW_BUFFER_QUEUE_MAX_SIZE;
import static thinkinjava.conf.Constant.FLOW_BUFFER_WAIT_TIME_OUT;
import static thinkinjava.util.currence.FlowBuffer.State.BLOCK;
import static thinkinjava.util.currence.FlowBuffer.State.RELEASE_BY_OTHER;
import static thinkinjava.util.currence.FlowBuffer.State.RELEASE_BY_SELF;


/**
 *
 * 流量缓冲带.
 *
 * 该组件用于应对突发流量导致的毛刺. 当流量过大,就会将请求贮存在队列中(timeout), 当有空闲资源时, 就会唤醒请求线程.
 */
public class FlowBuffer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowBuffer.class);

    private String unique;

    /** 通道类型. */
    private ChannelType channelType;

    public ConcurrentLinkedQueue<Node> queue = new ConcurrentLinkedQueue<>();

    /**
     * 队列当前的大小.
     */
    private AtomicInteger currentSize = new AtomicInteger();

    /**
     * 时间窗口内, 进入缓冲区的请求数.
     */
    private TimeWindow window = new TimeWindow(10);

    /**
     * 进入队列的总请求数, 用于调优.
     */
    private AtomicLong record = new AtomicLong();

    private DynamicProperties properties = Plugins.getInstance().getDynamicProperties();

    private DynamicProperty<Integer> bufferMaxSize_ = properties.getInteger(FLOW_BUFFER_QUEUE_MAX_SIZE, DEFAULT_FLOW_BUFFER_QUEUE_MAX_SIZE);
    private volatile int bufferMaxSize = bufferMaxSize_.get();

    private DynamicProperty<Long> waitTime_ = properties.getLong(FLOW_BUFFER_WAIT_TIME_OUT, DEFAULT_FLOW_BUFFER_WAIT_TIME_OUT);
    private volatile long waitTime = waitTime_.get();

    private FlowBuffer(Builder builder) {
        unique = builder.unique;
        channelType = builder.channelType;

        bufferMaxSize_.addCallback(() -> bufferMaxSize = bufferMaxSize_.get());
        waitTime_.addCallback(() -> waitTime = waitTime_.get());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 将当前请求加入缓冲区并等待.
     */
    public void addBufferAndWait() throws LuTimeoutException, FlowBufferAddException {
        Node node = new Node(Thread.currentThread(), BLOCK);

        if (currentSize.get() > bufferMaxSize) {
            throw new FlowBufferAddException("FlowBuffer Queue Full, Queue Size : " + bufferMaxSize);
        }

        boolean result = queue.offer(node);
        currentSize.incrementAndGet();
        record.incrementAndGet();
        window.getCurrentWindow().addTotal();

        if (result) {
            LockSupport.parkNanos(Thread.currentThread(), TimeUnit.MILLISECONDS.toNanos(waitTime));
            // 自己超时了, 释放一个空间.
            if (node.flag.compareAndSet(BLOCK, RELEASE_BY_SELF) && (currentSize.decrementAndGet() != -1)) {
                throw new LuTimeoutException("this request on Flow Buffer timeout, timeout time : " + waitTime +
                    ", unique : " + unique + ", channelType : " + channelType + ", bufferMaxSize : " + bufferMaxSize
                    + ", currentSize : " + currentSize.get());
            }

        } else {
            throw new FlowBufferAddException("offer FlowBuffer Queue Fail");
        }
    }

    /**
     * 唤醒缓冲区请求(顺手清理队列里超时的请求).
     */
    public void notifyBuffer() {
        Node node;
        // 条件不能变序
        while ((node = queue.poll()) != null && node.flag.compareAndSet(BLOCK, RELEASE_BY_OTHER)) {

            currentSize.decrementAndGet();

            LockSupport.unpark(node.currentThread);
            break;
        }
    }


    public enum State {
        /** 阻塞中 */
        BLOCK,
        /** 被别人唤醒. */
        RELEASE_BY_OTHER,
        /** 被自己唤醒. */
        RELEASE_BY_SELF
    }


    public static class Node {

        volatile Thread currentThread;
        final AtomicReference<State> flag;

        public Node(Thread currentThread, State flag) {
            this.currentThread = currentThread;
            this.flag = new AtomicReference<>(flag);
        }
    }

    public static final class Builder {

        String unique;
        private ChannelType channelType;

        private Builder() {
        }


        public FlowBuffer build() {
            return new FlowBuffer(this);
        }

        public Builder unique(String val) {
            unique = val;
            return this;
        }

        public Builder channelType(ChannelType val) {
            channelType = val;
            return this;
        }
    }


}
