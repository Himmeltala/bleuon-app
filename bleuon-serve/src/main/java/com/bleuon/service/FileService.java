package com.bleuon.service;

import com.bleuon.utils.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/10
 */
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileUtil fileUtil;

    /**
     * @param writePath   写入的目录
     * @param newFilename 重命名文件
     * @param file        上传的文件
     * @return 返回一个存储的文件路径
     */
    public String upload(String writePath, String newFilename, MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();

            if (filename != null && filename.lastIndexOf(".") != -1) {
                String extension = filename.substring(filename.lastIndexOf(".") + 1);

                if (StringUtils.hasText(newFilename)) {
                    filename = newFilename + "." + extension;
                }

                boolean writeSuccess = fileUtil.writeToResources(writePath, filename, file.getInputStream());

                if (writeSuccess) {
                    return "http://localhost:8080/api/file/preview/image?filepath=" + writePath + "&filename=" + filename;
                }
            }

            return "";
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public byte[] load(String filename, String filepath, HttpServletResponse response) {
        try {
            MediaType contentType = fileUtil.getContentType(filename);
            response.setContentType(contentType.toString());
            return fileUtil.readFromResources(filepath, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
