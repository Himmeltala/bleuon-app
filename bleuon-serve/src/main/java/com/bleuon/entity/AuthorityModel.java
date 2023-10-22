package com.bleuon.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityModel implements Serializable {

    private Integer id;

    @Schema(description = "权限名称或备注")
    private String name;

    @Schema(description = "权限集合")
    private String value;

}
