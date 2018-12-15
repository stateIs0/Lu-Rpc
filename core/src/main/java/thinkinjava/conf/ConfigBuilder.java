package thinkinjava.conf;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigBuilder.class);

    /** 单例全局, 系统依赖此配置对象. */
    private Config config;

    private static ConfigBuilder getInstance() {
        return ConfigBuilderInner.INSTANCE;
    }

    private ConfigBuilder() {
        init();
    }

    public void init() {
        ServiceLoader<Config> configs = ServiceLoader.load(Config.class);

        Iterator<Config> iterator = configs.iterator();
        while (iterator.hasNext()) {
            config = iterator.next();
            LOGGER.info("SPI Config Load Success , Config Implement Class : [{}]", config.getClass().getName());
            break;
        }
        if (config == null) {
            config = new DefaultConfig();
            LOGGER.info("No SPI Config, Config Implement Class : [{}]", config.getClass().getName());
        }
    }

    private static class ConfigBuilderInner {

        private static final ConfigBuilder INSTANCE = new ConfigBuilder();
    }

    public static ConfigBuilder create() {
        return getInstance();
    }

    /**
     * 返回的必须是单例.
     * @return 单例 config 对象.
     */
    public Config build() {
        return config;
    }


    @Override
    public String toString() {
        return "ConfigBuilder{" +
            "config=" + config +
            '}';
    }
}
