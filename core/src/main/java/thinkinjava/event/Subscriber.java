package thinkinjava.event;

/**
 *
 * 事件订阅者.
 */
public interface Subscriber {

    /**
     * 发生事件时回调此方法.
     * @param event
     */
    void onEvent(Event event);

    /**
     * 同步处理还是异步处理, 注意: 同步处理将会影响框架吞吐量.
     * @return
     */
    default boolean isSync() {
        return false;
    }
}
