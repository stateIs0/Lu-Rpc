package thinkinjava.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 支持 3 种格式
 * 1. yyyy-MM-dd HH:mm:ss:sss
 * 2. yyyy-MM-dd HH:mm:ss
 * 3. yyyy-MM-dd HH:mm
 */
public class SimpleDateFormatUtil {

    private final static ThreadLocal<SimpleDateFormat> simpleDateFormat1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> simpleDateFormat2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> simpleDateFormat3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    public static String format1(Date date) {
        return simpleDateFormat1.get().format(date);
    }

    public static String format2(Date date) {
        return simpleDateFormat2.get().format(date);
    }

    public static String format3(Date date) {
        return simpleDateFormat3.get().format(date);
    }


}
