package com.bleuon.controller.open;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/9
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/public/file")
@Tag(name = "文件")
public class FileController implements Serializable {

    private final FileService fileService;

    @Operation(summary = "获取图片二进制")
    @GetMapping(value = "/preview/image")
    public void previewImageFile(FileParamsVO vo, HttpServletResponse response) {
        try {
            byte[] bytes = fileService.download(vo.getFilepath(), vo.getFilename(), response);
            try (ServletOutputStream output = response.getOutputStream()) {
                output.write(bytes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "上传图片", parameters = {
            @Parameter(name = "path", description = "图片路径，上传到 classes/target 目录下", example = "/static/images/avatar", in = ParameterIn.HEADER)
    })
    @PostMapping("/upload/image")
    public R<Object> uploadImageFile(MultipartFile file, String filepath) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        String imgUrl = fileService.upload("/static/images/" + filepath, null, file);
        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传成功！", imgUrl);
        } else {
            return R.failed("上传失败！");
        }
    }

    @Operation(summary = "删除图片")
    @DeleteMapping("/delete/image")
    public R<Object> deleteImageFile(FileParamsVO vo) {
        boolean success = fileService.delete(vo.getFilepath(), vo.getFilename());
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

}
