package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.entity.vo.FileParamsVo;
import com.bleuon.utils.FileUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

/**
 * @description:
 * @package: com.bleuon.controller
 * @author: zheng
 * @date: 2023/10/9
 */
@RequiredArgsConstructor
@RequestMappingPrefix("/file")
public class FileController {

    private final FileUtil fileUtil;

    @GetMapping(value = "/preview/image")
    public void previewImageFile(FileParamsVo params, HttpServletResponse response) {
        try {
            MediaType contentType = fileUtil.getContentType(params.getFilename());
            response.setContentType(contentType.toString());

            byte[] bytes = fileUtil.readFromResources(params.getFilepath(), params.getFilename());
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                outputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
