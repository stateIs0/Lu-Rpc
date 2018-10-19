package cn.thinkinjava;

import thinkinjava.core.Invoker;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:03
 */
public interface Cluster {

    void add(Invoker invoker);

    Invoker getWrapperInvoker();

}
