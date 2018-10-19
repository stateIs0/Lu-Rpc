package thinkinjava.filter;

import thinkinjava.core.Invoker;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:02
 */
public interface Filter {

    /**
     * 过滤器设计
     * @param invoker
     * @param req
     * @return
     */
    Response filter(Invoker invoker, Request req);
}
