package com.bleuon.entity.criterias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity.criterias
 * @author: zheng
 * @date: 2023/10/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCriteria extends CommonCriteria implements Serializable {

    private String adminId;
    private Integer roleId;
    private Integer authId;
    private String username;
    private String keyword;
    private List<String> authIds;

}
