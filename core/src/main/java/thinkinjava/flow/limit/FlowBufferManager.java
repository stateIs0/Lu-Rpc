package thinkinjava.flow.limit;

import java.util.concurrent.ConcurrentHashMap;

import thinkinjava.util.currence.FlowBuffer;

public class FlowBufferManager {

    private ConcurrentHashMap<String, FlowBuffer> cache = new ConcurrentHashMap<>();


    public static FlowBufferManager getInstance() {
        return FlowBufferManagerInner.INSTANCE;
    }

    private FlowBufferManager() {
    }

    private static class FlowBufferManagerInner {

        private static final FlowBufferManager INSTANCE = new FlowBufferManager();
    }

    /**
     * 获取 Buffer
     * @param unique ..
     * @return PairFlowBuffer
     */
    public FlowBuffer getBuffer(String unique) {
        FlowBuffer buffer = cache.get(unique);
        if (buffer == null) {
            buffer = FlowBuffer.newBuilder().unique(unique).build();
            FlowBuffer old = cache.putIfAbsent(unique, buffer);
            if (old != null) {
                buffer = old;
            }
        }
        return buffer;
    }

}
