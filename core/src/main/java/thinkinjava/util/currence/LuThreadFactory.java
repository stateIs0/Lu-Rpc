package thinkinjava.util.currence;

import java.util.concurrent.ThreadFactory;

/**
 *
 * @author 莫那·鲁道
 * @date 2018-12-15-10:36
 */
public class LuThreadFactory implements ThreadFactory {

    private String name;

    public LuThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
