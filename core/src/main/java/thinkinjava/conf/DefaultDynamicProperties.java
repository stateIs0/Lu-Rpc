package thinkinjava.conf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alipay.sofa.common.profile.StringUtil;

import thinkinjava.LifeCycle;
import thinkinjava.util.currence.LuThreadPoolFactory;


public class DefaultDynamicProperties implements DynamicProperties, LifeCycle {

    private Map<DynamicProperty, CopyOnWriteArrayList<Callback>> callbacks = new ConcurrentHashMap<>();

    private Config config = ConfigBuilder.create().build();

    private ThreadPoolExecutor pool = LuThreadPoolFactory.getThreadPool(1, getClass().getName());

    private DefaultDynamicProperties() {
        init();
    }

    public static DefaultDynamicProperties getInstance() {
        return SystemDynamicPropertiesLazyHolder.INSTANCE;
    }

    private static class SystemDynamicPropertiesLazyHolder {

        private static final DefaultDynamicProperties INSTANCE = new DefaultDynamicProperties();
    }

    @Override
    public void init() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        for (Map.Entry<DynamicProperty, CopyOnWriteArrayList<Callback>> e : callbacks.entrySet()) {
                            for (Callback callback : e.getValue()) {
                                callback.apply();
                            }
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        pool.shutdown();
    }

    @Override
    public DynamicProperty<String> getString(String name, String defaultVal) {
        return new DynamicProperty<String>() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public void addCallback(Callback callback) {
                addCallback0(this, callback);
            }

            @Override
            public String get() {
                return config.getProperty(name, defaultVal);
            }
        };
    }

    @Override
    public DynamicProperty<Integer> getInteger(String name, Integer defaultVal) {
        return new DynamicProperty<Integer>() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public void addCallback(Callback callback) {
                addCallback0(this, callback);
            }

            @Override
            public Integer get() {
                String r = config.getProperty(name, "");

                if (StringUtil.isBlank(r)) {
                    return defaultVal;
                }
                return Integer.valueOf(r);
            }
        };
    }

    @Override
    public DynamicProperty<Long> getLong(String name, Long defaultVal) {
        return new DynamicProperty<Long>() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public void addCallback(Callback callback) {
                addCallback0(this, callback);
            }

            @Override
            public Long get() {
                String r = config.getProperty(name, "");

                if (StringUtil.isBlank(r)) {
                    return defaultVal;
                }
                return Long.valueOf(r);
            }
        };
    }


    @Override
    public DynamicProperty<Boolean> getBoolean(String name, Boolean defaultVal) {
        return new DynamicProperty<Boolean>() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public void addCallback(Callback callback) {
                addCallback0(this, callback);
            }

            @Override
            public Boolean get() {
                String r = config.getProperty(name, "");

                if (StringUtil.isBlank(r)) {
                    return defaultVal;
                }
                return Boolean.valueOf(r);
            }
        };
    }

    private void addCallback0(DynamicProperty dynamicProperty, Callback callback) {
        if (callbacks.get(dynamicProperty) == null) {
            CopyOnWriteArrayList<Callback> list = new CopyOnWriteArrayList<>();
            list.add(callback);
            callbacks.put(dynamicProperty, list);
        } else {
            callbacks.get(dynamicProperty).add(callback);
        }

    }


}
