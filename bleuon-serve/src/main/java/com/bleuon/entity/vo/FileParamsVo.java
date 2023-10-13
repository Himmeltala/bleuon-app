package com.bleuon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 获取文件的参数格式
 * @package: com.bleuon.entity.vo
 * @author: zheng
 * @date: 2023/10/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileParamsVo implements Serializable {

    // 文件路径
    private String filepath;

    // 文件名称
    private String filename;

}
