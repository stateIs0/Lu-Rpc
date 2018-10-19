package cn.thinkinjava.loadBlancer;

import java.util.List;

import cn.thinkinjava.LoadBlancer;
import thinkinjava.core.Invoker;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午11:00
 */
public abstract class AbstractLoadBlancer implements LoadBlancer {

    protected List<Invoker> invokers;

    public AbstractLoadBlancer(List<Invoker> invokers) {
        this.invokers = invokers;
    }

    public Invoker select(List<Invoker> invokers) {
        return doSelect(invokers);
    }

    protected abstract Invoker doSelect(List<Invoker> invokers);
}
