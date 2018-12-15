package thinkinjava.exception;

/**
 *
 * 限流异常. 通常不能算做熔断统计的数据依据.
 */
public class LimitException extends RuntimeException {

    public LimitException() {
    }

    public LimitException(String message) {
        super(message);
    }

    public LimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitException(Throwable cause) {
        super(cause);
    }

    public LimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
