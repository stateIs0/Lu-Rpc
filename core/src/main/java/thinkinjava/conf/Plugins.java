package thinkinjava.conf;

import java.util.ServiceLoader;


public class Plugins {

    private final DynamicProperties dynamicProperties;

    private Plugins() {
        dynamicProperties = loadDynamicProperties();
    }


    public static Plugins getInstance() {
        return PluginsLazyHolder.INSTANCE;
    }

    private static class PluginsLazyHolder {

        private static final Plugins INSTANCE = new Plugins();
    }


    public DynamicProperties getDynamicProperties() {
        return dynamicProperties;
    }

    private DynamicProperties loadDynamicProperties() {
        DynamicProperties d = getPluginImpl(DynamicProperties.class, DefaultDynamicProperties.getInstance());

        if (d == null) {
            d = findSPI(DynamicProperties.class, getClass().getClassLoader());
        }
        if (d == null) {
            d = DefaultDynamicProperties.getInstance();
        }
        return d;
    }


    /**
     * 先加载配置文件. 再加载 SPI. 最后使用默认(从系统环境变量获取).
     *
     * @param pluginClass
     * @param dynamicProperties
     * @param <T>
     * @return
     */
    private static <T> T getPluginImpl(Class<T> pluginClass, DynamicProperties dynamicProperties) {
        String name = pluginClass.getSimpleName();

        String propertyName = "lu-rpc.config.plugin." + name + ".impl";

        String impl = dynamicProperties.getString(propertyName, null).get();
        if (impl != null) {
            try {
                Class<?> cls = Class.forName(impl);
                // narrow the scope (cast) to the type we're expecting
                cls = cls.asSubclass(pluginClass);
                return (T) cls.newInstance();
            } catch (ClassCastException e) {
                throw new RuntimeException(
                    name + " implementation is not an instance of " + name + ": " + impl);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(name + " implementation class not found: " + impl, e);
            } catch (InstantiationException e) {
                throw new RuntimeException(name + " implementation not able to be instantiated: " + impl, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(name + " implementation not able to be accessed: " + impl, e);
            }
        } else {
            return null;
        }
    }

    private static <T> T findSPI(Class<T> spi, ClassLoader classLoader) {
        ServiceLoader<T> sl = ServiceLoader.load(spi,
            classLoader);
        for (T s : sl) {
            if (s != null) {
                return s;
            }
        }
        return null;
    }
}
