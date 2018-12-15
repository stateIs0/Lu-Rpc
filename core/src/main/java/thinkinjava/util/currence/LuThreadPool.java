package thinkinjava.util.currence;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author 莫那·鲁道
 * @date 2018-12-15-10:37
 */
public class LuThreadPool extends ThreadPoolExecutor {

    public LuThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, LuThreadFactory luThreadFactory,
        LuRejectedExecutionHandler luRejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


}
