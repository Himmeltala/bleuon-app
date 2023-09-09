package com.bleuon.utils.http;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class R<T> {

    private final Integer code;
    private String message;
    private T data;

    private R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private R(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    private R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> R<T> build(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }

    public static <T> R<T> build(Integer code, T data) {
        return new R<>(code, data);
    }

    public static R<Void> build(Integer code, String message) {
        return new R<>(code, message);
    }

    public static R<Void> error(String message) {
        return new R<>(HttpCode.ERROR.getCode(), message);
    }

    public static <T> R<T> error(String message, T data) {
        return new R<>(HttpCode.ERROR.getCode(), message, data);
    }

    public static <T> R<T> error(T data) {
        return new R<>(HttpCode.SUCCESS.getCode(), data);
    }

    public static R<Void> success(String message) {
        return new R<>(HttpCode.SUCCESS.getCode(), message);
    }

    public static <T> R<T> success(String message, T data) {
        return new R<>(HttpCode.SUCCESS.getCode(), message, data);
    }

    public static <T> R<T> success(T data) {
        return new R<>(HttpCode.SUCCESS.getCode(), data);
    }

    public static R<Void> failed(String message) {
        return new R<>(HttpCode.FAILED.getCode(), message);
    }

    public static <T> R<T> failed(String message, T data) {
        return new R<>(HttpCode.FAILED.getCode(), message, data);
    }

    public static <T> R<T> failed(T data) {
        return new R<>(HttpCode.FAILED.getCode(), data);
    }

}
