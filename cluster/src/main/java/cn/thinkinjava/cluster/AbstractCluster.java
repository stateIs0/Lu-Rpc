package cn.thinkinjava.cluster;

import cn.thinkinjava.Cluster;
import thinkinjava.core.Invoker;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:05
 */
public abstract class AbstractCluster implements Cluster {


    public void add(Invoker invoker) {

    }

    public Invoker getWrapperInvoker() {
        return null;
    }
}
