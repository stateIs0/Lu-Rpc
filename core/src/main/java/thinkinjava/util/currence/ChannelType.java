package thinkinjava.util.currence;

/**
 * 用于细化流量类型, 即:将一个通道的流量分成 2 种类型, 可动态调控通道大小,达到细化流量分配.
 *
 * 使用场景: 同一个接口, 有 VIP 用户和普通用户, 不能让普通用户的流量占满流量通道,无论如何也要分出部分流量给 VIP 用户.
 */
public enum ChannelType {

    SYNC("sync", 0), ASYNC("async", 1);

    String name;
    int code;

    ChannelType(String name, int code) {
        this.name = name;
        this.code = code;
    }

}
