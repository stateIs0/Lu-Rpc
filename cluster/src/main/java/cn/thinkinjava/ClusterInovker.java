package cn.thinkinjava;

import thinkinjava.core.Invoker;
import thinkinjava.core.ServiceConfig;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:06
 */
public abstract class ClusterInovker implements Invoker {

    public Response invoke(Request req) {
        return null;
    }

    public ServiceConfig getConfig() {
        return null;
    }

    protected abstract Response doInvoke(Request req);
}
