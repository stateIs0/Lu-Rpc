package thinkinjava.api.client;

import thinkinjava.core.ConsumerInvoker;
import thinkinjava.proxy.ConsumerInterceptor;
import thinkinjava.proxy.ProxyFactory;

/**
 * 服务消费者 API
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:13
 */
public class LuConsumer<T> {

    private LuConsumerConfig config;

    private T proxy;

    public LuConsumer(LuConsumerConfig config) {
        this.config = config;
    }

    /**
     * 从注册中心引用一个服务. <T> 即接口类型.
     */
    @SuppressWarnings("unchecked")
    public T ref() {

        if (proxy != null) {
            return proxy;
        }
        proxy = (T) ProxyFactory.getProxy(config.getService(), new ConsumerInterceptor(new ConsumerInvoker(config)));
        return proxy;
    }

}
