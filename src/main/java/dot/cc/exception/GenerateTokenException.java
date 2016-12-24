package dot.cc.exception;

/**
 * Created by haizhu on 2016/12/24.
 * <p>
 * haizhu12345@gmail.com
 */
public class GenerateTokenException extends Exception {

    public GenerateTokenException() {
    }

    public GenerateTokenException(String message) {
        super(message);
    }

    public GenerateTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateTokenException(Throwable cause) {
        super(cause);
    }

    public GenerateTokenException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
