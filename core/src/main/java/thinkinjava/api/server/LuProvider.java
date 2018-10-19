package thinkinjava.api.server;

import thinkinjava.rpc.remoting.LuServer;

/**
 * 服务提供者 API
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:18
 */
@SuppressWarnings("unchecked")
public class LuProvider<T> {

    private LuProviderConfig<T> config;

    public LuProvider(LuProviderConfig config) {
        this.config = config;
    }

    /**
     * 发布到注册中心,同时启动 RPC 服务器. 端口 8081
     */
    public void export() {
        // 注册到一个地方. 代表发布出去. 例如 zk
        config.registerService(config.getService(), config.getServiceUrl());

        ProviderManager.putProvider(config.getServiceName(), this);

        LuServer.create(8081).start();
    }

    public LuProviderConfig getConfig() {
        return config;
    }

}
