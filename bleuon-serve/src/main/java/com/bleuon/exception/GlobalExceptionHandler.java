package com.bleuon.exception;

import com.bleuon.utils.http.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求参数格式错误 @RequestBody上使用@Valid 实体上使用@NotNull等，验证失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e.getCause());
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return R.error(message);
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public R<Object> BindExceptionHandler(BindException e) {
        log.error(e.getMessage(), e.getCause());
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return R.error(message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Object> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage(), e.getCause());
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return R.error(message);
    }

    /**
     * 参数格式异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Object> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e.getCause());
        return R.error(e.getMessage());
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
