package thinkinjava.core;

import java.lang.reflect.InvocationTargetException;

import thinkinjava.api.server.LuProviderConfig;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/16-下午7:41
 */
@SuppressWarnings("unchecked")
public class ProviderInvoker implements Invoker {

    private Object realObj;
    private ServiceConfig config;

    public ProviderInvoker(Object realObj, LuProviderConfig config) {
        this.realObj = realObj;
        this.config = config;
    }

    public Response invoke(Request req) {
        try {
            return new Response(req.getMethod().invoke(realObj, req.getArgs()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ServiceConfig getConfig() {
        return config;
    }

}
