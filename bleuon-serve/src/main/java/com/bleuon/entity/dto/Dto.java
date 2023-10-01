package com.bleuon.entity.dto;

import com.bleuon.utils.http.HttpCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Dto implements Serializable {

    private HttpCode code;
    private String message;
    private Object data;

    public Dto() {

    }

    public Dto(HttpCode code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Dto(HttpCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Dto success(HttpCode code, String message, Object data) {
        return new Dto(code, message, data);
    }

    public static Dto success(HttpCode code, String message) {
        return new Dto(code, message);
    }

    public static Dto error(HttpCode code, String message, Object data) {
        return new Dto(code, message, data);
    }

    public static Dto error(HttpCode code, String message) {
        return new Dto(code, message);
    }

}
