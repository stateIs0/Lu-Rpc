package thinkinjava.util.currence;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author 莫那·鲁道
 * @date 2018-12-15-10:33
 */
public class LuThreadPoolFactory  {

    private static int cpu = Runtime.getRuntime().availableProcessors();
    private static long keepAlive = 60 * 1000;
    private static TimeUnit unit = TimeUnit.MILLISECONDS;
    private static int queueSize = 1024;
    private static String name = "default-lu-rpc-threadPool";

    public static ThreadPoolExecutor getThreadPool(int core, String name) {
        return new LuThreadPool(
            core,
            core << 2,
            keepAlive,
            unit,
            new LinkedBlockingQueue<Runnable>(queueSize),
            new LuThreadFactory(name),
            new LuRejectedExecutionHandler());
    }

    public static ThreadPoolExecutor getThreadPool( String name) {
        return new LuThreadPool(
            cpu,
            cpu << 2,
            keepAlive,
            unit,
            new LinkedBlockingQueue<Runnable>(queueSize),
            new LuThreadFactory(name),
            new LuRejectedExecutionHandler());
    }

}
