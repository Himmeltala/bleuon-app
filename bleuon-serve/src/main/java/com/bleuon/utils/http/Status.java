package com.bleuon.utils.http;

import lombok.Getter;

@Getter
public enum Status {

    /**
     * 权限不足的请求
     */
    NO_AUTHORITY(403),

    /**
     * 成功的请求
     */
    SUCCESS(200),

    /**
     * 错误的请求，是内部代码错误、异常等情况
     */
    ERROR(500),

    /**
     * 失败的请求，不是内部代码错误、异常等情况
     */
    FAILED(400);

    private final int code;

    Status(int code) {
        this.code = code;
    }

}
