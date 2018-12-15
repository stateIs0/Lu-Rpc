package thinkinjava.util.currence;

public enum ChannelType {

    SYNC("sync", 0), ASYNC("async", 1);

    String name;
    int code;

    ChannelType(String name, int code) {
        this.name = name;
        this.code = code;
    }

}
