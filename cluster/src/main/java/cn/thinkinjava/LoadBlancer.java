package cn.thinkinjava;

import java.util.List;

import thinkinjava.core.Invoker;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/19-下午10:56
 */
public interface LoadBlancer {

    Invoker select(List<Invoker> invokers);


}
