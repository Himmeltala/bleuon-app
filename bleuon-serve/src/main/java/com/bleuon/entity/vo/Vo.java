package com.bleuon.entity.vo;

import com.bleuon.consts.HttpCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Vo implements Serializable {

    private HttpCode code;
    private String message;
    private Object data;

    public Vo() {

    }

    public Vo(HttpCode code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Vo(HttpCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Vo success(HttpCode code, String message, Object data) {
        return new Vo(code, message, data);
    }

    public static Vo success(HttpCode code, String message) {
        return new Vo(code, message);
    }

    public static Vo error(HttpCode code, String message, Object data) {
        return new Vo(code, message, data);
    }

    public static Vo error(HttpCode code, String message) {
        return new Vo(code, message);
    }

}
