package cn.thinkinjava.core;

import cn.thinkinjava.api.client.LuConsumerConfig;
import cn.thinkinjava.rpc.Request;
import cn.thinkinjava.rpc.Response;
import cn.thinkinjava.rpc.remoting.LuClient;

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
