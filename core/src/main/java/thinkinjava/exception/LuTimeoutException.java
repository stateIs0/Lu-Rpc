package thinkinjava.exception;

/**
 *
 * 超时异常.
 */
public class LuTimeoutException extends RuntimeException {


    public LuTimeoutException() {
    }

    public LuTimeoutException(String message) {
        super(message);
    }

    public LuTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public LuTimeoutException(Throwable cause) {
        super(cause);
    }

    public LuTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
