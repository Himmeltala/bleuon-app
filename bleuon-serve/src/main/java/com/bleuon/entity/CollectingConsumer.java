package com.bleuon.entity;

import com.bleuon.constant.ValidPattern;
import com.bleuon.entity.dto.ConsumerDto;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class CollectingConsumer implements Serializable {

    private String id;
    private String remark;
    private Timestamp createDate;
    private Timestamp modifyDate;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String consumerId;

    @Pattern(regexp = ValidPattern.UUID, message = "不是合法的 UUID！")
    private String collectingCid;

    private ConsumerDto consumer;

    public CollectingConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

}
