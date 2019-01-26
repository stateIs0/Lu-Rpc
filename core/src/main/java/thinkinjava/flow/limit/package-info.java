/**
 *
 * @author 莫那·鲁道
 * @date 2019-01-26-11:08
 */
package thinkinjava.flow.limit;
/**
 * 限流模块, 利用了 EventBus 和 Filter, 实现限流, 目前设计了缓存区. 即限流后,会被加入到一个限时缓存区.
 * */