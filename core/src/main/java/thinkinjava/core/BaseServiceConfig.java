package thinkinjava.core;

import thinkinjava.vmZK.LocalFileRegister;
import thinkinjava.vmZK.ServiceRegisterDisCover;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/15-下午9:41
 */
public abstract class BaseServiceConfig implements ServiceConfig {

    protected ServiceRegisterDisCover serviceRegisterDisCover = new LocalFileRegister();

    private Class<?> service;

    public String getServiceName() {
        return service.getName();
    }

    public void setService(Class<?> service) {
        this.service = service;
    }

    public Class getService() {
        return service;
    }
}
