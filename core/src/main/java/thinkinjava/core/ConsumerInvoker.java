package thinkinjava.core;

import thinkinjava.api.client.LuConsumerConfig;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;
import thinkinjava.rpc.remoting.LuClient;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:28
 */
public class ConsumerInvoker implements Invoker {

    private LuClient client;
    private LuConsumerConfig luConsumerConfig;

    public ConsumerInvoker(LuConsumerConfig luConsumerConfig) {
        this.luConsumerConfig = luConsumerConfig;
        this.client = LuClient.create();
    }

    public Response invoke(Request req) {
        preHandlerRequest(req);
        Response res = client.invoke(req);
        postHandlerResponse(res);
        return res;
    }

    public ServiceConfig getConfig() {
        return luConsumerConfig;
    }


    protected void preHandlerRequest(Request req) {
    }

    protected void postHandlerResponse(Response res) {

    }

}
