package thinkinjava.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 简化 Java 里繁琐的 close.
 *
 * @author 莫那·鲁道
 * @date 2018/10/15-上午7:06
 */
public class CloseUtil {

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
