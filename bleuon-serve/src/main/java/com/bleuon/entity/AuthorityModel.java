package com.bleuon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

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
    private String name;
    private String value;
    private Timestamp modifyDate;
    private Timestamp createDate;

}
