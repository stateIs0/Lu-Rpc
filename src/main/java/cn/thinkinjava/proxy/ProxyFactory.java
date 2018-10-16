package cn.thinkinjava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午9:59
 */
public class ProxyFactory {

    public static Object getProxy(Class<?> origin,  InvocationHandler h) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{origin},
            h);
    }




}
