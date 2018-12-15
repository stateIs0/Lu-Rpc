package thinkinjava.breaker;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 熔断状态管理器.
 */
public class CircuitStateManager {

    private static final ConcurrentHashMap<String, CircuitState> CACHE = new ConcurrentHashMap<>();

    public static CircuitStateManager getInstance() {
        return CircuitStateManagerInner.INSTANCE;
    }

    private CircuitStateManager() {
    }


    private static class CircuitStateManagerInner {

        private static final CircuitStateManager INSTANCE = new CircuitStateManager();
    }

    /**
     * 获取状态.
     */
    public CircuitState getCircuitState(String unique) {
        CircuitState cs = CACHE.get(unique);
        if (cs == null) {
            cs = new CircuitState(unique);
            CircuitState old = CACHE.putIfAbsent(unique, cs);
            if (old != null) {
                cs = old;
            }
        }
        return cs;
    }

    /**
     * 处理错误信息.
     */
    public void handlerError(String unique) {
        WindowsManager.statisticError(unique);
    }

    /**
     *
     * 处理所有信息.
     *
     * @param unique
     */
    public void handlerNormal(String unique) {
        WindowsManager.statisticNormal(unique);
    }
}
