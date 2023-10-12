package com.bleuon.entity;

import com.bleuon.constant.ValidPattern;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @description:
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectingConsumer {

    private String id;
    private String remark;
    private Timestamp createDate;
    private Timestamp modifyDate;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    private Consumer consumer;

    public CollectingConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

}
