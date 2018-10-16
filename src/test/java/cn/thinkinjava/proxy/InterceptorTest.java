package cn.thinkinjava.proxy;

import cn.thinkinjava.api.client.LuConsumer;
import cn.thinkinjava.api.client.LuConsumerConfig;
import cn.thinkinjava.api.server.LuProvider;
import cn.thinkinjava.api.server.LuProviderConfig;
import cn.thinkinjava.rpc.remoting.LuUserProcessor;
import cn.thinkinjava.rpc.remoting.Url;
import cn.thinkinjava.rpc.remoting.testService.Demo;
import cn.thinkinjava.rpc.remoting.testService.DemoImpl;
import cn.thinkinjava.vmZK.LocalFileRegister;

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