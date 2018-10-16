package cn.thinkinjava.rpc;

import java.io.Serializable;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午9:39
 */
public class Response implements Serializable {

    private Object result;

    public Response(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
