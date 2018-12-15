package thinkinjava.rpc.remoting;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import thinkinjava.rpc.Request;
import thinkinjava.rpc.remoting.testService.Demo;
import thinkinjava.rpc.remoting.testService.DemoImpl;

public class RpcTest {

    public static Map<String, Method> methods = new ConcurrentHashMap<String, Method>();

    public static Map<String, Object> targets = new ConcurrentHashMap<String, Object>();


    @Before
    public void before() {
        try {
            LuUserProcessor.methods.put("hello", Demo.class.getMethod("hello", String.class));
            LuUserProcessor.targets.put("Demo", new DemoImpl());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test() {
        LuServer server = new LuServer("127.0.0.1", 8081, false);
        server.registerUserProcessor(new LuUserProcessor());

        server.start();

        LuClient client = LuClient.create();

        Request req = new Request();

        req.setClassName(Demo.class.getName());
        req.setMethodName("hello");
        req.setArgs(new Object[]{"client"});
        req.setServiceName("Demo");

        Object result = client.invoke(req);
        System.out.println(result);

        server.stop();
    }

}
