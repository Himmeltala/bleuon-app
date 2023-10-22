package com.bleuon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    @TableId
    private Integer id;
    private String name;
    private String value;

    private List<AuthorityModel> authorities;

}
