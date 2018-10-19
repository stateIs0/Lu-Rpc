package thinkinjava.rpc.remoting;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;

import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;

/**
 * @author 莫那·鲁道
 * @date 2018/10/14-下午8:38
 */
public class LuClient {

    final static RpcClient client = new RpcClient();
    static {
        client.init();
    }

    public static LuClient create() {
        return new LuClient();
    }

    private LuClient() {
    }

    public Response invoke(Request req) {
        try {

            Object result = client.invokeSync(req.getUrl(), req, 2000);
            return new Response(result);

        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("invoke error");
    }


}
