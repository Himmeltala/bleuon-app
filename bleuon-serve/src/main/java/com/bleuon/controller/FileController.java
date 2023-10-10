package com.bleuon.controller;

import com.bleuon.annotaion.RequestMappingPrefix;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.vo.FileParamsVo;
import com.bleuon.service.FileService;
import com.bleuon.utils.JwtUtil;
import com.bleuon.utils.http.R;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

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

    private final FileService service;

    @GetMapping(value = "/preview/image")
    public void previewImageFile(FileParamsVo params, HttpServletResponse response) {
        try {
            byte[] bytes = service.load(params.getFilename(), params.getFilepath(), response);
            try (ServletOutputStream output = response.getOutputStream()) {
                output.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload/ckeditor/image")
    public R<Object> uploadCkeditorImage(@RequestHeader(KeyVals.Token) String token, MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择一个图片！");
        }

        Claims claims = JwtUtil.parseJwt(token);
        String uid = (String) claims.get("id");

        String imgUrl = service.upload("/static/images/ckeditor/" + uid, null, file);
        if (StringUtils.hasText(imgUrl)) {
            return R.success("上传成功！", imgUrl);
        } else {
            return R.failed("上传失败！");
        }
    }

}
