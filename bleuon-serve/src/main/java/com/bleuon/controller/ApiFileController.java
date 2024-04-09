package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.Constants;
import com.bleuon.entity.vo.FileParamsVO;
import com.bleuon.service.FileService;
import com.bleuon.utils.FileUtil;
import com.bleuon.utils.http.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @description: API 控制器
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/9
 */
@Tag(name = "文件管理")
@RequiredArgsConstructor
@RequestMappingPrefix("/public/file")
public class ApiFileController implements Serializable {

    private final FileService fileService;

    @Operation(summary = "获取图片二进制")
    @GetMapping(value = "/preview/image")
    public void previewImageFile(FileParamsVO vo, HttpServletResponse response) {
        MediaType contentType = FileUtil.getContentType(vo.getFilename());
        response.setContentType(contentType.toString());

        try {
            byte[] bytes = fileService.download(vo.getFilepath(), vo.getFilename());
            ServletOutputStream output = response.getOutputStream();
            output.write(bytes);
            output.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "上传图片")
    @PostMapping("/upload/image")
    public R<Object> uploadImageFile(MultipartFile file, FileParamsVO vo) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        String imgUrl = fileService.upload(Constants.RESOURCE_ROOT + "/static/images/" + vo.getFilepath(), vo.getFilename(), file);

        if (!StringUtils.hasText(imgUrl)) {
            return R.failed("上传失败！");
        }

        return R.success("上传成功！", imgUrl);
    }

    @Operation(summary = "删除图片")
    @DeleteMapping("/delete/image")
    public R<Object> deleteImageFile(FileParamsVO vo) {
        boolean success = fileService.delete(vo.getFilepath(), vo.getFilename());
        return success ? R.success("删除成功！") : R.error("删除失败！");
    }

}
