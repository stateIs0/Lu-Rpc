package thinkinjava.api.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/16-下午7:12
 */
public class ProviderManager {

    private static Map<String, LuProvider> cache = new ConcurrentHashMap<String, LuProvider>();

    public static LuProvider getProvider(String name) {
        return cache.get(name);
    }

    public static void putProvider(String name, LuProvider luProvider) {
        cache.put(name, luProvider);
    }


}
