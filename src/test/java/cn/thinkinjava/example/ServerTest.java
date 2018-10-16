package cn.thinkinjava.example;

import cn.thinkinjava.api.server.LuProvider;
import cn.thinkinjava.api.server.LuProviderConfig;
import cn.thinkinjava.rpc.remoting.LuUserProcessor;
import cn.thinkinjava.rpc.remoting.Url;
import cn.thinkinjava.rpc.remoting.testService.Demo;
import cn.thinkinjava.rpc.remoting.testService.DemoImpl;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午11:04
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
