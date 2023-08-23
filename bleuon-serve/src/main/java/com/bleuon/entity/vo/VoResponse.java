package com.bleuon.entity.vo;

import lombok.Data;

@Data
public class VoResponse {

    // 状态码
    private Integer code;
    // 消息
    private String message;
    // 数据
    private Object data;

}
