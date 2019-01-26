package thinkinjava.exception;

/**
 *
 * 用户操作错误异常.
 *
 * @author 莫那·鲁道
 * @date 2019-01-26-12:20
 */
public class UserOperationException extends RuntimeException {

    public UserOperationException() {
    }

    public UserOperationException(String message) {
        super(message);
    }

    public UserOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserOperationException(Throwable cause) {
        super(cause);
    }

    public UserOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
