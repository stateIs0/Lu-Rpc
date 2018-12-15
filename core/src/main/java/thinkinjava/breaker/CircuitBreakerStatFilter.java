package thinkinjava.breaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thinkinjava.core.Invoker;
import thinkinjava.filter.Filter;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 *
 * 熔断数据统计.
 */
public class CircuitBreakerStatFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerStatFilter.class);


    private CircuitStateManager circuitStateManager = CircuitStateManager.getInstance();


    private CircuitBreakerStatFilter() {
    }

    public static CircuitBreakerStatFilter getInstance() {
        return CircuitBreakerStatFilterLazyHolder.INSTANCE;
    }

    private static class CircuitBreakerStatFilterLazyHolder {

        private static final CircuitBreakerStatFilter INSTANCE = new CircuitBreakerStatFilter();
    }

    @Override
    public Response filter(Invoker invoker, Request request) throws Throwable {

        LOGGER.info("CircuitBreakerStatFilter filtering");

        String unique = request.getUnique();
        // 无论成功失败, 都要统计.
        circuitStateManager.handlerNormal(unique);

        Response res = null;
        try {
            res = invoker.invoke(request);
        } catch (Throwable throwable) {
            circuitStateManager.handlerError(unique);
            throw throwable;
        }
        return res;
    }
}
