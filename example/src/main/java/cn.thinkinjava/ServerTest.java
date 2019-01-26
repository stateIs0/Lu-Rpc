package cn.thinkinjava;

import thinkinjava.api.server.LuProvider;
import thinkinjava.api.server.LuProviderConfig;
import thinkinjava.rpc.remoting.LuUserProcessor;
import thinkinjava.rpc.remoting.Url;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:25
 */
public class ServerTest {
    public static void main(String[] args) throws NoSuchMethodException {
        LuUserProcessor.methods.put("hello", Demo.class.getMethod("hello", String.class));
        LuUserProcessor.targets.put("Demo", new DemoImpl());
        LuProviderConfig luProviderConfig = new LuProviderConfig();

        luProviderConfig.setService(Demo.class);
        luProviderConfig.setRef(DemoImpl.class);
        luProviderConfig.setServiceUrl(new Url("127.0.0.1", 8081));


        LuProvider luProvider = new LuProvider(luProviderConfig);

        luProvider.export();
    }
}
