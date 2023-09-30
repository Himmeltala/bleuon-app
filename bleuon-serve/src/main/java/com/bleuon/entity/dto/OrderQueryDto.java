package com.bleuon.entity.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @package: com.bleuon.entity.dto
 * @author: zheng
 * @date: 2023/9/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryDto {

    @Pattern(regexp = "asc|desc", message = "升序格式错误！")
    private String order;

    private List<String> cols;

}
