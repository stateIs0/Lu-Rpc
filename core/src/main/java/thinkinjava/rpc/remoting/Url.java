package thinkinjava.rpc.remoting;

import java.io.Serializable;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/16-下午6:08
 */
public class Url implements Serializable {

    public static Url DEFAULT = new Url();

    String ip = "127.0.0.1";

    int port = 8081;

    String password = "helloWorld";

    String token = "LoDao";

    public Url() {
    }

    public Url(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Url(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
