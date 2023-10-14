package com.bleuon.controller.expose;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.vo.FileParamsVO;
import com.bleuon.service.FileService;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/9
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/public/file")
@Tag(name = "文件")
public class FileController {

    private final FileService service;

    @Operation(summary = "获取图片二进制")
    @GetMapping(value = "/preview/image")
    public void previewImageFile(FileParamsVO vo, HttpServletResponse response) {
        try {
            byte[] bytes = service.load(vo.getFilename(), vo.getFilepath(), response);
            try (ServletOutputStream output = response.getOutputStream()) {
                output.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Operation(summary = "上传图片", parameters = {
            @Parameter(name = "path", description = "图片路径，上传到 classes/target 目录下", example = "/static/images/avatar", in = ParameterIn.HEADER)
    })
    @PostMapping("/upload/image")
    public R<Object> uploadImageFile(MultipartFile file, String path) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        String imgUrl = service.upload("/static/images/" + path, null, file);
        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传成功！", imgUrl);
        } else {
            return R.failed("上传失败！");
        }
    }

}
