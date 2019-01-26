package cn.thinkinjava;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:26
 */
public class DemoImpl implements Demo {

    public String hello(String string) {
        System.out.println("receive client : " + string);
        return "server reply --- >" + string;
    }
}
