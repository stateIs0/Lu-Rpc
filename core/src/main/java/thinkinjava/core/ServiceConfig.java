package thinkinjava.core;

import thinkinjava.rpc.remoting.Url;

/**
 *
 * 服务配置类, 无论是客户端还是服务端, 通常需要接口名称, 设置接口类型, 以及接口 URL.
 *
 * @author 莫那·鲁道
 * @date 2018/10/15-下午4:23
 */
public interface ServiceConfig {

    String getServiceName();

    void setService(Class<?> service);

    Url getServiceUrl();
}
