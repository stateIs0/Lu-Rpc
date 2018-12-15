package thinkinjava.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author 莫那·鲁道
 * @date 2018/10/14-下午9:39
 */
public class Request implements Serializable {

    private String serviceName;

    private String className;

    private String methodName;

    private Method method;

    private Object[] args;

    private Class<?>[] argTypes;

    private String ip;

    private int port;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class<?>[] argTypes) {
        this.argTypes = argTypes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return ip + ":" + port;
    }

    public void setUrl(String url) {
        if (url != null) {
            String[] arr = url.split(":");
            ip = arr[0];
            port = Integer.valueOf(arr[1]);
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUnique() {
        return ip + ":" +  port + "$" + serviceName;
    }


}
