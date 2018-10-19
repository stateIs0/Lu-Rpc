package thinkinjava.proxy;

import thinkinjava.api.client.LuConsumer;
import thinkinjava.api.client.LuConsumerConfig;
import thinkinjava.api.server.LuProvider;
import thinkinjava.api.server.LuProviderConfig;
import thinkinjava.rpc.remoting.LuUserProcessor;
import thinkinjava.rpc.remoting.Url;
import thinkinjava.rpc.remoting.testService.Demo;
import thinkinjava.rpc.remoting.testService.DemoImpl;
import thinkinjava.vmZK.LocalFileRegister;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:52
 */
public class InterceptorTest {


    public static void main(String[] args) throws NoSuchMethodException {
        LuUserProcessor.methods.put("hello", Demo.class.getMethod("hello", String.class));
        LuUserProcessor.targets.put(Demo.class.getName(), new DemoImpl());
        LuProviderConfig<Demo> luProviderConfig = new LuProviderConfig<Demo>();
        luProviderConfig.setService(Demo.class);
        luProviderConfig.setRef(DemoImpl.class);

        LuProvider luProvider = new LuProvider(luProviderConfig);
        luProvider.export();

        LocalFileRegister.create().registerService(Demo.class.getName(), new Url("localhost", 8081));

        LuConsumerConfig luConsumerConfig = new LuConsumerConfig();
        luConsumerConfig.setService(Demo.class);

        LuConsumer<Demo> luConsumer = new LuConsumer<Demo>(luConsumerConfig);
        Demo demo = luConsumer.ref();

        System.out.println(demo.hello("hello"));
    }

}