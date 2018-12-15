package thinkinjava.conf;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultConfig implements Config {

    private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public String getProperty(String key, String defaultValue) {
        String v = concurrentHashMap.get(key);
        if (v == null) {
            v = defaultValue;
        }
        return v;
    }

    @Override
    public void setProperty(String key, String val) {
        concurrentHashMap.put(key, val);
    }

    @Override
    public String toString() {
        return "DefaultConfig{" +
            "concurrentHashMap=" + concurrentHashMap +
            '}';
    }
}
