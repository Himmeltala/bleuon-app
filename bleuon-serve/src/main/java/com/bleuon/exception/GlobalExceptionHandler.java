package com.bleuon.exception;

import com.bleuon.constant.JdbcExpType;
import com.bleuon.utils.http.R;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public R handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e.getCause());
        return R.error(e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    public R handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e.getCause());
        return R.error(e.getMessage());
    }

    @ExceptionHandler({JdbcErrorException.class})
    public R handleJdbcErrorException(JdbcErrorException e) {
        log.error(e.getMessage(), e.getCause());
        return R.error(JdbcExpType.ERROR);
    }

    @ExceptionHandler({JdbcFailedException.class})
    public R handleJdbcOperationException(JdbcFailedException e) {
        log.warn(e.getMessage(), e.getCause());
        return R.error(JdbcExpType.FAILED);
    }

}
