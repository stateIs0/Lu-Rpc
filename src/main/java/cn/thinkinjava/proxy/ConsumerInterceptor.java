package cn.thinkinjava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.thinkinjava.core.Invoker;
import cn.thinkinjava.filter.FilterChain;
import cn.thinkinjava.rpc.Request;
import cn.thinkinjava.rpc.Response;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午9:59
 */
public class ConsumerInterceptor implements InvocationHandler {

    private Invoker invoker;

    private FilterChain chain;

    public ConsumerInterceptor(Invoker invoker) {
        // 过滤器链,链尾是真正的 invoker
        this.chain = new FilterChain(invoker);
        this.invoker = this.chain.buildFilterChain();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 构造请求体
        Request req = buildRequest(method, args);

        // 2. 调用过滤器链
        Response res = invoker.invoke(req);
        return res.getResult();
    }

    /**
     * 构造请求体
     */
    private Request buildRequest(Method method, Object[] args) {
        Request req = new Request();
        req.setServiceName(invoker.getConfig().getServiceName());
        req.setArgs(args);
        req.setMethodName(method.getName());
        req.setIp(invoker.getConfig().getServiceUrl().getIp());
        req.setPort(invoker.getConfig().getServiceUrl().getPort());
        req.setArgTypes(method.getParameterTypes());

        return req;
    }

}
