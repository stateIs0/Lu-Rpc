package thinkinjava.rpc.remoting.testService;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午9:50
 */
public class DemoImpl implements Demo {


    public String hello(String string) {
        System.out.println("receive client : " + string);
        return "server reply --- >" + string;
    }
}
