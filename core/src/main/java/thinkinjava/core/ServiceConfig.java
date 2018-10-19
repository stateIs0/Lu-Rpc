package thinkinjava.core;

import thinkinjava.rpc.remoting.Url;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/15-下午4:23
 */
public interface ServiceConfig {

    String getServiceName();

    void setService(Class<?> service);

    Url getServiceUrl();
}
