package com.bleuon.exception;

import com.bleuon.utils.http.R;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ValidationException.class,
            ConstraintViolationException.class
    })
    public R<Object> handleBindException(BindException e) {
        log.error(e.getMessage(), e.getCause());
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return R.error(message);
    }

    @ExceptionHandler({AuthenticationException.class})
    public R<Object> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e.getCause());
        return R.error(e.getMessage());
    }

    @ExceptionHandler({JdbcErrorException.class})
    public R<Object> handleJdbcErrorException(JdbcErrorException e) {
        log.error(e.getMessage(), e.getCause());
        return R.error("服务器内部异常，操作数据库错误！");
    }

    @ExceptionHandler({JdbcFailedException.class})
    public R<Object> handleJdbcOperationException(JdbcFailedException e) {
        log.warn(e.getMessage(), e.getCause());
        return R.error("服务器内部异常，操作数据库失败！");
    }

}
