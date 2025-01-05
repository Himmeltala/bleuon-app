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

    public static <T> R<T> build(Integer code, String message) {
        return new R<>(code, message);
    }

    public static <T> R<T> error(String message) {
        return new R<>(Status.ERROR.getCode(), message, null);
    }

    public static <T> R<T> error(String message, T data) {
        return new R<>(Status.ERROR.getCode(), message, data);
    }

    public static <T> R<T> error(T data) {
        return new R<>(Status.SUCCESS.getCode(), data);
    }

    public static <T> R<T> success(String message) {
        return new R<>(Status.SUCCESS.getCode(), message, null);
    }

    public static <T> R<T> success(String message, T data) {
        return new R<>(Status.SUCCESS.getCode(), message, data);
    }

    public static <T> R<T> success(T data) {
        return new R<>(Status.SUCCESS.getCode(), data);
    }

    public static <T> R<T> failed(String message) {
        return new R<>(Status.FAILED.getCode(), message, null);
    }

    public static <T> R<T> failed(String message, T data) {
        return new R<>(Status.FAILED.getCode(), message, data);
    }

    public static <T> R<T> failed(T data) {
        return new R<>(Status.FAILED.getCode(), data);
    }

    public static <T> R<T> unpass(String message) {
        return new R<>(Status.NO_AUTHORITY.getCode(), message, null);
    }

}
