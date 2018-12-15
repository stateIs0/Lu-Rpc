package thinkinjava.conf;

/**
 *
 * @author 莫那·鲁道
 * @date 2018-12-15-10:42
 */
public interface Constant {

    /** 异常比例 double */
    String EXCEPTION_RATIO = "exception.ratio";
    int DEFAULT_EXCEPTION_RATIO = 50;


    /** 最少请求量 int */
    String LESS_REQUEST = "less.request";
    int DEFAULT_LESS_REQUEST = 20;

    /** 熔断后休眠时间 int 单位毫秒 */
    String SLEEP_TIME_SECONDS = "sleep.time.seconds";
    // 5 * 1000;
    int DEFAULT_SLEEP_TIME_SECONDS = 5000;

    /** 单个滑动窗口的时间长度,  int 单位 : 毫秒; 注意: 必须能被 1000 整除*/
    String WINDOWS_LENGTH = "windows.length";
    int DEFAULT_WINDOWS_LENGTH = 1000;

    /** 滑动窗口的窗口数量; int; */
    String BUCKET_COUNT = "bucket.count";
    int DEFAULT_BUCKET_COUNT = 10;

    /** 控制台端口号 */
    String ADMIN_PORT = "admin.port";
    int DEFAULT_ADMIN_PORT = 8321;

    /** 第三方服务被访问需要统计, 这个是统计的时间间隔 */
    String UNIQUE_HINT_COUNT_STATISTICS_INTERVAL = "unique.hint.count.statistics.interval";
    /** 5 * 60 (不能超过 int 最大值)*/
    int DEFAULT_UNIQUE_HINT_COUNT_STATISTICS_INTERVAL = 10;

    /** 流量缓冲区的队列长度, 超过长度则直接抛出限流异常 */
    String FLOW_BUFFER_QUEUE_MAX_SIZE = "flow.buffer.queue.max.size";
    int DEFAULT_FLOW_BUFFER_QUEUE_MAX_SIZE = 32;

    /** 流量缓冲区请求的缓冲时间, 单位毫秒 */
    String FLOW_BUFFER_WAIT_TIME_OUT = "flow.buffer.wait.time.out";
    long DEFAULT_FLOW_BUFFER_WAIT_TIME_OUT = 200;

}
