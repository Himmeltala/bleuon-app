package com.bleuon.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "FormData 模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileParamsVO implements Serializable {

    @Schema(description = "文件路径")
    private String filepath;

    @Schema(description = "文件名称")
    private String filename;

}
