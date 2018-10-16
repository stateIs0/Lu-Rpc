package cn.thinkinjava.conf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/16-下午8:29
 */
public class ServerConfig {

    private final static Map<String, Object> CONFIG = new ConcurrentHashMap<String, Object>();

    public static Object getProperty(String key) {
        Object v = System.getProperty(key);
        if (v == null) {
            return CONFIG.get(key);
        }

        return null;
    }

}
