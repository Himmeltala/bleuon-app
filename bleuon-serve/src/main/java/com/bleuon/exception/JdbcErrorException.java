package com.bleuon.exception;

public class JdbcErrorException extends RuntimeException {

    public JdbcErrorException() {
        super();
    }

    public JdbcErrorException(String message) {
        super(message);
    }

    public JdbcErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcErrorException(Throwable cause) {
        super(cause);
    }

    protected JdbcErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
