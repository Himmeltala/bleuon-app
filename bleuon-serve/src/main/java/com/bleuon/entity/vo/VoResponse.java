package com.bleuon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoResponse {

    // 状态码
    private Integer code;
    // 消息
    private String message;
    // 数据
    private Object data;

}
