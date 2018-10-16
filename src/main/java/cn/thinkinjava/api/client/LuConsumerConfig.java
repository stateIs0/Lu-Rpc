package cn.thinkinjava.api.client;

import cn.thinkinjava.core.BaseServiceConfig;
import cn.thinkinjava.rpc.remoting.Url;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:14
 */
public class LuConsumerConfig extends BaseServiceConfig {


    public Url getServiceUrl() {
        return serviceRegisterDisCover.getServiceUrl(getServiceName());
    }
}
