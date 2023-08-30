package com.bleuon.constant;

import lombok.Getter;

@Getter
public enum HttpCode {

    NO_AUTHORITY(403),
    SUCCESS(200),
    ERROR(500);

    private final int code;

    HttpCode(int code) {
        this.code = code;
    }

}
