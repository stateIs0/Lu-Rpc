package cn.thinkinjava.example;

import cn.thinkinjava.api.client.LuConsumer;
import cn.thinkinjava.api.client.LuConsumerConfig;
import cn.thinkinjava.rpc.remoting.testService.Demo;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/16-下午7:07
 */
public class ClientTest {

    public static void main(String[] args) throws InterruptedException {
        LuConsumerConfig luConsumerConfig = new LuConsumerConfig();
        luConsumerConfig.setService(Demo.class);
        LuConsumer<Demo> luConsumer = new LuConsumer<Demo>(luConsumerConfig);
        Demo demo = luConsumer.ref();

        for (; ; ) {
            System.out.println(demo.hello("hello"));
            Thread.sleep(1111);
        }
    }

}
