package thinkinjava.filter;

import thinkinjava.core.Invoker;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:04
 */
public class ExampleFilter2 implements Filter {

    @Override
    public Response filter(Invoker invoker, Request req) throws Throwable {
        if (req == null) {
            throw new IllegalArgumentException("req  not be null");
        }
        System.out.println("ExampleFilter 2 ");
        return invoker.invoke(req);
    }
}
