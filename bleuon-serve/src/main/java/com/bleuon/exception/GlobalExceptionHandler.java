package com.bleuon.exception;

import com.bleuon.constant.HttpCode;
import com.bleuon.entity.vo.Vo;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public Vo handleValidationException(ValidationException e) {
        log.warn("ValidationException [{}: {}]", e.getClass().getName(), e.getMessage());
        return Vo.error(HttpCode.ERROR, e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    public Vo handleAuthenticationException(AuthenticationException e) {
        log.warn("AuthenticationException [{}: {}]", e.getClass().getName(), e.getMessage());
        return Vo.error(HttpCode.ERROR, e.getMessage());
    }

}
