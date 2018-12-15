package thinkinjava.breaker;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间窗口生命周期管理器.
 */
public class WindowsManager {

    private static final Map<String, TimeWindow> map = new ConcurrentHashMap<>();


    public static WindowsManager getInstance() {
        return StatisticManagerInner.INSTANCE;
    }

    private WindowsManager() {
    }

    private static class StatisticManagerInner {

        private static final WindowsManager INSTANCE = new WindowsManager();
    }

    /**
     * 处理异常.(对异常数加一)
     * @param unique
     */
    public static void statisticError(String unique) {
        TimeWindow sw = getTimeWindow(unique);
        Metrics mb = sw.getCurrentWindow();
        mb.addException();
    }

    /**
     * 处理请求(对总数 加一).
     * @param unique
     */
    public static void statisticNormal(String unique) {
        TimeWindow sw = getTimeWindow(unique);
        Metrics mb = sw.getCurrentWindow();
        mb.addTotal();
    }

    /**
     * 获取异常数.
     * @param unique
     * @return
     */
    public static long getexception(String unique) {
        TimeWindow sw = getTimeWindow(unique);
        List<Metrics> list = sw.values();
        long exception = 0;
        for (Metrics metricsBucket : list) {
            exception += metricsBucket.getException();
        }
        return exception;
    }


    /**
     * 获取总数
     * @param unique
     * @return
     */
    public static long getTotal(String unique) {
        TimeWindow sw = getTimeWindow(unique);
        List<Metrics> list = sw.values();
        long total = 0;
        for (Metrics metrics : list) {
            total += metrics.getTotal();
        }
        return total;
    }

    /**
     * 获取该时间窗口的异常比例
     */
    public static double getExceptionRatio(String unique) {
        TimeWindow sw = getTimeWindow(unique);
        List<Metrics> list = sw.values();
        long total = 0;
        long exception = 0;
        for (Metrics metricsBucket : list) {
            total += metricsBucket.getTotal();
            exception += metricsBucket.getException();
        }
        if (total == 0) {
            return 0.0;
        }
        return (double) exception / (double) total;
    }

    private static TimeWindow getTimeWindow(String unique) {
        TimeWindow sw = map.get(unique);
        if (sw == null) {
            sw = new TimeWindow();

            TimeWindow old = map.putIfAbsent(unique, sw);
            if (old != null) {
                sw = old;
            }
        }
        return sw;
    }

}
