package thinkinjava.conf;

public interface Config {

    /**
     * @param key String
     * @param defaultValue 默认值
     * @return 覆盖默认值.
     */
    String getProperty(String key, String defaultValue);

    /** 保持接口语义一致 */
    void setProperty(String key, String val);
}
