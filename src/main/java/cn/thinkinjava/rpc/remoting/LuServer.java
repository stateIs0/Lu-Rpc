package cn.thinkinjava.rpc.remoting;

import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.UserProcessor;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午8:49
 */
public class LuServer {

    private static RpcServer rpcServer;

    private volatile boolean flag;

    public static LuServer create(int port) {
        return new LuServer("127.0.0.1", port, false);
    }

    LuServer(String ip, int port, boolean manageConnection) {
        rpcServer = new RpcServer(ip, port, manageConnection);
        rpcServer.registerUserProcessor(new LuUserProcessor());
    }

    public void start() {
        if (flag) {
            return;
        }
        rpcServer.start();
        flag = true;
    }

    public void stop() {
        rpcServer.stop();
    }

    public RpcServer getServer() {
        return rpcServer;
    }

    public void registerUserProcessor(UserProcessor userProcessor) {
        rpcServer.registerUserProcessor(userProcessor);
    }


}
