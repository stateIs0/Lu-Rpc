package thinkinjava.event;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thinkinjava.util.currence.LuThreadPoolFactory;


/**
 * 事件总线.
 */
public class EventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private static final ConcurrentHashMap<Class<? extends Event>, CopyOnWriteArraySet<Subscriber>> MAP = new ConcurrentHashMap<>();

    private static final ThreadPoolExecutor EXECUTOR = LuThreadPoolFactory.getThreadPool(EventBus.class.getName());

    private static final AtomicBoolean flag = new AtomicBoolean();

    static {
        EXECUTOR.prestartAllCoreThreads();
    }

    private EventBus() {
    }

    public static void destroy() {
        EXECUTOR.shutdown();
        flag.compareAndSet(false, true);
    }

    /**
     * 发布事件.
     * @param event 事件.
     */
    public static void post(Event event) {
        if (flag.get()) {
            LOGGER.warn("EventBus always destroy, can not to be post event !");
            return;
        }
        if (event == null) {
            return;
        }
        CopyOnWriteArraySet<Subscriber> set = MAP.get(event.getClass());
        if (set == null) {
            return;
        }
        for (Subscriber subscribe : set) {
            if (subscribe.isSync()) {
                handleEvent(event, subscribe);
            } else {
                EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleEvent(event, subscribe);
                    }
                });

            }

        }
    }


    /**
     * 订阅事件.
     * @param eventClass 事件类型.
     * @param subscriber 订阅者.
     */
    public static void register(Class<? extends Event> eventClass, Subscriber subscriber) {
        CopyOnWriteArraySet<Subscriber> set = MAP.get(eventClass);
        if (set == null) {
            set = new CopyOnWriteArraySet<>();
            CopyOnWriteArraySet<Subscriber> old = MAP.putIfAbsent(eventClass, set);
            if (old != null) {
                set = old;
            }

        }
        set.add(subscriber);

        LOGGER.debug("subscriber : {} register  a event: {} ", subscriber, eventClass);
    }

    /**
     * 取消订阅事件.
     *
     * @param eventClass 事件类型.
     * @param subscriber 订阅者.
     */
    public static void unRegister(Class<? extends Event> eventClass, Subscriber subscriber) {
        CopyOnWriteArraySet<Subscriber> set = MAP.get(eventClass);
        if (set == null) {
            set = new CopyOnWriteArraySet<>();
            CopyOnWriteArraySet<Subscriber> old = MAP.putIfAbsent(eventClass, set);
            if (old != null) {
                set = old;
            }

        }
        set.remove(subscriber);
        LOGGER.debug("subscriber : {} unRegister  a event: {} ", subscriber, eventClass);

    }


    /**
     * 处理事件.
     * @param event 事件.
     * @param subscriber 订阅者.
     */
    private static void handleEvent(final Event event, final Subscriber subscriber) {
        try {
            subscriber.onEvent(event);
        } catch (Exception e) {
            LOGGER.error("subscriber {} handler event {} fail ", subscriber, event, e);
        }
    }

}
