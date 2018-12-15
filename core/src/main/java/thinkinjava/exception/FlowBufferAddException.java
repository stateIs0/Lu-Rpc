package thinkinjava.exception;

/**
 *
 * 流量缓冲区溢出.
 */
public class FlowBufferAddException extends RuntimeException {

    public FlowBufferAddException() {
    }

    public FlowBufferAddException(String message) {
        super(message);
    }

    public FlowBufferAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowBufferAddException(Throwable cause) {
        super(cause);
    }

    public FlowBufferAddException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
