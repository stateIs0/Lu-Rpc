package thinkinjava.rpc.remoting;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AbstractUserProcessor;

import thinkinjava.api.server.LuProvider;
import thinkinjava.api.server.ProviderManager;
import thinkinjava.rpc.Request;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午8:56
 */
@SuppressWarnings("unchecked")
public class LuUserProcessor extends AbstractUserProcessor<Request> {

    /** Just for Test */
    public static Map<String, Method> methods = new ConcurrentHashMap<String, Method>();
    /** Just for Test */
    public static Map<String, Object> targets = new ConcurrentHashMap<String, Object>();


    public LuUserProcessor() {
    }

    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, Request request) {
        // NOOP
    }

    public Object handleRequest(BizContext bizCtx, Request request) throws Exception {
        LuProvider provider = ProviderManager.getProvider(request.getServiceName());
        request.setMethod(provider.getConfig().getService().getDeclaredMethod(request.getMethodName(), request.getArgTypes()));

        return request.getMethod().invoke(provider.getConfig().getRefProxy(), request.getArgs());
    }


    /**
     * 用户请求的类名。
     * 使用String类型来避免类加载器问题。
     */
    public String interest() {
        return Request.class.getName();
    }
}
