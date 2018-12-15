package thinkinjava.exception;

/**
 *
 * 熔断异常. 通常不能算做熔断统计的数据依据.
 */
public class CircuitBreakerException extends RuntimeException {

    public CircuitBreakerException() {
    }

    public CircuitBreakerException(String message) {
        super(message);
    }

    public CircuitBreakerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircuitBreakerException(Throwable cause) {
        super(cause);
    }

    public CircuitBreakerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
