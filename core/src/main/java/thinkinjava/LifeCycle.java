package thinkinjava;

public interface LifeCycle {

    /** 初始化资源 */
    void init() throws Throwable;

    /** 关闭资源 */
    void destroy() throws Throwable;

}
