package com.bleuon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity
 * @author: zheng
 * @date: 2023/10/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements Serializable {

    private Integer id;
    private String remark;
    private String name;
    private Timestamp modifyDate;
    private Timestamp createDate;

    private List<AuthorityModel> authorities;

}
