package thinkinjava.filter;

import thinkinjava.core.Invoker;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

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
