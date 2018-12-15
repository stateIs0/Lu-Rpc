package thinkinjava.flow.limit;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thinkinjava.core.Invoker;
import thinkinjava.event.EventBus;
import thinkinjava.exception.FlowBufferAddException;
import thinkinjava.exception.LimitException;
import thinkinjava.exception.LuTimeoutException;
import thinkinjava.filter.Filter;
import thinkinjava.rpc.Request;
import thinkinjava.rpc.Response;
import thinkinjava.util.currence.FlowBuffer;
import thinkinjava.util.currence.Semaphore;


/**
 * 限流过滤器.
 */
public class LimitFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitFilter.class);


    private static final int RETRY_COUNT = 1;

    private LimitFilter() {
    }

    public static LimitFilter getInstance() {
        return LimitFilterLazyHolder.INSTANCE;
    }

    private static class LimitFilterLazyHolder {

        private static final LimitFilter INSTANCE = new LimitFilter();
    }


    @Override
    public Response filter(Invoker invoker, Request request) throws Throwable {

        LOGGER.info("LimitFilter filtering");

        String unique = request.getUnique();

        Semaphore semaphore = SemaphoreManager.getInstance().getSemaphore(unique);

        int count = 0;
        loop:
        for (; ; ) {

            FlowBuffer flowBuffer = FlowBufferManager.getInstance().getBuffer(unique);
            if (semaphore.tryAcquire()) {
                try {
                    // 这里不处理异常, 直接抛出.
                    return invoker.invoke(request);
                } finally {
                    semaphore.release();
                    flowBuffer.notifyBuffer();
                }
            } // 抢信号量失败
            else {
                try {
                    // 仅仅等待一次
                    if (++count <= RETRY_COUNT) {
                        // 进入队列可能会失败, 也可能会等待超时. 我们都认为是限流异常.
                        flowBuffer.addBufferAndWait();
                        // 唤醒后, 再次重新尝试获取信号量.
                        continue loop;
                    }
                    break;
                } catch (LuTimeoutException | FlowBufferAddException e) {
                    // 忽略, 认为是限流异常.
                }

            }
        }
        // 限流事件
        EventBus.post(LimitEvent.newBuilder().date(new Date()).build());

        return throwsException();
    }


    private Response throwsException() {
        throw new LimitException();
    }

}
