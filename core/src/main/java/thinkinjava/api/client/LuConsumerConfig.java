package thinkinjava.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thinkinjava.core.BaseServiceConfig;
import thinkinjava.exception.UserOperationException;
import thinkinjava.rpc.remoting.Url;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:14
 */
public class LuConsumerConfig extends BaseServiceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger("LuConsumerConfig");


    @Override
    public Url getServiceUrl() {
        try {
            return serviceRegisterDisCover.getServiceUrl(getServiceName());

        } catch (UserOperationException e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }
        return null;
    }
}
