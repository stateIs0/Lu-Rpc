package cn.thinkinjava;

import thinkinjava.api.client.LuConsumer;
import thinkinjava.api.client.LuConsumerConfig;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:25
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
