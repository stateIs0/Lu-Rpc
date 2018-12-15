package thinkinjava.conf;

public interface DynamicProperty<T> extends Property<T> {

    String getName();

    void addCallback(Callback callback);
}
