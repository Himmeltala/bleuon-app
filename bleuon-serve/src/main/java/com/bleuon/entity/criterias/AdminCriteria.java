package com.bleuon.entity.criterias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.entity.criterias
 * @author: zheng
 * @date: 2023/10/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCriteria extends CommonCriteria implements Serializable {

    private String id;
    private String username;
    private String password;
    private String phone;
    private String email;

}
