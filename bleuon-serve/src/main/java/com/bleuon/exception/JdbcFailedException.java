package com.bleuon.exception;

public class JdbcFailedException extends RuntimeException {

    public JdbcFailedException() {
        super();
    }

    public JdbcFailedException(String message) {
        super(message);
    }

    public JdbcFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcFailedException(Throwable cause) {
        super(cause);
    }

    protected JdbcFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}