package thinkinjava.vmZK;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import thinkinjava.rpc.remoting.Url;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/15-上午7:02
 */
public class LocalFileRegisterTest {

    private ServiceRegisterDisCover serviceRegisterDisCover;

    @Before
    public void setUp() throws Exception {

        serviceRegisterDisCover = LocalFileRegister.create();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void registerService() {
        serviceRegisterDisCover.registerService("hello", new Url("localhost", 8081));
        System.out.println(serviceRegisterDisCover.getServiceUrl("hello"));
        serviceRegisterDisCover.unregisterService("hello");

        System.out.println(serviceRegisterDisCover.getServiceUrl("hello"));

    }

    @Test
    public void getServerUrl() {
    }
}