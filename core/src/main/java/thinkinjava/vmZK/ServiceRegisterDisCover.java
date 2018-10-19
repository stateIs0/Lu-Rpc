package thinkinjava.vmZK;

import thinkinjava.rpc.remoting.Url;

/**
 * 服务注册发现 ServiceRegisterDisCover
 *
 * 虚拟的zk
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:22
 */
public interface ServiceRegisterDisCover {


    void registerService(String serviceName, Url url);

    void unregisterService(String serviceName);

    Url getServiceUrl(String serviceName);

}
