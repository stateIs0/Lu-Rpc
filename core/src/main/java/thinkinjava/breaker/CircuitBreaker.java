package thinkinjava.breaker;

import java.util.Date;

import thinkinjava.conf.DynamicProperties;
import thinkinjava.conf.DynamicProperty;
import thinkinjava.conf.Plugins;
import thinkinjava.event.EventBus;

import static thinkinjava.breaker.CircuitBreaker.State.CLOSE;
import static thinkinjava.breaker.CircuitBreaker.State.HALF_OPEN;
import static thinkinjava.breaker.CircuitBreaker.State.OPEN;
import static thinkinjava.conf.Constant.DEFAULT_EXCEPTION_RATIO;
import static thinkinjava.conf.Constant.DEFAULT_LESS_REQUEST;
import static thinkinjava.conf.Constant.DEFAULT_SLEEP_TIME_SECONDS;
import static thinkinjava.conf.Constant.EXCEPTION_RATIO;
import static thinkinjava.conf.Constant.LESS_REQUEST;
import static thinkinjava.conf.Constant.SLEEP_TIME_SECONDS;

/**
 *
 * 熔断器.
 *
 * 4 种状态变化:
 *  1. CLOSE to OPEN
 *  2. OPEN to HALF_OPEN
 *  3. HALF_OPEN to OPEN
 *  4. HALF_OPEN to CLOSE
 *
 */
public class CircuitBreaker {

    public static CircuitBreaker getInstance() {
        return CircuitBreakerInner.INSTANCE;
    }

    private volatile int sleepTime;
    private volatile int less;
    private volatile int expRatio;

    private DynamicProperty<Integer> sleepTime_;
    private DynamicProperty<Integer> less_;
    private DynamicProperty<Integer> expRatio_;

    DynamicProperties properties = Plugins.getInstance().getDynamicProperties();

    private CircuitBreaker() {
        init();
    }

    private static class CircuitBreakerInner {

        private static final CircuitBreaker INSTANCE = new CircuitBreaker();
    }

    private void init() {
        sleepTime_ = properties.getInteger(SLEEP_TIME_SECONDS, DEFAULT_SLEEP_TIME_SECONDS);
        sleepTime = sleepTime_.get();
        sleepTime_.addCallback(() -> sleepTime = sleepTime_.get());

        less_ = properties.getInteger(LESS_REQUEST, DEFAULT_LESS_REQUEST);
        less = less_.get();
        less_.addCallback(() -> less = less_.get());

        expRatio_ = properties.getInteger(EXCEPTION_RATIO, DEFAULT_EXCEPTION_RATIO);
        expRatio = expRatio_.get();
        expRatio_.addCallback(() -> expRatio = expRatio_.get());
    }

    /**
     * 尝试调用:
     *  如果是 CLOSE 状态, 立即调用.
     *  如果是 HALF_OPEN 状态, 停止调用.
     *  如果是 OPEN 状态, 检查是否超过休眠时间, 如果超过, 尝试修改为半开并调用, 防止并发时过多请求进入.
     * @param state
     * @return true 表示可以调用, 反之不能调用.
     */
    public boolean tryInvoke(CircuitState state) {
        if (state.getState().get() == CLOSE) {
            return true;
        }
        if (state.getState().get() == HALF_OPEN) {
            return false;
        }

        if (state.getStartTime() + sleepTime < System.currentTimeMillis()) {
            // open to half_open
            return state.getState().compareAndSet(OPEN, HALF_OPEN);
        }
        return true;


    }

    /**
     * 当 HALF_OPEN 时调用成功.
     * @param state
     */
    public void success(CircuitState state) {
        // half_open To Close
        if (state.getState().compareAndSet(HALF_OPEN, CLOSE)) {
            state.setStartTime(-1);
            // 发布恢复事件.
            publishRenewEvent(state);
        }
    }

    /**
     *  HALF_OPEN 调用失败: 修改为 OPEN.
     *  CLOSE 调用失败, 判断是否超过错误百分比.
     * @param state
     */
    public void fail(CircuitState state) {
        if (state.getState().get() == HALF_OPEN) {
            tryHalfOpen2open(state);
        } else if (state.getState().get() == CLOSE) {
            tryClose2open(state);
        }
    }

    private void tryClose2open(CircuitState state) {
        long total = WindowsManager.getTotal(state.getUnique());

        // 最少得 {xxx} 个请求.才触发熔断逻辑
        if (total < less) {
            return;
        }
        // 例如大于 {xxx}% ,熔断.
        if (WindowsManager.getExceptionRatio(state.getUnique()) >= expRatio) {
            if (state.getState().compareAndSet(CLOSE, OPEN)) {
                // 更新时间
                state.setStartTime(System.currentTimeMillis());

                // 发布熔断事件.
                publishBreakerEvent(state);
            }
        }
    }

    private void tryHalfOpen2open(CircuitState state) {
        if (state.getState().compareAndSet(HALF_OPEN, OPEN)) {
            state.setStartTime(System.currentTimeMillis());

            // 发布熔断事件. 相当于没 sleepTime 发布一次事件.
            publishBreakerEvent(state);
        }

    }


    /**
     * 发布熔断事件.
     */
    private void publishBreakerEvent(CircuitState state) {
        // 发布熔断事件
        CircuitBreakerEvent event = CircuitBreakerEvent.newBuilder().unique(state.getUnique()).startTime(new Date()).build();
        EventBus.post(event);
    }

    /**
     * 发布恢复事件.
     */
    private void publishRenewEvent(CircuitState state) {
        // 发布恢复事件.
        CircuitRenewEvent renewEvent = CircuitRenewEvent.newBuilder().unique(state.getUnique()).endTime(new Date()).build();
        EventBus.post(renewEvent);
    }


    public enum State {
        /** 熔断器打开 */
        OPEN,
        /** 熔断器半开 */
        HALF_OPEN,
        /** 熔断器关闭 */
        CLOSE
    }

}
