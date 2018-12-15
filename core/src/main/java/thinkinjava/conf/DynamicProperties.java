package thinkinjava.conf;

public interface DynamicProperties {

    DynamicProperty<String> getString(String name, String defaultVal);

    DynamicProperty<Integer> getInteger(String name, Integer defaultVal);

    DynamicProperty<Long> getLong(String name, Long defaultVal);

    DynamicProperty<Boolean> getBoolean(String name, Boolean defaultVal);


}
