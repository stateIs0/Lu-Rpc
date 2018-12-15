package thinkinjava.breaker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thinkinjava.core.Invoker;
import thinkinjava.exception.CircuitBreakerException;
import thinkinjava.filter.Filter;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 * 熔断检查过滤器
 */
public class CircuitBreakerFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerFilter.class);



    private CircuitBreaker circuitBreaker = CircuitBreaker.getInstance();

    public static CircuitBreakerFilter getInstance() {
        return CircuitBreakerFilterInner.INSTANCE;
    }


    private static class CircuitBreakerFilterInner {

        private static final CircuitBreakerFilter INSTANCE = new CircuitBreakerFilter();
    }

    private CircuitBreakerFilter() {
    }


    @Override
    public Response filter(Invoker invoker, Request request) throws Throwable {

        LOGGER.info("CircuitBreakerFilter filtering");

        String unique = request.getUnique();
        CircuitState cs = CircuitStateManager.getInstance().getCircuitState(unique);

        // 可能是 CLOSE, 也可能是 HALF_OPEN
        if (circuitBreaker.tryInvoke(cs)) {
            try {
                Response res = invoker.invoke(request);

                // 调用成功, 关闭 HALF_OPEN.
                circuitBreaker.success(cs);

                return res;
            } catch (Throwable throwable) {
                // 处理异常.
                circuitBreaker.fail(cs);
                throw throwable;
            }
        }
        throw new CircuitBreakerException("CircuitBreaker State : " + cs.getState().get().name() + ", unique : " + unique);

    }

}
