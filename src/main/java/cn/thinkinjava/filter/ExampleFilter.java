package cn.thinkinjava.filter;

import cn.thinkinjava.core.Invoker;
import cn.thinkinjava.rpc.Request;
import cn.thinkinjava.rpc.Response;

/**
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:04
 */
public class ExampleFilter implements Filter {

    public Response filter(Invoker invoker, Request req) {
        if (req == null) {
            throw new IllegalArgumentException("req  not be null");
        }
        return invoker.invoke(req);
    }
}
